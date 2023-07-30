package com.undefined.undefined.domain.raffle.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyRaffleResponse {
    private Long id;
    private String ca;
    private int tokenId;
    private String tokenUri;
    private int totalTicket;
    private int leftTicket;
    private double ticketPrice;
    private LocalDateTime endTime;
    private boolean isEnd;
    private boolean isPaid;
    private double settlement;
    private LocalDateTime createdAt;

    @Builder
    public MyRaffleResponse(
            Long id, String ca, int tokenId, String tokenUri,
            int totalTicket, int leftTicket, double ticketPrice,
            LocalDateTime endTime, boolean isEnd, boolean isPaid,
            double settlement, LocalDateTime createdAt) {
        this.id = id;
        this.ca = ca;
        this.tokenId = tokenId;
        this.tokenUri = tokenUri;
        this.totalTicket = totalTicket;
        this.leftTicket = leftTicket;
        this.ticketPrice = ticketPrice;
        this.endTime = endTime;
        this.isEnd = isEnd;
        this.isPaid = isPaid;
        this.settlement = settlement;
        this.createdAt = createdAt;
    }
}
