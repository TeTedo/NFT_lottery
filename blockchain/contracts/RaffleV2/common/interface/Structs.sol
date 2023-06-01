// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

interface Structs {
    struct RaffleInfo {
        uint256 raffleId;
        uint256 tokenId;
        uint256 ticketPrice;
        uint256 endTime;
        uint128 leftTickets;
        uint128 totalTickets;
        address nftCa;
        address seller;
        address winner;
    }
    struct NftInfo {
        uint256 raffleId;
        uint256 tokenId;
        address ca;
    }
}
