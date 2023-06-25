// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/interfaces/IERC721.sol";
import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";
import "@openzeppelin/contracts-upgradeable/security/ReentrancyGuardUpgradeable.sol";
import "./Interface/IUndefined.sol";
import "./UndefinedConfing.sol";


contract Undefined is IUndefined, UndefinedConfig, ReentrancyGuardUpgradeable {
    RaffleInfo[] public raffles; // raffleId => raffleInfo
    mapping(uint => mapping(uint => address)) internal buyers; // raffleId => ticketIndex => buyer
    mapping(address => NftInfo[]) public claimableNft;
    mapping(address => uint) public claimableBalance;

    constructor() {
        _disableInitializers();
    }

    function initialize(
        uint120 maxTicketAmount_,
        uint120 minTicketPrice_,
        uint16 feeNumerator_,
        address feeTo_
    ) external initializer {
        require(feeTo_ != address(0), "feeTo address is zero address");
        __UndefinedConfig_init(maxTicketAmount_, minTicketPrice_, feeNumerator_, feeTo_);
        __Ownable_init();
    }

    modifier onlyRegistered(uint raffleId) {
        require(raffleId < raffles.length, "unregisterd raffle ID");
        _;
    }

    function registerRaffle(
        address nftCa,
        uint tokenId,
        uint120 totalTickets,
        uint120 ticketPrice,
        uint day
    ) external onlyListed(nftCa) {
        require(ticketPrice >= minTicketPrice, "too low price");
        require(
            totalTickets > 0 && totalTickets <= maxTicketAmount,
            "unproper ticket amount"
        );
        IERC721(nftCa).transferFrom(msg.sender, address(this), tokenId);
        RaffleInfo memory raffleInfo = RaffleInfo(
            tokenId,
            uint128(raffles.length), // raffle Id
            ticketPrice,
            block.timestamp + day * 1 days,
            totalTickets,
            totalTickets,
            nftCa,
            msg.sender,
            address(0)
        );
        raffles.push(raffleInfo);
        emit RegisterRaffle(raffleInfo);
    }

    function buyTickets(
        uint raffleId,
        uint amount
    ) external payable onlyRegistered(raffleId) {
        RaffleInfo storage raffleInfo = raffles[raffleId];
        require(0 < amount && amount <= raffleInfo.leftTickets, "invalid amount");
        require(amount * raffleInfo.ticketPrice == msg.value, "improper money");
        require(raffleInfo.endTime > block.timestamp, "raffle times up");
        uint soldTicketsAmount;
        uint toIndex;
        unchecked {
            soldTicketsAmount =
                raffleInfo.totalTickets -
                raffleInfo.leftTickets; // totalTickets >= leftTickets
            toIndex = soldTicketsAmount + amount - 1; // amount > 0
        }
        for (uint i = 1; i <= (amount / 50); i++) {
            uint stampIndex = soldTicketsAmount + 50 * i - 1;
            if (toIndex == stampIndex) break;
            buyers[raffleId][stampIndex] = msg.sender;
        } // 구매자가 티켓 100개이상 구매하면 인덱스 50 단위마다 주인 등록하는 기능
        buyers[raffleId][toIndex] = msg.sender;
        unchecked {
            raffleInfo.leftTickets -= uint128(amount); // leftTickets >= amount
        }
        emit BuyTickets(
            raffleId,
            msg.sender,
            soldTicketsAmount,
            toIndex,
            amount,
            raffleInfo.leftTickets
        );
    }

    function chooseWinner(
        uint raffleId,
        uint randNum,
        address creator,
        uint creatorFeeNumerator
    ) external onlyOwner onlyRegistered(raffleId) {
        uint _feeNumerator = feeNumerator;
        require(_feeNumerator + creatorFeeNumerator <= 1000 , "fees over 100%");
        RaffleInfo storage raffleInfo = raffles[raffleId];
        uint soldTicketsAmount;
        unchecked {
            soldTicketsAmount = raffleInfo.totalTickets - raffleInfo.leftTickets;
        } // totalTickets >= leftTickets
        require(soldTicketsAmount > 0, "failed raffle");
        require(raffleInfo.winner == address(0), "winner already chosen");
        require(
            raffleInfo.endTime < block.timestamp || raffleInfo.leftTickets <= 0,
            "not ended or not sold out"
        );
        uint winnerTicketIndex = uint(
            keccak256(
                abi.encodePacked(block.prevrandao, block.timestamp, randNum)
            )
        ) % soldTicketsAmount;
        address winner = getTicketOwnerByIndex(raffleId, winnerTicketIndex);
        raffleInfo.winner = winner;
        uint wholeSales = soldTicketsAmount * raffleInfo.ticketPrice;
        uint fee = (wholeSales * _feeNumerator) / 1000;
        uint creatorFee = creator != address(0) ? (wholeSales * creatorFeeNumerator) / 1000 : 0;
        uint settlement;
        unchecked {
            settlement = wholeSales - fee - creatorFee;
        } // feeNumerator + creatorFeeNumerator <= 1000
        _setClaimInfo(
            winner,
            raffleInfo.seller,
            NftInfo(raffleInfo.tokenId, raffleInfo.nftCa),
            settlement
        );
        if(fee > 0){
            (bool success, ) = feeTo.call{value: fee}("");
            require(success, "failed to transfer fee");
        }
        if(creatorFee > 0){
            (bool success, ) = creator.call{value: creatorFee}("");
            require(success, "failed to transfer creatorFee");
        }
        emit ChooseWinner(raffleId, winner, winnerTicketIndex, block.number);
    }

    function claimAllNfts() external {
        NftInfo[] memory nftInfoList = claimableNft[msg.sender];
        delete claimableNft[msg.sender];
        for (uint i = 0; i < nftInfoList.length; i++) {
            IERC721(nftInfoList[i].nftCa).transferFrom(
                address(this),
                msg.sender,
                nftInfoList[i].tokenId
            );
        }
        emit ClaimAllNfts(msg.sender, nftInfoList);
    }

    function claimNftByIndex(uint index) external {
        NftInfo[] storage nfts = claimableNft[msg.sender];
        uint length = nfts.length;
        require(length > 0, "nothing to claim");
        NftInfo memory claimNftInfo = nfts[index];
        nfts[index] = nfts[length - 1];
        nfts.pop();
        IERC721(claimNftInfo.nftCa).transferFrom(
            address(this),
            msg.sender,
            claimNftInfo.tokenId
        );
        emit ClaimNft(msg.sender, claimNftInfo);
    }

    // 티켓이 하나도 안팔린 nft를 판매자가 스스로 수령해가는 함수
    function claimNftForFailedSeller(
        uint raffleId
    ) external onlyRegistered(raffleId) {
        RaffleInfo storage raffleInfo = raffles[raffleId];
        require(raffleInfo.seller == msg.sender, "not seller");
        require(raffleInfo.endTime < block.timestamp, "not ended");
        require(raffleInfo.leftTickets == 0, "not failed raffle");
        require(raffleInfo.winner == address(0), "already claimed");
        raffleInfo.winner = msg.sender;
        IERC721(raffleInfo.nftCa).transferFrom(
            address(this),
            msg.sender,
            raffleInfo.tokenId
        );
        // emit ClaimNftForFailedSeller(raffleId, raffleInfo.seller);
    }

    function claimBalance(uint amount) external nonReentrant {
        uint balance = claimableBalance[msg.sender];
        require(balance >= amount, "not enough balance");
        claimableBalance[msg.sender] -= amount;
        (bool success, ) = msg.sender.call{value: amount}("");
        if (!success) revert("send transaction failed");
        emit ClaimBalance(msg.sender, amount, balance - amount);
    }

    function getTicketOwnerByIndex(
        uint raffleId,
        uint index
    ) public view returns (address) {
        uint soldTicketsAmount = raffles[raffleId].totalTickets - raffles[raffleId].leftTickets; // more gas efficient way
        require(index < soldTicketsAmount, "unsold index");
        for (uint i = index; i < soldTicketsAmount; i++) {
            address owner = buyers[raffleId][i];
            if (owner != address(0)) return owner;
        }
        revert("something wrong");
    }

    function getClaimableNftsLength(
        address owner
    ) external view returns (uint length) {
        length = claimableNft[owner].length;
    }

    function _setClaimInfo(
        address winner,
        address seller,
        NftInfo memory nft,
        uint settlement
    ) private {
        claimableNft[winner].push(nft);
        claimableBalance[seller] = settlement;
    }
}
