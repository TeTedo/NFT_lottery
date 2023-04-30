// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "@openzeppelin/contracts/access/Ownable.sol";

contract GlobalStates is Ownable {
    uint256 public maxTicketLimit;
    uint256 public minTicketPrice;

    constructor(uint256 maxTicketLimit_, uint256 minTicketPrice_) {
        maxTicketLimit = maxTicketLimit_;
        minTicketPrice = minTicketPrice_ * 1 ether;
    }

    struct RaffleInfo {
        address seller;
        uint256 ticketPrice;
        uint256 ticketAmount;
        uint256 endTime;
        address winner;
    }

    mapping(address => mapping(uint => RaffleInfo)) public raffles;

    function changeMaxTicketLimit(uint256 _limit) external onlyOwner {
        maxTicketLimit = _limit;
    }

    function changeMinTicketPrice(uint256 _limit) external onlyOwner {
        minTicketPrice = _limit * 1 ether;
    }
}
