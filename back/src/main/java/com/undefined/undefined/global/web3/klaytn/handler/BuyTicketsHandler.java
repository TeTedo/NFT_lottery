package com.undefined.undefined.global.web3.klaytn.handler;

import com.undefined.undefined.domain.ticket.service.TicketService;
import com.undefined.undefined.global.web3.klaytn.dto.BuyTicketsDto;
import com.undefined.undefined.global.web3.klaytn.event.BuyTicketsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuyTicketsHandler extends BuyTicketsEvent {
    private final TicketService ticketService;

    @Override
    public void callBack(BuyTicketsDto dto){
        ticketService.saveTicketByEvent(dto);
    }
}
