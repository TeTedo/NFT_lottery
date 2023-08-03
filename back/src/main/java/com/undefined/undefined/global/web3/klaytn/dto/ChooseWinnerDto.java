package com.undefined.undefined.global.web3.klaytn.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChooseWinnerDto {
    private long raffleId;
    private String winner;
    private int winnerTicketIndex;
    private double settlement;

    @Builder
    public ChooseWinnerDto(long raffleId, String winner, int winnerTicketIndex, double settlement) {
        this.raffleId = raffleId;
        this.winner = winner;
        this.winnerTicketIndex = winnerTicketIndex;
        this.settlement = settlement;
    }
}
