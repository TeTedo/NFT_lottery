// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./RaffleInfo.sol";
import "@openzeppelin/contracts/interfaces/IERC721.sol";
import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";

contract RaffleSale is RaffleInfo {
    function registerRaffle(
        address nftCa,
        uint256 tokenId,
        uint128 totalTickets,
        uint256 ticketPrice,
        uint256 day
    ) external onlyListed(nftCa) {
        require(ticketPrice >= _minTicketPrice, "too low price");
        require(
            totalTickets > 0 && totalTickets <= _maxTicketAmount,
            "unproper ticket amount"
        );

        IERC721 nft = IERC721(nftCa);
        nft.transferFrom(msg.sender, address(this), tokenId);
        uint256 raffleId = _raffles.length;
        _raffles[raffleId] = RaffleInfo(
            raffleId,
            tokenId,
            ticketPrice,
            block.timestamp + day * 1 days,
            totalTickets,
            totalTickets,
            nftCa,
            msg.sender,
            address(0)
        );
        emit RegisterRaffle(_raffles[raffleId]);
    }

    function buyTickets(
        uint256 raffleId,
        uint128 amount
    ) external payable onlyRegistered(raffleId) {
        RaffleInfo storage raffleInfo = _raffles[raffleId];
        require(amount > 0, "improper amount");
        require(amount * raffleInfo.ticketPrice == msg.value, "improper money");
        require(raffleInfo.endTime > block.timestamp, "raffle times up");
        require(raffleInfo.leftTickets >= amount, "not enough tickets");

        unchecked {
            uint256 soldTicketsAmount = raffleInfo.totalTickets -
                raffleInfo.leftTickets; // totalTickets >= leftTickets
            uint256 fromIndex = soldTicketsAmount;
            uint256 toIndex = soldTicketsAmount + amount - 1; // amount > 0

            // for (uint i = 1; i <= (amount / 100); i++) {
            //     uint256 stampIndex = 100 * i - 1;
            //     if (toIndex - stampIndex <= 50) return;
            //     _buyers[raffleId][stampIndex] = msg.sender;
            // }
            // 구매자가 티켓 100개이상 구매하면 인덱스 100 단위마다 주인 등록하는 기능
            // getTicketOwnerByIndex 성능을 향상시켜주기 위함
            // 티켓 판매량이 한 만개정도 넘어가면 이거 있는게 좋을지도

            _buyers[raffleId][toIndex] = msg.sender;
            raffleInfo.leftTickets -= amount; // leftTickets >= 0
            emit BuyTickets(raffleId, msg.sender, fromIndex, toIndex, amount);
        }
    }

    function chooseWinner(
        uint256 raffleId,
        uint256 randNum
    ) external onlyOwner onlyRegistered(raffleId) {
        RaffleInfo storage raffleInfo = _raffles[raffleId];
        uint256 soldTicketsAmount;
        unchecked {
            soldTicketsAmount =
                raffleInfo.totalTickets -
                raffleInfo.leftTickets;
        } // totalTickets >= leftTickets

        require(soldTicketsAmount > 0, "failed raffle");
        require(raffleInfo.winner == address(0), "winner already chosen");
        require(
            raffleInfo.endTime < block.timestamp || raffleInfo.leftTickets <= 0,
            "not ended or not sold out"
        );

        uint256 winnerTicketIndex = uint256(
            keccak256(
                abi.encodePacked(block.prevrandao, block.timestamp, randNum)
            )
        ) % soldTicketsAmount; // 블록정보로 난수생성하는거 위험...
        address winner = getTicketOwnerByIndex(raffleId, winnerTicketIndex);
        raffleInfo.winner = winner;
        uint256 wholeSales = soldTicketsAmount * raffleInfo.ticketPrice;
        uint256 commission = (wholeSales * _commissionPercentage) / 100;
        uint256 settlement;
        unchecked {
            settlement = wholeSales - commission;
        }

        _setClaimInfo(
            winner,
            raffleInfo.seller,
            NftInfo(raffleId, raffleInfo.tokenId, raffleInfo.nftCa),
            settlement
        );
        _addCommissionBox(commission);
        emit ChooseWinner(raffleId, winner, winnerTicketIndex, block.number);
    }

    function getRaffleInfo(
        uint256 raffleId
    ) external view returns (RaffleInfo memory) {
        return _raffles[raffleId];
    }

    function getTicketOwnerByIndex(
        uint256 raffleId,
        uint256 index
    ) public view returns (address) {
        RaffleInfo memory raffleInfo = _raffles[raffleId];
        uint256 soldTicketsAmount = raffleInfo.totalTickets -
            raffleInfo.leftTickets;
        require(index < soldTicketsAmount, "unsold index");
        for (uint i = index; i < soldTicketsAmount; i++) {
            address owner = _buyers[raffleId][i];
            if (owner != address(0)) return owner;
        }
        revert("something wrong");
    }

    function _addCommissionBox(uint256 amount) internal {
        _commissionBox += amount;
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

    // function getRaffleList() external view returns (RaffleInfo[] memory) {
    //     return _rafflesList;
    // }
}
