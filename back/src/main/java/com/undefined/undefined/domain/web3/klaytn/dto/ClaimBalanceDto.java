package com.undefined.undefined.domain.web3.klaytn.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClaimBalanceDto {
    private String claimer;
    private double amount;
    private double afterBalance;

    // TODO afterBalance, price 필요 없어보임
    @Builder
    public ClaimBalanceDto(String claimer, double amount, double afterBalance) {
        this.claimer = claimer;
        this.amount = amount;
        this.afterBalance = afterBalance;
    }
}
