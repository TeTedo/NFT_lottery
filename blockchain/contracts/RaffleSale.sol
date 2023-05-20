// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "./RaffleClaimInfo.sol";
import "@openzeppelin/contracts/interfaces/IERC721.sol";

contract RaffleSale is RaffleClaimInfo {
    struct RaffleInfo {
        address seller;
        uint128 ticketAmount;
        uint128 lefTicketAmount;
        uint256 ticketPrice;
        uint256 endTime;
        address[] buyers;
    }

    mapping(address => mapping(uint256 => RaffleInfo)) private _raffles; // nftCa -> tokenId -> RaffleInfo

    // RaffleInfo[] private _rafflesList; // 테스트 편의성을 위한 임시값

    event RegisterRaffle(RaffleInfo raffleInfo);
    event BuyTickets(
        address buyer,
        uint256 fromIndex,
        uint256 toIndex,
        uint128 amount
    );
    event ChooseWinner(
        address winner,
        RaffleInfo raffleInfo,
        uint256 blockNumber
    );

    constructor() {
        _disableInitializers();
    }

    function initialize(
        uint256 maxTicketAmount_,
        uint256 minTicketPrice_,
        uint8 raffleFeePercentage_
    ) external initializer {
        __RaffleEnv_init(
            maxTicketAmount_,
            minTicketPrice_,
            raffleFeePercentage_
        );
        __Ownable_init();
    }

    function registerRaffle(
        address nftCa,
        uint256 tokenId,
        uint128 ticketAmount,
        uint256 ticketPrice,
        uint256 day
    ) external {
        require(_isListed(nftCa), "This nft is not listed");
        require(
            _raffles[nftCa][tokenId].seller == address(0),
            "This nft is already on raffle"
        );
        require(ticketAmount <= getMaxTicketAmount());
        require(ticketPrice >= getMinTicketPrice());

        IERC721 nft = IERC721(nftCa);
        nft.transferFrom(msg.sender, address(this), tokenId);

        uint256 endTime = block.timestamp + day * 1 days;
        RaffleInfo memory newRaffleInfo = RaffleInfo(
            msg.sender,
            ticketAmount,
            ticketAmount,
            ticketPrice,
            endTime,
            new address[](ticketAmount)
        );
        _raffles[nftCa][tokenId] = newRaffleInfo;
        // _rafflesList.push(newRaffleInfo);
        emit RegisterRaffle(newRaffleInfo);
    }

    function buyTickets(
        address nftCa,
        uint256 tokenId,
        uint128 amount
    ) external payable {
        RaffleInfo storage raffleInfo = _raffles[nftCa][tokenId];
        address[] storage buyers = raffleInfo.buyers;
        require(raffleInfo.endTime > block.timestamp, "raffle ended");
        require(raffleInfo.lefTicketAmount >= amount, "not enough tickets");
        require(amount * raffleInfo.ticketPrice == msg.value, "improper money");

        uint256 soldTicketsAmount = raffleInfo.ticketAmount -
            raffleInfo.lefTicketAmount;
        uint256 fromIndex = soldTicketsAmount;
        uint256 toIndex = soldTicketsAmount + amount - 1;

        buyers[toIndex] = msg.sender;
        raffleInfo.lefTicketAmount -= amount;
        emit BuyTickets(msg.sender, fromIndex, toIndex, amount);
    }

    function chooseWinner(address nftCa, uint256 tokenId) external {
        RaffleInfo memory raffleInfo = _raffles[nftCa][tokenId];
        require(
            raffleInfo.endTime < block.timestamp ||
                raffleInfo.lefTicketAmount <= 0,
            "raffle is not ended"
        );

        uint256 soldTicketsAmount = raffleInfo.ticketAmount -
            raffleInfo.lefTicketAmount;
        uint256 randIndex = uint(
            keccak256(abi.encodePacked(block.prevrandao, block.timestamp))
        ) % soldTicketsAmount;
        address winner = getTicketOwnerByIndex(nftCa, tokenId, randIndex);
        uint256 wholeSales = soldTicketsAmount * raffleInfo.ticketPrice;
        uint256 commission = (wholeSales * getCommissionPercentage()) / 100;
        uint256 settlement = wholeSales - commission;

        _deregisterRaffle(nftCa, tokenId);
        _setClaimInfo(
            winner,
            raffleInfo.seller,
            NftInfo(nftCa, tokenId),
            settlement
        );
        emit ChooseWinner(winner, raffleInfo, block.number);
    }

    function getRaffleInfo(
        address nftCa,
        uint256 tokenId
    ) external view returns (RaffleInfo memory) {
        return _raffles[nftCa][tokenId];
    }

    function getTicketOwnerByIndex(
        address nftCa,
        uint256 tokenId,
        uint256 index
    ) public view returns (address) {
        address[] memory buyers = _raffles[nftCa][tokenId].buyers;
        for (uint i = index; i < buyers.length; i++) {
            if (buyers[i] != address(0)) return buyers[i];
        }
        return address(0);
    }

    // function getRaffleList() external view returns (RaffleInfo[] memory) {
    //     return _rafflesList;
    // }

    function _deregisterRaffle(address nftCa, uint256 tokenId) internal {
        delete _raffles[nftCa][tokenId];
    }
}
