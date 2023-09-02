// SPDX-License-Identifier: MIT

pragma solidity 0.8.18;

import "@openzeppelin/contracts/interfaces/IERC721.sol";
import "@openzeppelin/contracts/token/ERC721/IERC721Receiver.sol";
import "./Interface/IUndefined.sol";
import "./UndefinedConfig.sol";

contract Undefined is IUndefined, UndefinedConfig, IERC721Receiver {
     struct RaffleInfo {
        uint96 raffleId;
        uint144 ticketPrice;
        uint16 totalTickets;
        uint16 leftTickets;
        address nftCa;
        uint tokenId; 
        uint endTime;
        address seller;
        address winner;
    } 
    struct NftInfo {
        uint96 raffleId;
        address nftCa;
        uint tokenId;
    }

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
        uint96 minTicketPrice_,
        address feeTo_
    ) external initializer {
        __UndefinedConfig_init(
            feeNumerator_,
            maxTicketAmount_,
            minTicketPrice_,
            feeTo_
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
        uint16 totalTickets,
        uint144 ticketPrice,
        uint minute
        // uint day
    )
        external
        onlyListed(nftCa)
    {
        require(ticketPrice >= minTicketPrice, "too low price");
        require(
            totalTickets > 0 && totalTickets <= maxTicketAmount,
            "unproper ticket amount"
        );
        require(minute > 0, "day must more than zero");
        // require(day > 0 && day < 7, "day must more than zero & less than 7");
        IERC721(nftCa).transferFrom(msg.sender, address(this), tokenId);
        uint96 raffleId = uint96(raffles.length);
        raffles.push(RaffleInfo({
            tokenId: tokenId,
            raffleId: raffleId, 
            ticketPrice: ticketPrice,
            // block.timestamp + day * 1 days,
            endTime: block.timestamp + minute * 1 minutes,
            totalTickets: totalTickets,
            nftCa: nftCa,
            leftTickets: totalTickets,
            seller: msg.sender,
            winner: address(0)
        }));
        emit RegisterRaffle(
            raffleId, 
            tokenId, 
            nftCa, 
            ticketPrice, 
            totalTickets, 
            block.timestamp + minute * 1 minutes, 
            msg.sender
            );
    }

    function buyTickets(
        uint96 raffleId,
        uint16 amount
    ) external payable onlyRegistered(raffleId) {
        RaffleInfo storage raffleInfo = raffles[raffleId];
        require(
            0 < amount && amount <= raffleInfo.leftTickets,
            "invalid amount"
        );
        require(amount * raffleInfo.ticketPrice == msg.value, "improper money");
        require(raffleInfo.endTime >= block.timestamp, "raffle times up");
        uint soldTicketsAmount;
        uint toIndex;
        uint max;
        unchecked {
            soldTicketsAmount =
                raffleInfo.totalTickets -
                raffleInfo.leftTickets; // totalTickets >= leftTickets
            toIndex = soldTicketsAmount + amount - 1 ; // amount >= 1
            max = amount / 50;
        }
        for (uint i = 1; i <= max;) {
            uint stampIndex;
            unchecked {
                stampIndex = soldTicketsAmount + 50 * i - 1;    
            } // i start from 1
            if (toIndex == stampIndex) break;
            buyer[raffleId][stampIndex] = msg.sender;
            unchecked {
                i++;
            }
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

    function chooseWinner(
        uint96 raffleId,
        uint salt
    ) external onlyOwner onlyRegistered(raffleId) lock {
        RaffleInfo storage raffleInfo = raffles[raffleId];
        uint80 soldTicketsAmount;
        unchecked {
            soldTicketsAmount =
                raffleInfo.totalTickets -
                raffleInfo.leftTickets;
        } // totalTickets >= leftTickets
        require(soldTicketsAmount > 0, "failed raffle");
        require(raffleInfo.winner == address(0), "winner already chosen");
        require(
            raffleInfo.endTime <= block.timestamp || raffleInfo.leftTickets <= 0,
            "not ended or not sold out"
        );

        // choose winner
        uint winnerTicketIndex = uint(
            keccak256(
                abi.encodePacked(block.prevrandao, block.timestamp, salt)
            )
        ) % soldTicketsAmount;
        address winner = getTicketOwnerByIndex(raffleId, winnerTicketIndex);
        raffleInfo.winner = winner;

        // fee calculation
        uint wholeSales = soldTicketsAmount * raffleInfo.ticketPrice;
        uint fee = (wholeSales * feeNumerator) / 1000;
        feeBox += uint240(fee); // fee cannot over uint240 max
        uint settlement;
        unchecked {
            settlement = wholeSales - fee;
        } // fee(max 30% of wholeSales)

        CreatorInfo storage creatorInfo = creatorInfo[raffleInfo.nftCa]; // not use memory for gas saving
        uint creatorFeeNumerator = creatorInfo.creatorFeeNumerator;
        if(creatorFeeNumerator > 0){
        uint creatorFee = (wholeSales * creatorFeeNumerator) / 1000;
            unchecked {
                settlement -= creatorFee;
            } // creatorFee(max 10% of wholeSales)
            (bool success, ) = creatorInfo.creator.call{value: creatorFee}("");
            require(success, "failed to transfer creatorFee");
        }

        // settlement
        claimableBalance[raffleInfo.seller] = settlement;
        claimableNft[winner].push(
            NftInfo(raffleId, raffleInfo.nftCa, raffleInfo.tokenId)
        );
        emit ChooseWinner(raffleId, winner, winnerTicketIndex, settlement);
    }

    function claimAllNfts() external {
        NftInfo[] storage nftInfoList = claimableNft[msg.sender]; // not use memory for gas saving
        uint length = nftInfoList.length; // for gas saving
        uint[] memory raffleIds = new uint[](length);
        require(length > 0, "no claimable nfts");
        for (uint i = 0; i < length;) {
            IERC721(nftInfoList[i].nftCa).safeTransferFrom(
                address(this),
                msg.sender,
                nftInfoList[i].tokenId
            );
            raffleIds[i] = nftInfoList[i].raffleId;
            unchecked {
                i++;
            }
        }
        delete claimableNft[msg.sender]; 
        emit ClaimAllNfts(msg.sender, raffleIds);
    }

    function claimNftByIndex(uint index) external {
        NftInfo[] storage nfts = claimableNft[msg.sender];
        uint length = nfts.length; // for gas saving
        require(index < length, "unclaimable index");
        NftInfo storage claimNftInfo = nfts[index];
        IERC721(claimNftInfo.nftCa).safeTransferFrom(
            address(this),
            msg.sender,
            claimNftInfo.tokenId
        );
        nfts[index] = nfts[length - 1];
        nfts.pop();
        emit ClaimNft(msg.sender, claimNftInfo.raffleId);
    }

    function claimNftForFailedSeller(
        uint96 raffleId
    ) external onlyRegistered(raffleId) {
        RaffleInfo storage raffleInfo = raffles[raffleId];
        require(raffleInfo.seller == msg.sender, "not seller");
        require(raffleInfo.endTime < block.timestamp, "not ended");
        require(
            raffleInfo.leftTickets == raffleInfo.totalTickets,
            "not failed raffle"
        );
        require(raffleInfo.winner == address(0), "already claimed");
        raffleInfo.winner = msg.sender;
        IERC721(raffleInfo.nftCa).safeTransferFrom(
            address(this),
            msg.sender,
            raffleInfo.tokenId
        );
        emit ClaimNft(msg.sender, raffleId);
    }

    function claimBalance(uint amount) external lock {
        uint balance = claimableBalance[msg.sender];
        require(balance >= amount, "not enough balance");
        claimableBalance[msg.sender] -= amount;
        (bool success, ) = msg.sender.call{value: amount}("");
        require(success, "transfer failed");
        emit ClaimBalance(msg.sender, amount, balance - amount);
    }

    function withdrawFee(uint amount) external onlyOwner lock {
        require(amount <= feeBox, "cannot withdraw more than feeBox");
        (bool success, ) = feeTo.call{value: amount}("");
        require(success, "transfer failed");
        emit WithdrawFee(feeTo, amount);
    }

    function getTicketOwnerByIndex(
        uint raffleId,
        uint index
    ) public view returns (address) {
        uint soldTicketsAmount;
        unchecked {
            soldTicketsAmount = raffles[raffleId].totalTickets - raffles[raffleId].leftTickets;
        } // totalTickets >= leftTickets
        require(index < soldTicketsAmount, "unsold index");
        for (uint i = index; i < soldTicketsAmount;) {
            address owner = buyer[raffleId][i];
            if (owner != address(0)) return owner;
            unchecked {
                i++;
            }
        }
        revert("internal logic error");
    }

    function getClaimableNftsLength(
        address owner
    ) external view returns (uint length) {
        length = claimableNft[owner].length;
    }

    function onERC721Received(
        address operator,
        address from,
        uint256 tokenId,
        bytes calldata data
    ) external pure returns (bytes4){
        return IERC721Receiver.onERC721Received.selector;
    }
}
