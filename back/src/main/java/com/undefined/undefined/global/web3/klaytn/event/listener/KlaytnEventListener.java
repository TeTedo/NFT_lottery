package com.undefined.undefined.global.web3.klaytn.event.listener;


import com.undefined.undefined.global.web3.klaytn.service.BlockService;
import com.undefined.undefined.global.web3.klaytn.service.EventBalancer;
import com.undefined.undefined.global.web3.klaytn.service.KlaytnServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;

import java.math.BigInteger;

@Component
@RequiredArgsConstructor
public class KlaytnEventListener {

    private static final String CONTRACT_ADDRESS = "0xedE916cA2375F50aEaB50a9cCb92Bb69F8c37438";

    private static BigInteger latestEventBlock;

    private final EventBalancer eventBalancer;

    private final BlockService blockService;

    private final Web3j web3jWebsocket;

    @Bean
    public void subscribeToEvents() {
        latestEventBlock = blockService.getLatestBlock();

        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(latestEventBlock),
                DefaultBlockParameterName.LATEST,
                CONTRACT_ADDRESS);

        web3jWebsocket.ethLogFlowable(filter).subscribe(this::handledEvent);
    }

    private void handledEvent(Log log) {
        String eventHash = log.getTopics().get(0);
        latestEventBlock = log.getBlockNumber();
        blockService.saveLatestBlock(latestEventBlock);
        eventBalancer.eventBalancer(log, eventHash);
    }
}

