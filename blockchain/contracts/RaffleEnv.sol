// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";
import "@openzeppelin/contracts/utils/Arrays.sol";

contract RaffleEnv is OwnableUpgradeable {
    uint256 private _maxTicketAmount;
    uint256 private _minTicketPrice;
    uint8 private _commissionPercentage; // 0 ~ 100
    address[] private _listedNfts;

    event SetMaxTicketAmount(uint256 amount);
    event SetMinTicketPrice(uint256 price);
    event SetCommissionPercentage(uint8 percentage);
    event ListNft(address nftCa);

    function __RaffleEnv_init(
        uint256 maxTicketAmount_,
        uint256 minTicketPrice_,
        uint8 raffleFeePercentage_
    ) internal onlyInitializing {
        __RaffleEnv_init_unchained(
            maxTicketAmount_,
            minTicketPrice_,
            raffleFeePercentage_
        );
    }

    function __RaffleEnv_init_unchained(
        uint256 maxTicketAmount_,
        uint256 minTicketPrice_,
        uint8 commissionPercentage_
    ) internal onlyInitializing {
        _maxTicketAmount = maxTicketAmount_;
        _minTicketPrice = minTicketPrice_;
        _commissionPercentage = commissionPercentage_;
    }

    function setMaxTicketAmount(uint256 amount) external onlyOwner {
        _maxTicketAmount = amount;
        emit SetMaxTicketAmount(amount);
    }

    function setMinTicketPrice(uint256 price) external onlyOwner {
        _minTicketPrice = price;
        emit SetMinTicketPrice(price);
    }

    function setCommisssionPercentage(uint8 percentage) external onlyOwner {
        _commissionPercentage = percentage;
        emit SetCommissionPercentage(percentage);
    }

    function listNft(address nftCa) external onlyOwner {
        require(!_isListed(nftCa), "Already listed NFT");
        _listedNfts.push(nftCa);
        emit ListNft(nftCa);
    }

    function getMaxTicketAmount() public view returns (uint256) {
        return _maxTicketAmount;
    }

    function getMinTicketPrice() public view returns (uint256) {
        return _minTicketPrice;
    }

    function getListedNft() public view returns (address[] memory) {
        return _listedNfts;
    }

    function getCommissionPercentage() public view returns (uint8) {
        return _commissionPercentage;
    }

    function _isListed(address nftCa) internal view returns (bool) {
        for (uint i = 0; i < _listedNfts.length; i++) {
            if (_listedNfts[i] == nftCa) return true;
        }
        return false;
    }

    /**
     * @dev This empty reserved space is put in place to allow future versions to add new
     * variables without shifting down storage in the inheritance chain.
     * See https://docs.openzeppelin.com/contracts/4.x/upgradeable#storage_gaps
     */
    uint256[46] private __gap;
}
