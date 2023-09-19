package com.undefined.undefined.domain.web3.klaytn.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;

@Getter
public class MultiChooseWinnerDto {

    private BigInteger raffleId;
    private BigInteger randNum;

    @Builder
    public MultiChooseWinnerDto(Long raffleId, int randNum) {
        this.raffleId = BigInteger.valueOf(raffleId);
        this.randNum = BigInteger.valueOf(randNum);
    }

}
