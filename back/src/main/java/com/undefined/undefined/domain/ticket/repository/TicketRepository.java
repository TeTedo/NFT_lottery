package com.undefined.undefined.domain.ticket.repository;

import com.undefined.undefined.domain.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
            SELECT t
            FROM Ticket t
            JOIN FETCH t.raffle r
            WHERE r.id = :raffleId
            AND t.owner = :owner
            """)
    Optional<Ticket> findByRaffleIdAndOwner(@Param("raffleId") Long raffleId, @Param("owner") String owner);
}
