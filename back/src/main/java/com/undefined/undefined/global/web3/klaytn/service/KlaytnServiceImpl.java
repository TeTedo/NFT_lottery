package com.undefined.undefined.global.web3.klaytn.service;

import com.undefined.undefined.global.web3.klaytn.event.handler.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Log;

@Service
@Getter
@RequiredArgsConstructor
public class KlaytnServiceImpl implements KlaytnService{
    private final BuyTicketsHandler buyTicketsHandler;
    private final ChooseWinnerHandler chooseWinnerHandler;
    private final ClaimAllNftsHandler claimAllNftsHandler;
    private final ClaimBalanceHandler claimBalanceHandler;
    private final ClaimNftHandler claimNftHandler;
    private final DeListNftHandler deListNftHandler;
    private final ListNftHandler listNftHandler;
    private final RegisterRaffleHandler registerRaffleHandler;

    @Override
    public void eventBalancer(Log log, Object eventHash) {
        try{
            if(eventHash.equals(buyTicketsHandler.getEventHash())) buyTicketsHandler.saveData(log);
            if(eventHash.equals(chooseWinnerHandler.getEventHash())) chooseWinnerHandler.saveData(log);
            if(eventHash.equals(claimAllNftsHandler.getEventHash())) claimAllNftsHandler.saveData(log);
            if(eventHash.equals(claimBalanceHandler.getEventHash())) claimBalanceHandler.saveData(log);
            if(eventHash.equals(claimNftHandler.getEventHash())) claimNftHandler.saveData(log);
            if(eventHash.equals(deListNftHandler.getEventHash())) deListNftHandler.saveData(log);
            if(eventHash.equals(listNftHandler.getEventHash())) listNftHandler.saveData(log);
            if(eventHash.equals(registerRaffleHandler.getEventHash())) registerRaffleHandler.saveData(log);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void chooseWinner() {}
}
