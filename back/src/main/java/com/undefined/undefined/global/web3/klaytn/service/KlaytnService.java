package com.undefined.undefined.global.web3.klaytn.service;

import com.undefined.undefined.global.web3.klaytn.events.BuyTicketsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Log;

@Service
@RequiredArgsConstructor
public class KlaytnService {
    private final BuyTicketsEvent buyTicketsEvent;

    public void balancer(Log log, String eventHash) {
        if(eventHash.equals(buyTicketsEvent.getEventHash())) {
          buyTicketsEvent.saveData(log);
        }
    }
}
