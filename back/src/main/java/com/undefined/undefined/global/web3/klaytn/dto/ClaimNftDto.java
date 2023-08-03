package com.undefined.undefined.global.web3.klaytn.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClaimNftDto {
    // TODO claimer 필요 없음
    private String claimer;
    private Long raffleId;

    @Builder
    public ClaimNftDto(String claimer, Long raffleId) {
        this.claimer = claimer;
        this.raffleId = raffleId;
    }
}
