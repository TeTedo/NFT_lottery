// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "../interface/Structs.sol";

abstract contract States is Structs {
    // info
    mapping(address => bool) internal _listedNfts;
    uint256 internal _maxTicketAmount;
    uint256 internal _minTicketPrice;
    uint8 internal _commissionPercentage; // 0 ~ 100
    uint256[10] private __gap_info;

    // claim
    mapping(address => NftInfo[]) internal _claimableNft;
    mapping(address => uint256) internal _claimableBalance;
    uint256 internal _commissionBox;
    uint256[10] private __gap_claim;

    // sale
    mapping(uint256 => RaffleInfo) internal _raffles; // raffleId => raffleInfo
    mapping(uint256 => mapping(uint256 => address)) internal _buyers; // raffleId => ticketIndex => buyer
    uint256 internal _raffleIndex; // 레플 등록때마다 1씩 증가

    /**
     * @dev This empty reserved space is put in place to allow future versions to add new
     * variables without shifting down storage in the inheritance chain.
     * See https://docs.openzeppelin.com/contracts/4.x/upgradeable#storage_gaps
     */
    uint256[21] private __gap;
}
