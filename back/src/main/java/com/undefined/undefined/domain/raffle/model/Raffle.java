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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 66, nullable = false)
    private String ca;

    @Column(name = "token_id", nullable = false)
    private int tokenId;

    @Column(name = "token_uri", length = 255, nullable = true)
    private String tokenUri;

    @Column(length = 66, nullable = false)
    private String seller;

    @Column(length = 66, nullable = false)
    private String winner;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ColumnDefault("0")
    @Column(name = "is_end", nullable = false)
    private boolean isEnd;

    @ColumnDefault("0")
    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @Builder

    public Raffle(
            Long id, String ca, int tokenId, String tokenUri,
            String seller, String winner, LocalDateTime endTime,
            boolean isEnd, boolean isPaid) {

        this.id = id;
        this.ca = ca;
        this.tokenId = tokenId;
        this.tokenUri = tokenUri;
        this.seller = seller;
        this.winner = winner;
        this.endTime = endTime;
        this.isEnd = isEnd;
        this.isPaid = isPaid;
    }
}
