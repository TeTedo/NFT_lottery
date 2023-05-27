// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./core/RaffleSale.sol";
import "./core/RaffleClaim.sol";

contract Raffle is RaffleSale, RaffleClaim {
    constructor() {
        _disableInitializers();
    }

    function initialize(
        uint256 maxTicketAmount_,
        uint256 minTicketPrice_,
        uint8 raffleFeePercentage_
    ) external initializer {
        __RaffleInfo_init(
            maxTicketAmount_,
            minTicketPrice_,
            raffleFeePercentage_
        );
        __Ownable_init();
        __ReentrancyGuard_init();
    }
}
