// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./state/States.sol";
import "./interface/Events.sol";

abstract contract Common is States, Events {
    function _addCommissionBox(uint256 amount) internal {
        _commissionBox += amount;
    }

    function _setClaimInfo(
        address winner,
        address seller,
        NftInfo memory nft,
        uint256 settlement
    ) internal {
        _claimableNft[winner].push(nft);
        _claimableBalance[seller] = settlement;
    }

    function isListed(address nftCa) public view returns (bool) {
        return _listedNfts[nftCa];
    }
}
