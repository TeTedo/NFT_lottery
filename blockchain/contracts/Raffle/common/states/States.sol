// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "../interface/Structs.sol";

abstract contract States is Structs {
    // info
    uint256 internal _maxTicketAmount;
    uint256 internal _minTicketPrice;
    uint8 internal _commissionPercentage; // 0 ~ 100
    address[] internal _listedNfts;

    // claim
    mapping(address => NftInfo[]) internal _claimableNft;
    mapping(address => uint256) internal _claimableBalance;
    uint256 internal _commissionBox;

    // sale
    mapping(address => mapping(uint256 => RaffleInfo)) internal _raffles; // nftCa -> tokenId -> RaffleInfo
    // RaffleInfo[] private _rafflesList; // 테스트 편의성을 위한 임시값

    /**
     * @dev This empty reserved space is put in place to allow future versions to add new
     * variables without shifting down storage in the inheritance chain.
     * See https://docs.openzeppelin.com/contracts/4.x/upgradeable#storage_gaps
     */
    uint256[42] private __gap;
}
