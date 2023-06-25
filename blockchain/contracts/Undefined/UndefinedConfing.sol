// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";
import "./Interface/IUndefinedConfig.sol";

contract UndefinedConfig is IUndefinedConfig, OwnableUpgradeable {
    mapping(address => bool) public isListed;
    address public feeTo;
    // uint120 max 
    // 1329227995784915872.903807060280344575 18decimal
    // 1329227995784915872903807060280344575 wei
    // enough for ticket amount & ticket price
    uint120 public maxTicketAmount;
    uint120 public minTicketPrice; 
    uint16 public feeNumerator; // 0 ~ 1000

    function __UndefinedConfig_init(
        uint120 maxTicketAmount_,
        uint120 minTicketPrice_,
        uint16 feeNumerator_,
        address feeTo_
    ) internal onlyInitializing {
        __UndefinedConfig_init_unchained(
            maxTicketAmount_,
            minTicketPrice_,
            feeNumerator_,
            feeTo_
        );
    }

    modifier onlyListed(address nftCa) {
        require(isListed[nftCa], "unlisted nft");
        _;
    }

    function __UndefinedConfig_init_unchained(
        uint120 maxTicketAmount_,
        uint120 minTicketPrice_,
        uint16 feeNumerator_,
        address feeTo_
    ) internal onlyInitializing {
        maxTicketAmount = maxTicketAmount_;
        minTicketPrice = minTicketPrice_;
        feeNumerator = feeNumerator_;
        feeTo = feeTo_;
    }

    function setMaxTicketAmount(uint120 amount) external onlyOwner {
        maxTicketAmount = amount;
        emit SetMaxTicketAmount(amount);
    }

    function setMinTicketPrice(uint120 price) external onlyOwner {
        minTicketPrice = price;
        emit SetMinTicketPrice(price);
    }

    function setFeeNumerator(uint16 feeNumerator_) external onlyOwner {
        require(feeNumerator_ <= 1000, "between 0 ~ 1000");
        feeNumerator = feeNumerator_;
        emit SetFeeNumerator(feeNumerator_);
    }

    function setFeeTo(address feeTo_) external onlyOwner {
        require(feeTo != address(0), "feeTo address is zero address");
        feeTo = feeTo_;
    }

    function listNft(address nftCa) external onlyOwner {
        require(!isListed[nftCa], "Already listed NFT");
        isListed[nftCa] = true;
        emit ListNft(nftCa);
    }

    function deListNft(address nftCa) external onlyOwner onlyListed(nftCa) {
        delete isListed[nftCa];
        emit DeListNft(nftCa);
    }

    uint256[47] private __gap;
}
