// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./RaffleInfo.sol";
import "@openzeppelin/contracts/interfaces/IERC721.sol";
import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";

contract RaffleSale is RaffleInfo {
    function registerRaffle(
        address nftCa,
        uint256 tokenId,
        uint128 ticketAmount,
        uint256 ticketPrice,
        uint256 day
    ) external {
        require(isListed(nftCa), "This nft is not listed");
        require(
            !_isRegisteredRaffle(nftCa, tokenId),
            "This nft is already on raffle"
        );
        require(ticketAmount > 0);
        require(
            ticketAmount <= getMaxTicketAmount(),
            "ticket amount outof range"
        );
        require(ticketPrice >= getMinTicketPrice(), "ticket price too small");
        IERC721 nft = IERC721(nftCa);
        require(nft.getApproved(tokenId) == address(this), "not approved");

        nft.transferFrom(msg.sender, address(this), tokenId);
        uint256 endTime = block.timestamp + day * 1 days;
        RaffleInfo memory newRaffleInfo = RaffleInfo(
            msg.sender,
            ticketAmount,
            ticketAmount,
            ticketPrice,
            endTime,
            new address[](ticketAmount)
        );
        _raffles[nftCa][tokenId] = newRaffleInfo;
        // _rafflesList.push(newRaffleInfo);
        emit RegisterRaffle(
            msg.sender,
            nftCa,
            tokenId,
            ticketAmount,
            ticketPrice,
            endTime
        );
    }

    function buyTickets(
        address nftCa,
        uint256 tokenId,
        uint128 amount
    ) external payable {
        RaffleInfo storage raffleInfo = _raffles[nftCa][tokenId];
        require(_isRegisteredRaffleByInfo(raffleInfo), "unregisterd raffle");
        require(raffleInfo.endTime > block.timestamp, "raffle times up");
        require(raffleInfo.leftTicketAmount >= amount, "not enough tickets");
        require(amount * raffleInfo.ticketPrice == msg.value, "improper money");

        uint256 soldTicketsAmount = raffleInfo.ticketAmount -
            raffleInfo.leftTicketAmount;
        uint256 fromIndex = soldTicketsAmount;
        uint256 toIndex = soldTicketsAmount + amount - 1;

        raffleInfo.buyers[toIndex] = msg.sender;
        raffleInfo.leftTicketAmount -= amount;
        emit BuyTickets(msg.sender, fromIndex, toIndex, amount);
    }

    function chooseWinner(
        address nftCa,
        uint256 tokenId,
        uint256 randNum
    ) external onlyOwner {
        RaffleInfo memory raffleInfo = _raffles[nftCa][tokenId];
        require(_isRegisteredRaffleByInfo(raffleInfo), "unregisterd raffle");
        uint256 soldTicketsAmount = raffleInfo.ticketAmount -
            raffleInfo.leftTicketAmount;
        require(soldTicketsAmount > 0, "failed raffle");
        require(
            raffleInfo.endTime < block.timestamp ||
                raffleInfo.leftTicketAmount <= 0,
            "not ended or not sold out"
        );

        uint256 randIndex = uint256(
            keccak256(
                abi.encodePacked(block.prevrandao, block.timestamp, randNum)
            )
        ) % soldTicketsAmount; // 블록정보로 난수생성하는거 위험...
        address winner = getTicketOwnerByIndex(nftCa, tokenId, randIndex);
        uint256 wholeSales = soldTicketsAmount * raffleInfo.ticketPrice;
        uint256 commission = (wholeSales * getCommissionPercentage()) / 100;
        uint256 settlement = wholeSales - commission;

        _setClaimInfo(
            winner,
            raffleInfo.seller,
            NftInfo(nftCa, tokenId),
            settlement
        );
        _addCommissionBox(commission);
        _deregisterRaffle(nftCa, tokenId);
        // emit ChooseWinner(winner, raffleInfo, block.number);
        emit ChooseWinner(
            winner,
            raffleInfo.seller,
            nftCa,
            tokenId,
            raffleInfo.ticketAmount,
            raffleInfo.leftTicketAmount,
            raffleInfo.ticketPrice,
            raffleInfo.endTime,
            block.number
        );
    }

    function getRaffleInfo(
        address nftCa,
        uint256 tokenId
    ) external view returns (RaffleInfo memory) {
        return _raffles[nftCa][tokenId];
    }

    function getTicketOwnerByIndex(
        address nftCa,
        uint256 tokenId,
        uint256 index
    ) public view returns (address) {
        RaffleInfo memory raffleInfo = _raffles[nftCa][tokenId];
        uint256 soldTicketsAmount = raffleInfo.ticketAmount -
            raffleInfo.leftTicketAmount;
        require(index < soldTicketsAmount, "unsold index");
        address[] memory buyers = raffleInfo.buyers;
        for (uint i = index; i < soldTicketsAmount; i++) {
            if (buyers[i] != address(0)) return buyers[i];
        }
        revert("something wrong");
    }

    // function getRaffleList() external view returns (RaffleInfo[] memory) {
    //     return _rafflesList;
    // }
}
