// SPDX-License-Identifier: MIT

pragma solidity 0.8.18;

interface IUndefined {
    struct RaffleInfo {
        uint tokenId;
        uint96 raffleId;
        uint128 ticketPrice;
        uint endTime;
        uint80 totalTickets;
        address nftCa;
        uint80 leftTickets;
        address seller;
        address winner;
    }
    struct NftInfo {
        uint96 raffleId;
        address nftCa;
        uint tokenId;
    }

    event RegisterRaffle(
        uint raffleId,
        uint tokenId,
        address nftCa,
        uint128 ticketPrice,
        uint80 totalTickets,
        uint endTime,
        address seller
    );
    event BuyTickets(
        uint indexed raffleId,
        address indexed buyer,
        uint fromIndex,
        uint toIndex,
        uint128 leftTickets
    );
    event ChooseWinner(
        uint raffleId,
        address indexed winner,
        uint winnerTicketIndex,
        uint settlement
    );
    event ClaimNft(address claimer, uint96 raffleId);
    event ClaimAllNfts(address claimer, uint96[] raffleIds);
    event ClaimBalance(address indexed claimer, uint amount, uint afterBalance);
    // event ClaimNftForFailedSeller(uint raffleId, address seller);
    event WithdrawFee(address indexed to, uint amount);

    function raffles(uint id) external view returns (
            uint tokenId,
            uint96 raffleId,
            uint128 ticketPrice,
            uint endTime,
            uint80 totalTickets,
            address nftCa,
            uint80 leftTickets,
            address seller,
            address winner
    );
    function buyer(uint raffleId, uint ticketIndex) external view returns (address);
    function claimableNft(address owner, uint index) external view returns (uint96 raffleId, address ca, uint tokenId);
    function claimableBalance(address owner) external view returns (uint);
    function getTicketOwnerByIndex(uint raffleId, uint index) external view returns (address);
    function getClaimableNftsLength(address owner) external view returns (uint);
    function registerRaffle(
        address nftCa,
        uint tokenId,
        uint80 totalTickets,
        uint120 ticketPrice,
        uint day
    ) external;
    function buyTickets(uint96 raffleId, uint80 amount) external payable;
    function chooseWinner(uint96 raffleId, uint randNum) external;
    function claimAllNfts() external;
    function claimNftByIndex(uint index) external;
    function claimNftForFailedSeller(uint96 raffleId) external;
    function withdrawFee(address to, uint amount) external;
}
