package com.undefined.undefined.domain.raffle.repository;

import com.undefined.undefined.domain.raffle.model.Raffle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            AND r.isPaid = false
            AND r.isEnd = true
            """)
    List<Raffle> findAllBySeller(@Param("seller") String seller);

    @Query("""
            SELECT r
            FROM Raffle r
            WHERE r.seller = :seller
            """)
    Page<Raffle> findBySellerAndPage(Pageable pageable, @Param("seller") String seller);

    @Query("""
            SELECT r
            FROM Raffle r
            """)
    Page<Raffle> findAllByPage(Pageable pageable);

    @Query("""
            SELECT r
            FROM Raffle r
            WHERE r.ca = :ca
            """)
    Page<Raffle> findByCaAndPage(Pageable pageable, @Param("ca") String ca);

    @Query("""
            SELECT r
            FROM Raffle r
            WHERE r.winner = :winner
            """)
    Page<Raffle> findByWinnerAndPage(Pageable pageable, @Param("winner") String winner);

    @Query("""
            SELECT r
            FROM Raffle r
            WHERE r.endTime <= now()
            AND r.isEnd = false
            """)
    List<Raffle> findEndRaffle();

    @Query("""
            SELECT r
            FROM Raffle r
            WHERE r.endTime > now()
            ORDER BY r.endTime DESC
            LIMIT 10
            """)
    List<Raffle> findDeadlineRaffles();

    @Query("""
            SELECT r
            FROM Raffle r
            WHERE r.leftTicket = ( 
                    SELECT MIN(a.leftTicket) 
                    FROM Raffle a
                    WHERE a.leftTicket != 0
                    AND a.endTime > now()
                    )
            LIMIT 1
            """)
    Optional<Raffle> findPopularRaffle();
}
