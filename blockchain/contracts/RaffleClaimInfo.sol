// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "./RaffleEnv.sol";
import "@openzeppelin/contracts/interfaces/IERC721.sol";
import "@openzeppelin/contracts-upgradeable/security/ReentrancyGuardUpgradeable.sol";

contract RaffleClaimInfo is RaffleEnv, ReentrancyGuardUpgradeable {
    struct NftInfo {
        address ca;
        uint256 tokenId;
        // uint128 tokenId; -> 나중에결정
    }
    mapping(address => NftInfo[]) private _claimableNft;
    mapping(address => uint256) private _claimableBalance;
    uint256 private _commissionBox;

    function claimNft(uint256 index) external {
        NftInfo memory nftInfo = _claimableNft[msg.sender][index];
        require(nftInfo.ca == address(0), "not claimable index"); // tokenId는 0인경우도 있어서 default(0)이랑 비교하는거 무의미

        delete _claimableNft[msg.sender];
        IERC721(nftInfo.ca).transferFrom(
            address(this),
            msg.sender,
            nftInfo.tokenId
        ); // transfer를 IERC721에 넣을까?
    }

    function claimBalance(uint256 amount) external nonReentrant {
        require(_claimableBalance[msg.sender] >= amount);
        _claimableBalance[msg.sender] -= amount;
        (bool success, ) = msg.sender.call{value: amount}("");
        if (!success) revert("send transaction failed");
    }

    function withdrawCommission(uint256 amount) external onlyOwner {
        require(_commissionBox >= amount, "not enough commssion balance");
        _commissionBox -= amount;
        (bool success, ) = msg.sender.call{value: amount}("");
        if (!success) revert("send transaction failed");
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

    function _addCommissionBox(uint256 amount) internal {
        _commissionBox += amount;
    }

    function getClaimableNfts(
        address user
    ) external view returns (NftInfo[] memory) {
        return _claimableNft[user];
    }

    function getClaimableBalance(address user) external view returns (uint256) {
        return _claimableBalance[user];
    }

    function getCommssionBoxBalance()
        external
        view
        onlyOwner
        returns (uint256)
    {
        return _commissionBox;
    }

    uint256[47] private __gap;
}