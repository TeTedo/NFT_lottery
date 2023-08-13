package com.undefined.undefined.global.web3.klaytn.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClaimAllNftsDto {
    private String claimer;
    private Long[] raffleIds;

    @Builder
    public ClaimAllNftsDto(String claimer, Long[] raffleIds) {
        this.claimer = claimer;
        this.raffleIds = raffleIds;
    }
}
