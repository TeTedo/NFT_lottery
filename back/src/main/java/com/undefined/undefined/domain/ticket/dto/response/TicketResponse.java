package com.undefined.undefined.domain.ticket.dto.response;

import com.undefined.undefined.domain.raffle.model.Raffle;
import com.undefined.undefined.domain.ticket.model.Ticket;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TicketResponse {
    private Long id;
    private Raffle raffle;
    private String owner;
    private String tokenUri;
    private int amount;

    @Builder
    public TicketResponse(Long id, Raffle raffle, String owner, String tokenUri, int amount) {
        this.id = id;
        this.raffle = raffle;
        this.owner = owner;
        this.tokenUri = tokenUri;
        this.amount = amount;
    }

    public static TicketResponse of(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .raffle(ticket.getRaffle())
                .owner(ticket.getOwner())
                .tokenUri(ticket.getTokenUri())
                .amount(ticket.getAmount())
                .build();
    }
}
