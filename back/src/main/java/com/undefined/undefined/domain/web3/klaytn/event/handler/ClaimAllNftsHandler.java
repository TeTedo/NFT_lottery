package com.undefined.undefined.domain.web3.klaytn.event.handler;

import com.undefined.undefined.domain.web3.klaytn.dto.ClaimAllNftsDto;
import com.undefined.undefined.domain.web3.klaytn.event.events.ClaimAllNftsEvent;
import com.undefined.undefined.domain.raffle.service.RaffleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClaimAllNftsHandler extends ClaimAllNftsEvent {

    private final RaffleService raffleService;
    @Override
    public void callBack(ClaimAllNftsDto dto){
        raffleService.claimAllNftByEvent(dto);
    }

}
