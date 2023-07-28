package com.undefined.undefined.domain.ticket.service;

import com.undefined.undefined.global.web3.klaytn.dto.BuyTicketsDto;

public interface TicketService {
    void saveTicketByEvent(BuyTicketsDto dto);
}
