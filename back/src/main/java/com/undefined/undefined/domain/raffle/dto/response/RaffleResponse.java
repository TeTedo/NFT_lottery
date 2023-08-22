package com.undefined.undefined.domain.raffle.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RaffleResponse {
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
    private boolean isClaimNft;
    private double settlement;
    private LocalDateTime createdAt;

    @Builder
    public RaffleResponse(
            Long id, String ca, int tokenId, String tokenUri,
            int totalTicket, int leftTicket, double ticketPrice,
            LocalDateTime endTime, boolean isEnd, boolean isPaid,
            boolean isClaimNft, double settlement, LocalDateTime createdAt) {
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
        this.isClaimNft = isClaimNft;
        this.settlement = settlement;
        this.createdAt = createdAt;
    }
}
