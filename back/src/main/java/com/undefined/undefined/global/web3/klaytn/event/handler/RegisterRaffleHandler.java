package com.undefined.undefined.global.web3.klaytn.event.handler;

import com.undefined.undefined.domain.raffle.service.RaffleService;
import com.undefined.undefined.global.web3.klaytn.dto.RegisterRaffleDto;
import com.undefined.undefined.global.web3.klaytn.event.events.RegisterRaffleEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterRaffleHandler extends RegisterRaffleEvent {
    private final RaffleService raffleService;
    @Override
    public void callBack(RegisterRaffleDto dto){
        raffleService.saveRaffleByEvent(dto);
    }
}
