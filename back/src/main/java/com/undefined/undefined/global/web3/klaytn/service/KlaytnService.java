package com.undefined.undefined.global.web3.klaytn.service;

import com.undefined.undefined.global.web3.klaytn.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Log;

@Service
@RequiredArgsConstructor
public class KlaytnService {
    private final BuyTicketsEvent buyTicketsEvent;
    private final ChooseWinnerEvent chooseWinnerEvent;
    private final ClaimAllNftsEvent claimAllNftsEvent;
    private final ClaimBalanceEvent claimBalanceEvent;
    private final ClaimNftEvent claimNftEvent;
    private final DeListNftEvent deListNftEvent;
    private final ListNftEvent listNftEvent;
    private final RegisterRaffleEvent registerRaffleEvent;

    public void eventBalancer(Log log, Object eventHash) {
        System.out.println("Event start!");
        if(eventHash.equals(buyTicketsEvent.getEventHash())) buyTicketsEvent.saveData(log);
        if(eventHash.equals(chooseWinnerEvent.getEventHash())) chooseWinnerEvent.saveData(log);
        if(eventHash.equals(claimAllNftsEvent.getEventHash())) claimAllNftsEvent.saveData(log);
        if(eventHash.equals(claimBalanceEvent.getEventHash())) claimBalanceEvent.saveData(log);
        if(eventHash.equals(claimNftEvent.getEventHash())) claimNftEvent.saveData(log);
        if(eventHash.equals(deListNftEvent.getEventHash())) deListNftEvent.saveData(log);
        if(eventHash.equals(listNftEvent.getEventHash())) listNftEvent.saveData(log);
        if(eventHash.equals(registerRaffleEvent.getEventHash())) registerRaffleEvent.saveData(log);
    }
}
