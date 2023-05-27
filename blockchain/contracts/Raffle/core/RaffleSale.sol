// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./RaffleInfo.sol";
import "@openzeppelin/contracts/interfaces/IERC721.sol";
import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";

// 1. registerRaffle을 통해 레플등록 -> _raffles에 RaffleInfo 등록됨
// 2. buyTickets를 통해 티켓 구매(레플 참여)가능 -> batch로 구매
// 3. 경매시간 마감 혹은 티켓이 다 팔린 경우 chooseWinner실행 -> 이건 일단 백서버에서 owner 계정으로 실행시킨다는 전제로 구성
// chooseWinner로직에서 랜덤으로 winner를 선발한뒤 _raffles에서 해당 RaffleInfo 삭제(어차피 DB에 저장하고 있을꺼고 event에서 제공되는 블록넘버로 언제던지 블록체인 히스토리 열람 가능)

contract RaffleSale is RaffleInfo {
    function registerRaffle(
        address nftCa,
        uint256 tokenId,
        uint128 ticketAmount,
        uint256 ticketPrice,
        uint256 day
    ) external {
        require(_isListed(nftCa), "This nft is not listed");
        require(
            !_isRegisteredRaffle(nftCa, tokenId),
            "This nft is already on raffle"
        );
        require(ticketAmount > 0);
        require(ticketAmount <= getMaxTicketAmount());
        require(ticketPrice >= getMinTicketPrice());
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
        emit RegisterRaffle(newRaffleInfo);
    }

    function buyTickets(
        address nftCa,
        uint256 tokenId,
        uint128 amount
    ) external payable {
        require(_isRegisteredRaffle(nftCa, tokenId), "unregisterd raffle");
        RaffleInfo storage raffleInfo = _raffles[nftCa][tokenId];
        require(raffleInfo.endTime > block.timestamp, "raffle times up");
        require(raffleInfo.lefTicketAmount >= amount, "not enough tickets");
        require(amount * raffleInfo.ticketPrice == msg.value, "improper money");

        address[] storage buyers = raffleInfo.buyers;
        uint256 soldTicketsAmount = raffleInfo.ticketAmount -
            raffleInfo.lefTicketAmount;
        uint256 fromIndex = soldTicketsAmount;
        uint256 toIndex = soldTicketsAmount + amount - 1;

        buyers[toIndex] = msg.sender;
        raffleInfo.lefTicketAmount -= amount;
        emit BuyTickets(msg.sender, fromIndex, toIndex, amount);
    }

    function chooseWinner(
        address nftCa,
        uint256 tokenId,
        uint256 randNum
    ) external onlyOwner {
        require(_isRegisteredRaffle(nftCa, tokenId), "unregisterd raffle");
        RaffleInfo storage raffleInfo = _raffles[nftCa][tokenId];
        require(
            raffleInfo.ticketAmount - raffleInfo.lefTicketAmount > 0,
            "failed raffle"
        );
        require(
            raffleInfo.endTime < block.timestamp ||
                raffleInfo.lefTicketAmount <= 0,
            "not ended or not sold out"
        );

        uint256 soldTicketsAmount = raffleInfo.ticketAmount -
            raffleInfo.lefTicketAmount;
        uint256 randIndex = uint(
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

        emit ChooseWinner(winner, raffleInfo, block.number);
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
        address[] memory buyers = _raffles[nftCa][tokenId].buyers;
        for (uint i = index; i < buyers.length; i++) {
            if (buyers[i] != address(0)) return buyers[i];
        }
        return address(0);
    }

    // function getRaffleList() external view returns (RaffleInfo[] memory) {
    //     return _rafflesList;
    // }
}
