package com.undefined.undefined.domain.web3.klaytn.repository;

import com.undefined.undefined.domain.web3.klaytn.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, BigInteger> {
    @Query("""
            SELECT b
            FROM Block b
            ORDER BY b.blockNum DESC
            LIMIT 1
            """)
    Optional<Block> findLatestBlockNum();

    @Modifying
    @Query("""
            UPDATE Block b
            SET b.blockNum = :blockNum
            """)
    void updateBlockNum(@Param("blockNum") BigInteger blockNum);
}
