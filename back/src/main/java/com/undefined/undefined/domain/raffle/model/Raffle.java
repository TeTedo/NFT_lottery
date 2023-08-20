package com.undefined.undefined.domain.raffle.model;

import com.undefined.undefined.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "raffles")
@Getter
@NoArgsConstructor
public class Raffle extends BaseTimeEntity {

    @Id
    private Long id;

    @Column(length = 66, nullable = false)
    private String ca;

    @Column(name = "token_id", nullable = false)
    private int tokenId;

    @Column(name = "token_uri", length = 255, nullable = true)
    private String tokenUri;

    @Column(name = "total_ticket", nullable = false)
    private int totalTicket;

    @Column(name = "left_ticket", nullable = false)
    private int leftTicket;

    @Column(name = "ticket_price", nullable = false)
    private double ticketPrice;

    @Column(length = 66, nullable = false)
    private String seller;

    @Column(length = 66)
    private String winner;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ColumnDefault("0")
    @Column(name = "is_end", nullable = false)
    private boolean isEnd;

    @ColumnDefault("0")
    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @ColumnDefault("0")
    @Column(name = "is_claim_nft", nullable = false)
    private boolean isClaimNft;

    @Column
    private double settlement;

    @Builder
    public Raffle(Long id, String ca, int tokenId, String tokenUri, int totalTicket, double ticketPrice, String seller, String winner, LocalDateTime endTime, boolean isEnd, boolean isPaid,
                  int leftTicket, double settlement, boolean isClaimNft) {
        this.id = id;
        this.ca = ca;
        this.tokenId = tokenId;
        this.tokenUri = tokenUri;
        this.totalTicket = totalTicket;
        this.ticketPrice = ticketPrice;
        this.seller = seller;
        this.winner = winner;
        this.endTime = endTime;
        this.isEnd = isEnd;
        this.isPaid = isPaid;
        this.isClaimNft = isClaimNft;
        this.leftTicket = leftTicket;
        this.settlement = settlement;
    }

    public Raffle updateLeftTicket(int boughtTicket) {
        this.leftTicket -= boughtTicket;
        return this;
    }

    public Raffle chooseWinner(String address, double settlement) {
        this.winner = address;
        this.settlement = settlement;
        this.isEnd = true;
        return this;
    }

    public Raffle claimNft() {
        this.isClaimNft = true;
        return this;
    }

    public Raffle claimBalance() {
        this.isPaid = true;
        return this;
    }

    public void endTimeRaffle() {
        this.endTime = LocalDateTime.now();
    }
}
