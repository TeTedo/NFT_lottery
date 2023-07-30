package com.undefined.undefined.domain.raffle.mapper;

import com.undefined.undefined.domain.raffle.dto.response.MyRaffleResponse;
import com.undefined.undefined.domain.raffle.model.Raffle;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class RaffleMapper {
    public MyRaffleResponse toMyRaffleResponse(Raffle raffle) {
        return MyRaffleResponse.builder()
                .id(raffle.getId())
                .ca(raffle.getCa())
                .tokenId(raffle.getTokenId())
                .tokenUri(raffle.getTokenUri())
                .totalTicket(raffle.getTotalTicket())
                .leftTicket(raffle.getLeftTicket())
                .ticketPrice(raffle.getTicketPrice())
                .endTime(raffle.getEndTime())
                .isEnd(raffle.isEnd())
                .isPaid(raffle.isPaid())
                .settlement(raffle.getSettlement())
                .createdAt(raffle.getCreatedAt())
                .build();
    }

    public Page<MyRaffleResponse> toMyRaffleResponse(Page<Raffle> rafflePage) {
        return rafflePage.map(v -> toMyRaffleResponse(v));
    }
}
