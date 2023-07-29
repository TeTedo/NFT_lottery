package com.undefined.undefined.domain.raffle.repository;

import com.undefined.undefined.domain.raffle.model.Raffle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Long> {
    Optional<Raffle> findById(Long id);

    @Query("""
            SELECT r
            FROM Raffle r
            WHERE r.seller = :seller
            """)
    List<Raffle> findAllBySeller(@Param("seller") String seller);
}
