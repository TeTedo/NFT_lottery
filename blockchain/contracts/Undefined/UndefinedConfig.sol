// SPDX-License-Identifier: MIT

pragma solidity 0.8.18;

import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";
import "./Interface/IUndefinedConfig.sol";

contract UndefinedConfig is IUndefinedConfig, OwnableUpgradeable {
    struct CreatorInfo {
        address creator;
        uint8 creatorFeeNumerator; // 0 ~ 100
    }

    mapping(address => bool) public isListed;
    mapping(address => CreatorInfo) public creatorInfo; // nftCa => creatorInfo
    // slot 2
    uint16 public maxTicketAmount; // 65,535
    uint96 public minTicketPrice;
    // slot 3
    uint240 public feeBox;
    uint16 public feeNumerator; // 0 ~ 300
    // slot4
    address public feeTo;

    function __UndefinedConfig_init(
        uint16 feeNumerator_,
        uint16 maxTicketAmount_,
        uint96 minTicketPrice_,
        address feeTo_
    ) internal onlyInitializing {
        __UndefinedConfig_init_unchained(
            feeNumerator_,
            maxTicketAmount_,
            minTicketPrice_,
            feeTo_
        );
    }

    function __UndefinedConfig_init_unchained(
        uint16 feeNumerator_,
        uint16 maxTicketAmount_,
        uint96 minTicketPrice_,
        address feeTo_
    ) internal onlyInitializing {
        require(feeNumerator_ <= 300, "fee cannot over 30%");
        feeNumerator = feeNumerator_;
        maxTicketAmount = maxTicketAmount_;
        minTicketPrice = minTicketPrice_;
        feeTo = feeTo_;

    }

    modifier onlyListed(address nftCa) {
        require(isListed[nftCa], "unlisted nft");
        _;
    }

    modifier onlyEOA(address addr) {
        require(addr.code.length == 0, "creator address must be EOA"); // 악의적인 receive 함수가 있는 CA를 방지하기 위함
        _;
    }
    
     function setFeeNumerator(uint16 feeNumerator_) external onlyOwner {
        require(feeNumerator_ <= 300, "fee cannot over 30%");
        feeNumerator = feeNumerator_;
        emit SetFeeNumerator(feeNumerator_);
    }

    function setMaxTicketAmount(uint16 amount) external onlyOwner {
        maxTicketAmount = amount;
        emit SetMaxTicketAmount(amount);
    }

    function setMinTicketPrice(uint96 price) external onlyOwner {
        minTicketPrice = price;
        emit SetMinTicketPrice(price);
    }

    function setFeeTo(address feeTo_) external onlyOwner {
        feeTo = feeTo_;
    }

    function listNft(address nftCa) external onlyOwner {
        require(nftCa != address(0), "zero address");
        require(!isListed[nftCa], "Already listed NFT");
        isListed[nftCa] = true;
        emit ListNft(nftCa, address(0), 0);
    }

    function listNft(
        address nftCa,
        address creator,
        uint8 creatorFeeNumerator
    ) external onlyOwner onlyEOA(creator) {
        require(nftCa != address(0) && creator != address(0), "zero address");
        require(!isListed[nftCa], "Already listed NFT");
        isListed[nftCa] = true;
        if (creatorFeeNumerator > 0) {
            require(creatorFeeNumerator <= 100, "creator fee cannot over 10%");
            creatorInfo[nftCa].creatorFeeNumerator = creatorFeeNumerator;
        }
        emit ListNft(nftCa, creator, creatorFeeNumerator);
    }

    function deListNft(address nftCa) external onlyOwner onlyListed(nftCa) {
        delete isListed[nftCa];
        emit DeListNft(nftCa);
    }

    function setCreatorInfo(
        address nftCa,
        address creator,
        uint8 creatorFeeNumerator
    ) external onlyOwner onlyListed(nftCa) onlyEOA(creator) {
        require(creator != address(0), "zero address");
        require(creatorFeeNumerator <= 100, "creator fee cannot over 10%");
        creatorInfo[nftCa].creator = creator;
        creatorInfo[nftCa].creatorFeeNumerator = creatorFeeNumerator;
        emit SetCreatorInfo(creator, creatorFeeNumerator);
    }

    uint256[45] private __gap;
}
