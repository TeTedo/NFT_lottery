package com.undefined.undefined.global.scheduler;

import com.undefined.undefined.domain.raffle.service.RaffleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EndRaffleScheduler {

    private final RaffleService raffleService;

    // TODO error handling
    @Scheduled(cron = "0 * * * * *")
    public void chooseWinner() throws IOException {
        raffleService.chooseWinner();
    }
}
