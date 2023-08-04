package com.undefined.undefined.global.web3.klaytn.listener;


import com.undefined.undefined.global.web3.klaytn.service.KlaytnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.websocket.WebSocketService;

import java.math.BigInteger;
import java.net.ConnectException;

@Component
public class KlaytnEventListener {


    private static final String WEBSOCKET_URI = "wss://archive-en.baobab.klaytn.net/ws";
    private static final String CONTRACT_ADDRESS = "0xedE916cA2375F50aEaB50a9cCb92Bb69F8c37438";

    private WebSocketService webSocketService;
    private Web3j web3j;

    @Autowired
    KlaytnService klaytnService;

    public KlaytnEventListener() throws ConnectException {

        webSocketService = new WebSocketService(WEBSOCKET_URI, false);
        webSocketService.connect();
//            webSocketService.connect("websocket 연결 끊김", "","");

        web3j = Web3j.build(webSocketService);

        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST,
                CONTRACT_ADDRESS);

        web3j.ethLogFlowable(filter).subscribe(this::handledEvent);

    }

    private void handledEvent(Log log) {
        String eventHash = log.getTopics().get(0);
        klaytnService.eventBalancer(log, eventHash);
    }
}

