// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/interfaces/IERC721.sol";
import "@openzeppelin/contracts-upgradeable/security/ReentrancyGuardUpgradeable.sol";
import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";
import "./RaffleInfo.sol";
import "hardhat/console.sol";

contract RaffleClaim is RaffleInfo, ReentrancyGuardUpgradeable {
    function claimNft(uint256 index) external {
        NftInfo memory nftInfo = _claimableNft[msg.sender][index];
        require(nftInfo.ca != address(0), "not claimable index"); // tokenId는 0인경우도 있어서 default(0)이랑 비교하는거 무의미

        _claimableNft[msg.sender][index] = _claimableNft[msg.sender][
            _claimableNft[msg.sender].length - 1
        ];
        _claimableNft[msg.sender].pop();

        IERC721(nftInfo.ca).transferFrom(
            address(this),
            msg.sender,
            nftInfo.tokenId
        );
        emit ClaimNft(msg.sender, nftInfo);
    }

    function claimAllNfts() external {
        NftInfo[] memory nftInfoList = _claimableNft[msg.sender];
        delete _claimableNft[msg.sender];
        for (uint i = 0; i < nftInfoList.length; i++) {
            IERC721(nftInfoList[i].ca).transferFrom(
                address(this),
                msg.sender,
                nftInfoList[i].tokenId
            );
        }
        emit ClaimAllNfts(msg.sender, nftInfoList);
    }

    function claimBalance(uint256 amount) external nonReentrant {
        require(
            _claimableBalance[msg.sender] >= amount,
            "not enough claimable balance"
        );
        _claimableBalance[msg.sender] -= amount;
        (bool success, ) = msg.sender.call{value: amount}("");
        if (!success) revert("send transaction failed");
        emit ClaimBalance(msg.sender, amount, _claimableBalance[msg.sender]);
    }

    // 티켓이 하나도 안팔린 nft를 판매자가 스스로 수령해가는 함수
    function claimNftForFailedSeller(
        uint256 raffleId
    ) external onlyRegistered(raffleId) {
        RaffleInfo storage raffleInfo = _raffles[raffleId];
        require(raffleInfo.seller == msg.sender, "not seller");
        require(raffleInfo.endTime < block.timestamp, "not ended");
        require(raffleInfo.leftTickets == 0, "not failed raffle");
        require(raffleInfo.winner == address(0), "already claimed");
        raffleInfo.winner = msg.sender;
        IERC721(raffleInfo.nftCa).transferFrom(
            address(this),
            msg.sender,
            raffleInfo.tokenId
        );
        // emit ClaimNftForFailedSeller(raffleId, raffleInfo.seller);
    }

    function withdrawCommission(uint256 amount) external onlyOwner {
        require(_commissionBox >= amount, "not enough commssion balance");
        _commissionBox -= amount;
        (bool success, ) = msg.sender.call{value: amount}("");
        if (!success) revert("send transaction failed");
        emit WithdrawCommission(amount, _commissionBox);
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
}
