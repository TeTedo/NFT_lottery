// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;
import "./Structs.sol";

interface Events is Structs {
    event ClaimNft(address claimer, NftInfo nftInfo);
    event ClaimAllNfts(address claimer, NftInfo[] nftInfoList);
    event ClaimBalance(address claimer, uint256 amount, uint256 afterBalance);
    event WithdrawCommission(uint256 amount, uint256 afterBalance);
    event SetMaxTicketAmount(uint256 amount);
    event SetMinTicketPrice(uint256 price);
    event SetCommissionPercentage(uint8 percentage);
    event ListNft(address nftCa);
    event DeListNft(address nftCa);
    event RegisterRaffle(RaffleInfo);
    event BuyTickets(
        uint256 raffleId,
        address buyer,
        uint256 fromIndex,
        uint256 toIndex,
        uint128 amount
    );
    event ChooseWinner(RaffleInfo, uint256 ticketIndex, uint256 blockNumber);
}
