package com.undefined.undefined.global.web3.klaytn.dto;

import com.undefined.undefined.domain.raffle.model.Raffle;
import lombok.Builder;

import java.time.LocalDateTime;

public class RegisterRaffleDto {
    private final Long raffleId;
    private final int tokenId;
    private final String nftCa;
    private final double ticketPrice;
    private final int totalTickets;
    private final LocalDateTime endTime;
    private final String seller;

    private final int leftTickets;

    @Builder
    public RegisterRaffleDto(
            Long raffleId, int tokenId, String nftCa, double ticketPrice,
            int totalTickets, LocalDateTime endTime, String seller) {
        this.raffleId = raffleId;
        this.tokenId = tokenId;
        this.nftCa = nftCa;
        this.ticketPrice = ticketPrice;
        this.totalTickets = totalTickets;
        this.endTime = endTime;
        this.seller = seller;
        this.leftTickets = totalTickets;
    }

    public Raffle toRaffle() {
        return Raffle.builder()
                .id(raffleId)
                .tokenId(tokenId)
                .ca(nftCa)
                .ticketPrice(ticketPrice)
                .totalTicket(totalTickets)
                .endTime(endTime)
                .seller(seller)
                .leftTicket(leftTickets)
                .build();
    }

}
