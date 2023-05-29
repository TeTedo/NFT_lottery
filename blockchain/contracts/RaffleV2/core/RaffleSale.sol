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
    ) external {
        require(isListed(nftCa), "This nft is not listed");
        require(ticketPrice >= getMinTicketPrice(), "too low price");
        require(
            totalTickets > 0 || totalTickets <= getMaxTicketAmount(),
            "unproper ticket amount"
        );
        IERC721 nft = IERC721(nftCa);
        require(nft.getApproved(tokenId) == address(this), "not approved");

        nft.transferFrom(msg.sender, address(this), tokenId);
        uint256 raffleId = _raffleIndex;
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
        unchecked {
            _raffleIndex++;
        }
        emit RegisterRaffle(_raffles[raffleId]);
    }

    function buyTickets(uint256 raffleId, uint128 amount) external payable {
        RaffleInfo storage raffleInfo = _raffles[raffleId];
        require(raffleId < _raffleIndex, "unregisterd raffle ID");
        require(raffleInfo.endTime > block.timestamp, "raffle times up");
        require(raffleInfo.leftTickets >= amount, "not enough tickets");
        require(amount * raffleInfo.ticketPrice == msg.value, "improper money");

        uint256 soldTicketsAmount = raffleInfo.totalTickets -
            raffleInfo.leftTickets;
        uint256 fromIndex = soldTicketsAmount;
        uint256 toIndex = soldTicketsAmount + amount - 1;

        _buyers[raffleId][toIndex] = msg.sender;
        raffleInfo.leftTickets -= amount;
        emit BuyTickets(raffleId, msg.sender, fromIndex, toIndex, amount);
    }

    function chooseWinner(
        uint256 raffleId,
        uint256 randNum
    ) external onlyOwner {
        RaffleInfo storage raffleInfo = _raffles[raffleId];
        uint256 soldTicketsAmount = raffleInfo.totalTickets -
            raffleInfo.leftTickets;

        require(raffleId < _raffleIndex, "unregisterd raffle ID");
        require(raffleInfo.winner == address(0), "winner already chosen");
        require(soldTicketsAmount > 0, "failed raffle");
        require(
            raffleInfo.endTime < block.timestamp || raffleInfo.leftTickets <= 0,
            "not ended or not sold out"
        );

        uint256 randIndex = uint256(
            keccak256(
                abi.encodePacked(block.prevrandao, block.timestamp, randNum)
            )
        ) % soldTicketsAmount; // 블록정보로 난수생성하는거 위험...
        address winner = getTicketOwnerByIndex(raffleId, randIndex);
        raffleInfo.winner = winner;
        uint256 wholeSales = soldTicketsAmount * raffleInfo.ticketPrice;
        uint256 commission = (wholeSales * getCommissionPercentage()) / 100;
        uint256 settlement = wholeSales - commission;

        _setClaimInfo(
            winner,
            raffleInfo.seller,
            NftInfo(raffleInfo.nftCa, raffleInfo.tokenId),
            settlement
        );
        _addCommissionBox(commission);
        emit ChooseWinner(raffleInfo, randIndex, block.number);
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
        revert("something wrong"); // 바이너리 서치로 로직 변경
    }

    // function getRaffleList() external view returns (RaffleInfo[] memory) {
    //     return _rafflesList;
    // }
}
