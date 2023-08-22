package com.undefined.undefined.global.scheduler;

import com.undefined.undefined.domain.raffle.service.RaffleService;
import com.undefined.undefined.global.web3.klaytn.service.KlaytnService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WebSocketConnectScheduler {

    private final KlaytnService klaytnService;

    @Scheduled(cron = "30 * * * * ?")
    public void chooseWinner() throws IOException {
        klaytnService.getBlockNumber();
    }
}
