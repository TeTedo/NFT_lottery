package com.undefined.undefined.domain.web3.klaytn.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeListNftDto {
    private String ca;
    private String type;

    @Builder
    public DeListNftDto(String ca) {
        this.ca = ca;
        this.type = "Klaytn";
    }
}
