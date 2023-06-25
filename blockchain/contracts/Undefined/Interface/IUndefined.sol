// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

interface IUndefined {
    struct RaffleInfo {
        uint tokenId;
        uint128 raffleId;
        uint128 ticketPrice;
        uint endTime;
        uint128 leftTickets;
        uint128 totalTickets;
        address nftCa;
        address seller;
        address winner;
    }
    struct NftInfo {
        // uint raffleId;
        uint tokenId;
        address nftCa;
    }

    event ClaimNft(address claimer, NftInfo nftInfo);
    event ClaimAllNfts(address claimer, NftInfo[] nftInfoList);
    event ClaimBalance(address indexed claimer, uint amount, uint afterBalance);
    event ClaimNftForFailedSeller(uint raffleId, address seller);
    event RegisterRaffle(RaffleInfo);
    event BuyTickets(
        uint indexed raffleId,
        address indexed buyer,
        uint fromIndex,
        uint toIndex,
        uint amount,
        uint128 leftTickets
    );
    event ChooseWinner(
        uint raffleId,
        address indexed winner,
        uint winnerTicketIndex,
        uint blockNumber
    );

    function raffles(uint raffleId) external view returns (uint tokenId, uint128 id, uint128 ticketPrice, uint endTime, uint128 leftTickets, uint128 totalTickets, address nftCa, address seller, address winner);
    function claimableNft(address nftCa, uint index) external view returns (uint tokenId, address ca);
    function claimableBalance(address owner) external view returns (uint balance);
    function getTicketOwnerByIndex(uint raffleId, uint index) external view returns (address owner);
    function getClaimableNftsLength(address owner) external view returns(uint length);

    function registerRaffle(address nftCa, uint tokenId, uint120 totalTickets, uint120 ticketPrice, uint day) external;
    function buyTickets(uint raffleId, uint amount) external payable;
    function chooseWinner(uint raffleId, uint randNum, address creator, uint creatorFee) external;
    function claimAllNfts() external;
    function claimNftByIndex(uint index) external;
    function claimNftForFailedSeller(uint raffleId) external;
}
