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

    @ManyToOne
    @JoinColumn(name = "raffle_id", nullable = false)
    private Raffle raffle;

    @Column(nullable = false)
    private double price;

    @Column(length = 66, nullable = false)
    private String owner;

    @Column(name = "token_uri", length = 255, nullable = false)
    private String tokenUri;

    @Builder

    public Ticket(
            Long id, Raffle raffle, double price,
            String owner, String tokenUri) {

        this.id = id;
        this.raffle = raffle;
        this.price = price;
        this.owner = owner;
        this.tokenUri = tokenUri;
    }
}
