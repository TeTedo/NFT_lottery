// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

interface Structs {
    struct RaffleInfo {
        address seller;
        uint128 ticketAmount;
        uint128 leftTicketAmount;
        uint256 ticketPrice;
        uint256 endTime;
        address[] buyers;
    }
    struct NftInfo {
        address ca;
        uint256 tokenId;
    }
}
