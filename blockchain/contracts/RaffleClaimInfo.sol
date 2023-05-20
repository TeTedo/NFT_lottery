// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "./RaffleEnv.sol";

contract RaffleClaimInfo is RaffleEnv {
    struct NftInfo {
        address ca;
        uint256 tokenId;
        // uint128 tokenId; -> 나중에결정
    }
    mapping(address => NftInfo) private _claimableNft;
    mapping(address => uint256) private _claimableBalance;

    function _setClaimInfo(
        address winner,
        address seller,
        NftInfo memory nft,
        uint256 settlement
    ) internal {
        _claimableNft[winner] = nft;
        _claimableBalance[seller] = settlement;
    }
}
