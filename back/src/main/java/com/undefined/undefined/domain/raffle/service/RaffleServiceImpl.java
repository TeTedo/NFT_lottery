package com.undefined.undefined.domain.raffle.service;

import com.undefined.undefined.domain.raffle.repository.RaffleRepository;
import com.undefined.undefined.global.web3.klaytn.dto.RegisterRaffleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaffleServiceImpl implements  RaffleService{
    private final RaffleRepository raffleRepository;

    @Override
    public void saveRaffleByEvent(RegisterRaffleDto dto) {
        raffleRepository.save(dto.toRaffle());
    }
}
