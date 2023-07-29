package com.undefined.undefined.global.web3.klaytn.handler;

import com.undefined.undefined.domain.raffle.service.RaffleService;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimBalanceDto;
import com.undefined.undefined.global.web3.klaytn.event.ClaimBalanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClaimBalanceHandler extends ClaimBalanceEvent {
    private final RaffleService raffleService;

    @Override
    public void callBack(ClaimBalanceDto dto){
        raffleService.claimBalanceByEvent(dto);
    }
}
