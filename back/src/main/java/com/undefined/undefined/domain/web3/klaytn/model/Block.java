package com.undefined.undefined.domain.web3.klaytn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name = "blocks")
@Getter
@NoArgsConstructor
public class Block {
    @Id
    @Column(name = "block_num", nullable = false)
    private BigInteger blockNum;

    @Builder
    public Block(BigInteger blockNum) {
        this.blockNum = blockNum;
    }
}
