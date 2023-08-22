package com.undefined.undefined.domain.ticket.service;

import com.undefined.undefined.domain.ticket.dto.request.GetMyTicketsRequest;
import com.undefined.undefined.domain.ticket.dto.response.TicketResponse;
import com.undefined.undefined.global.web3.klaytn.dto.BuyTicketsDto;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface TicketService {
    void saveTicketByEvent(BuyTicketsDto dto);

    Page<TicketResponse> getMyTickets(GetMyTicketsRequest request);
}
