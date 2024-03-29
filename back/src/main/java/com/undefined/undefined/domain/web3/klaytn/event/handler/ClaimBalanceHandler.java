package com.undefined.undefined.domain.web3.klaytn.event.handler;

import com.undefined.undefined.domain.web3.klaytn.event.events.ClaimBalanceEvent;
import com.undefined.undefined.domain.raffle.service.RaffleService;
import com.undefined.undefined.domain.web3.klaytn.dto.ClaimBalanceDto;
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
