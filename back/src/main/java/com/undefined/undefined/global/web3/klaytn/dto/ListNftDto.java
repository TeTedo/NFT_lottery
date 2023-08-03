package com.undefined.undefined.global.web3.klaytn.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ListNftDto {
    private String ca;
    private String type;

    @Builder
    public ListNftDto(String ca) {
        this.ca = ca;
        this.type = "Klaytn";
    }
}
