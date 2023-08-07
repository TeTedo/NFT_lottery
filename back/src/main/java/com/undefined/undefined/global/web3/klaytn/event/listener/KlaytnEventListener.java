package com.undefined.undefined.global.web3.klaytn.event.listener;


import com.undefined.undefined.global.web3.klaytn.service.EventBalancer;
import com.undefined.undefined.global.web3.klaytn.service.KlaytnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;

@Component
public class KlaytnEventListener {

    private static final String CONTRACT_ADDRESS = "0xedE916cA2375F50aEaB50a9cCb92Bb69F8c37438";

    @Autowired
    EventBalancer eventBalancer;

    public KlaytnEventListener(Web3j web3jWebsocket) {

        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST,
                CONTRACT_ADDRESS);

        web3jWebsocket.ethLogFlowable(filter).subscribe(this::handledEvent);

    }

    private void handledEvent(Log log) {
        String eventHash = log.getTopics().get(0);
        eventBalancer.eventBalancer(log, eventHash);
    }
}

