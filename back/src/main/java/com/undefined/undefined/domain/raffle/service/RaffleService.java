package com.undefined.undefined.domain.raffle.service;

import com.undefined.undefined.global.web3.klaytn.dto.RegisterRaffleDto;

public interface RaffleService {
    void saveRaffleByEvent(RegisterRaffleDto dto);
}
