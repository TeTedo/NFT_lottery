// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./states/States.sol";
import "./interface/Events.sol";

abstract contract Common is States, Events {
    function _deregisterRaffle(address nftCa, uint256 tokenId) internal {
        delete _raffles[nftCa][tokenId];
    }

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

    function _isRegisteredRaffle(
        address nftCa,
        uint256 tokenId
    ) internal view returns (bool) {
        return _raffles[nftCa][tokenId].seller != address(0);
    }

    function isListed(address nftCa) public view returns (bool) {
        return _listedNfts[nftCa];
    }
}
