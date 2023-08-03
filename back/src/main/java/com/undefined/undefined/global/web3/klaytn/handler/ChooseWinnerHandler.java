package com.undefined.undefined.global.web3.klaytn.handler;

import com.undefined.undefined.domain.raffle.service.RaffleService;
import com.undefined.undefined.global.web3.klaytn.dto.ChooseWinnerDto;
import com.undefined.undefined.global.web3.klaytn.event.ChooseWinnerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChooseWinnerHandler extends ChooseWinnerEvent {
    private final RaffleService raffleService;

    @Override
    public void callBack(ChooseWinnerDto dto){
        raffleService.chooseWinnerByEvent(dto);
    }
}
