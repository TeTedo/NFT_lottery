package com.undefined.undefined.global.web3.klaytn.event.handler;

import com.undefined.undefined.domain.ticket.service.TicketService;
import com.undefined.undefined.global.web3.klaytn.dto.BuyTicketsDto;
import com.undefined.undefined.global.web3.klaytn.event.events.BuyTicketsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BuyTicketsHandler extends BuyTicketsEvent {
    private final TicketService ticketService;

    @Override
    public void callBack(BuyTicketsDto dto) throws IOException {
        ticketService.saveTicketByEvent(dto);
    }
}
