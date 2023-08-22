package com.undefined.undefined.global.web3.klaytn.dto;

import com.undefined.undefined.domain.raffle.model.Raffle;
import com.undefined.undefined.domain.ticket.model.Ticket;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyTicketsDto {
    private long raffleId;
    private int fromIndex;
    private int toIndex;
    private int leftTickets;
    private String buyer;
    private Raffle raffle;
    private int amount;

    @Builder
    public BuyTicketsDto(long raffleId, int fromIndex, int toIndex, int leftTickets,String buyer) {
        this.raffleId = raffleId;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.leftTickets = leftTickets;
        this.buyer = buyer;
        this.amount = toIndex - fromIndex + 1;
    }

    public Ticket toTicket(){
        return Ticket.builder()
                .raffle(raffle)
                .owner(buyer)
                .amount(amount)
                .build();
    }
}
