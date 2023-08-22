package com.undefined.undefined.domain.ticket.model;

import com.undefined.undefined.domain.raffle.model.Raffle;
import com.undefined.undefined.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tickets")
@Getter
@NoArgsConstructor
public class Ticket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "raffle_id", nullable = false)
    private Raffle raffle;

    @Column(length = 66, nullable = false)
    private String owner;

    @Column(name = "token_uri", length = 255)
    private String tokenUri;

    @Column(nullable = false)
    private int amount;

    @Builder
    public Ticket(
            Long id, Raffle raffle, String owner, String tokenUri, int amount) {
        this.id = id;
        this.raffle = raffle;
        this.owner = owner;
        this.tokenUri = tokenUri;
        this.amount = amount;
    }

    public Ticket updateAmount(int boughtTicket) {
        this.amount += boughtTicket;
        return this;
    }
}
