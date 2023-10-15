package com.undefined.undefined.domain.ticket.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.undefined.undefined.domain.ticket.dto.response.TicketResponse;
import com.undefined.undefined.domain.ticket.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketMapper {
    private final ObjectMapper objectMapper;

    public TicketResponse toTicketResponse(Ticket ticket, String contractName) {
        TicketResponse ticketResponse = objectMapper.convertValue(ticket, TicketResponse.class);
        ticketResponse.setContractName(contractName);

        return ticketResponse;
    }
}
