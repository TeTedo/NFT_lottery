// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";
import "../common/Common.sol";

contract RaffleInfo is Common, OwnableUpgradeable {
    function __RaffleInfo_init(
        uint256 maxTicketAmount_,
        uint256 minTicketPrice_,
        uint8 raffleFeePercentage_
    ) internal onlyInitializing {
        __RaffleInfo_init_unchained(
            maxTicketAmount_,
            minTicketPrice_,
            raffleFeePercentage_
        );
    }

    function __RaffleInfo_init_unchained(
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
        require(percentage <= 100, "between 0 ~ 100");
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
}
