package com.undefined.undefined.domain.ticket.mapper;

import com.undefined.undefined.domain.ticket.dto.response.TicketResponse;
import com.undefined.undefined.domain.ticket.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketResponse toTicketResponse(Ticket ticket, String contractName) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .raffle(ticket.getRaffle())
                .owner(ticket.getOwner())
                .tokenUri(ticket.getTokenUri())
                .amount(ticket.getAmount())
                .contractName(contractName)
                .build();
    }
}
