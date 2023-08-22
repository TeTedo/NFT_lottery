package com.undefined.undefined.global.web3.klaytn.event.handler;

import com.undefined.undefined.domain.raffle.service.RaffleService;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimNftDto;
import com.undefined.undefined.global.web3.klaytn.event.events.ClaimNftEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClaimNftHandler extends ClaimNftEvent {
    private final RaffleService raffleService;

    @Override
    public void callBack(ClaimNftDto dto){
        raffleService.claimNftByEvent(dto);
    }
}
