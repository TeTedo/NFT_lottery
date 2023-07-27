package com.undefined.undefined.domain.raffle.repository;

import com.undefined.undefined.domain.raffle.model.Raffle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Long> {
}
