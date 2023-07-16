// SPDX-License-Identifier: MIT

pragma solidity 0.8.18;

import "@openzeppelin/contracts/interfaces/IERC721.sol";
import "./Interface/IUndefined.sol";
import "./UndefinedConfig.sol";

contract Undefined is IUndefined, UndefinedConfig {
    RaffleInfo[] public raffles;
    mapping(uint => mapping(uint => address)) public buyer; // raffleId => ticketIndex => buyer
    mapping(address => NftInfo[]) public claimableNft;
    mapping(address => uint) public claimableBalance;
    uint private constant LOCKED = 2;
    uint private constant UN_LOCKED = 1;
    uint private door;

    constructor() {
        _disableInitializers();
    }

    function initialize(
        uint16 feeNumerator_,
        uint16 maxTicketAmount_,
        uint224 minTicketPrice_
    ) external initializer {
        __UndefinedConfig_init(
            feeNumerator_,
            maxTicketAmount_,
            minTicketPrice_
        );
        __Ownable_init();
        door = UN_LOCKED;
    }

    modifier onlyRegistered(uint raffleId) {
        require(raffleId < raffles.length, "unregisterd raffle ID");
        _;
    }

    modifier lock() {
        require(door == UN_LOCKED, "locked");
        door = LOCKED;
        _;
        door = UN_LOCKED;
    }

    function registerRaffle(
        address nftCa,
        uint tokenId,
        uint80 totalTickets,
        uint120 ticketPrice,
        uint miunte
        // uint day
    ) external onlyListed(nftCa) {
        require(ticketPrice >= minTicketPrice, "too low price");
        require(totalTickets > 0 && totalTickets <= maxTicketAmount, "unproper ticket amount");
        require(miunte > 0, "day must more than zero");
        // require(day > 0, "day must more than zero");
        IERC721(nftCa).transferFrom(msg.sender, address(this), tokenId);
        RaffleInfo memory raffleInfo = RaffleInfo(
            tokenId,
            uint96(raffles.length), // raffle Id
            ticketPrice,
            // block.timestamp + day * 1 days,
            block.timestamp + miunte * 1 minutes,
            totalTickets,
            nftCa,
            totalTickets,
            msg.sender,
            address(0)
        );
        raffles.push(raffleInfo);
        emit RegisterRaffle(raffleInfo);
    }

    function buyTickets(uint96 raffleId, uint80 amount) external payable onlyRegistered(raffleId) {
        RaffleInfo storage raffleInfo = raffles[raffleId];
        require(0 < amount && amount <= raffleInfo.leftTickets, "invalid amount");
        require(amount * raffleInfo.ticketPrice == msg.value, "improper money");
        require(raffleInfo.endTime > block.timestamp, "raffle times up");
        uint soldTicketsAmount;
        uint toIndex;
        unchecked {
            soldTicketsAmount = raffleInfo.totalTickets - raffleInfo.leftTickets; // totalTickets >= leftTickets
            toIndex = soldTicketsAmount + amount - 1; // amount > 0
        }
        for (uint i = 1; i <= (amount / 50); i++) {
            uint stampIndex = soldTicketsAmount + 50 * i - 1;
            if (toIndex == stampIndex) break;
            buyer[raffleId][stampIndex] = msg.sender;
        }
        buyer[raffleId][toIndex] = msg.sender;
        unchecked {
            raffleInfo.leftTickets -= amount; // leftTickets >= amount
        }
        emit BuyTickets(
            raffleId,
            msg.sender,
            soldTicketsAmount,
            toIndex,
            raffleInfo.leftTickets
        );
    }

    function chooseWinner(uint96 raffleId, uint randNum) external onlyOwner onlyRegistered(raffleId) lock {
        RaffleInfo storage raffleInfo = raffles[raffleId];
        uint80 soldTicketsAmount;
        unchecked {
            soldTicketsAmount = raffleInfo.totalTickets - raffleInfo.leftTickets;
        } // totalTickets >= leftTickets
        require(soldTicketsAmount > 0, "failed raffle");
        require(raffleInfo.winner == address(0), "winner already chosen");
        require(
            raffleInfo.endTime < block.timestamp || raffleInfo.leftTickets <= 0,
            "not ended or not sold out"
        );

        // choose winner
        uint winnerTicketIndex = uint(keccak256(
            abi.encodePacked(block.prevrandao, block.timestamp, randNum)
        )) % soldTicketsAmount;
        address winner = getTicketOwnerByIndex(raffleId, winnerTicketIndex);
        raffleInfo.winner = winner;

        // settlement calculation
        uint wholeSales = soldTicketsAmount * raffleInfo.ticketPrice;
        uint fee = (wholeSales * feeNumerator) / 1000;
        CreatorInfo memory creatorInfo = creatorInfo[raffleInfo.nftCa];
        uint creatorFee = (wholeSales * creatorInfo.creatorFeeNumerator) / 1000;
        uint settlement;
        unchecked {
            settlement = wholeSales - fee - creatorFee;
        } // fee(max 30% of wholeSales), creatorFee(max 10% of wholeSales)

        claimableNft[winner].push(
            NftInfo(raffleId, raffleInfo.nftCa, raffleInfo.tokenId)
        );
        claimableBalance[raffleInfo.seller] = settlement;
        feeBox += fee;
        if (creatorFee > 0) {
            // If creatorFee > 0, creator is not zero address.
            (bool success,) = creatorInfo.creator.call{value: creatorFee}("");
            require(success, "failed to transfer creatorFee");
        }
        emit ChooseWinner(raffleId, winner, winnerTicketIndex, settlement); 
    }

    function claimAllNfts() external {
        NftInfo[] memory nftInfoList = claimableNft[msg.sender];
        delete claimableNft[msg.sender];
        uint96 [] memory raffleIds = new uint96[](nftInfoList.length);
        for (uint i = 0; i < nftInfoList.length; i++) {
            IERC721(nftInfoList[i].nftCa).safeTransferFrom(address(this), msg.sender, nftInfoList[i].tokenId);
            raffleIds[i] = nftInfoList[i].raffleId;
        }
        emit ClaimAllNfts(msg.sender, raffleIds);
    }

    function claimNftByIndex(uint index) external {
        NftInfo[] storage nfts = claimableNft[msg.sender];
        require(nfts.length > 0, "nothing to claim");
        NftInfo memory claimNftInfo = nfts[index];
        nfts[index] = nfts[nfts.length - 1];
        nfts.pop();
        IERC721(claimNftInfo.nftCa).safeTransferFrom(address(this), msg.sender, claimNftInfo.tokenId);
        emit ClaimNft(msg.sender, claimNftInfo.raffleId);
    }

    function claimNftForFailedSeller(uint96 raffleId) external onlyRegistered(raffleId) {
        RaffleInfo storage raffleInfo = raffles[raffleId];
        require(raffleInfo.seller == msg.sender, "not seller");
        require(raffleInfo.endTime < block.timestamp, "not ended");
        require(raffleInfo.leftTickets == raffleInfo.totalTickets, "not failed raffle");
        require(raffleInfo.winner == address(0), "already claimed");
        raffleInfo.winner = msg.sender;
        IERC721(raffleInfo.nftCa).safeTransferFrom(address(this), msg.sender, raffleInfo.tokenId);
        emit ClaimNft(msg.sender, raffleId);
    }

    function claimBalance(uint amount) external lock {
        uint balance = claimableBalance[msg.sender];
        require(balance >= amount, "not enough balance");
        claimableBalance[msg.sender] -= amount;
        (bool success, ) = msg.sender.call{value: amount}("");
        if (!success) revert("transfer failed");
        emit ClaimBalance(msg.sender, amount, balance - amount);
    }

    function withdrawFee(address to, uint amount) external onlyOwner lock {
        require(to != address(0), "zero address");
        require(amount <= feeBox, "cannot withdraw more than feeBox");
       (bool success,) = to.call{value: amount}("");
       require(success, "transfer failed");
       emit WithdrawFee(to, amount);
    }

    function getTicketOwnerByIndex(uint raffleId, uint index) public view returns (address) {
        uint soldTicketsAmount = raffles[raffleId].totalTickets - raffles[raffleId].leftTickets; // more gas efficient way
        require(index < soldTicketsAmount, "unsold index");
        for (uint i = index; i < soldTicketsAmount; i++) {
            address owner = buyer[raffleId][i];
            if (owner != address(0)) return owner;
        }
        revert("internal logic error");
    }

    function getClaimableNftsLength(address owner) external view returns (uint length) {
        length = claimableNft[owner].length;
    }
}
