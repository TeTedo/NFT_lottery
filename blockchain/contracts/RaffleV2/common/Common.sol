// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./States.sol";
import "./interface/Events.sol";

abstract contract Common is States, Events {
    modifier onlyRegistered(uint256 raffleId) {
        require(raffleId < _raffles.length, "unregisterd raffle ID");
        _;
    }

    modifier onlyListed(address nftCa) {
        require(_isListed[nftCa], "unlisted nft");
        _;
    }
}
