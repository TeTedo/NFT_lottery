// SPDX-License-Identifier: MIT

pragma solidity 0.8.18;

interface IUndefined {
    event RegisterRaffle(
        uint indexed raffleId,
        uint tokenId,
        address nftCa,
        uint ticketPrice,
        uint totalTickets,
        uint endTime,
        address indexed seller
    );
    event BuyTickets(
        uint indexed raffleId,
        address indexed buyer,
        uint fromIndex,
        uint toIndex,
        uint leftTickets
    );
    event ChooseWinner(
        uint indexed raffleId,
        address indexed winner,
        uint winnerTicketIndex,
        uint settlement
    );
    event ClaimNft(address indexed claimer, uint indexed raffleId);
    event ClaimAllNfts(address indexed claimer, uint[] raffleIds);
    event ClaimBalance(address indexed claimer, uint amount, uint afterBalance);
    event WithdrawFee(address indexed to, uint amount);

    function raffles(uint id) external view returns (
        uint96 raffleId,
        uint144 ticketPrice,
        uint16 totalTickets,
        uint16 leftTickets,
        address nftCa,
        uint tokenId, 
        uint endTime,
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
        uint16 totalTickets,
        uint144 ticketPrice,
        uint day
    ) external;
    function buyTickets(uint96 raffleId, uint16 amount) external payable;
    function chooseWinner(uint96 raffleId, uint salt) external;
    function claimAllNfts() external;
    function claimNftByIndex(uint index) external;
    function claimNftForFailedSeller(uint96 raffleId) external;
    function withdrawFee(uint amount) external;
}
