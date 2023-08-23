package com.undefined.undefined.global.web3.klaytn.event.listener;


import com.undefined.undefined.global.web3.klaytn.service.BlockService;
import com.undefined.undefined.global.web3.klaytn.service.EventBalancer;
import com.undefined.undefined.global.web3.klaytn.service.KlaytnService;
import com.undefined.undefined.global.web3.klaytn.service.KlaytnServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;

import java.math.BigInteger;

@Component
@RequiredArgsConstructor
public class KlaytnEventListener {

    private static final String CONTRACT_ADDRESS = "0x04e244d3835871Cb3F7e2040ECEe33E85Da5C2DD";

    private static BigInteger latestEventBlock;

    private final EventBalancer eventBalancer;

    private final BlockService blockService;

    private final Web3j web3jWebsocket;

    private final KlaytnService klaytnService;

    @Bean
    public void subscribeToEvents() {
        latestEventBlock = blockService.getLatestBlock();
        BigInteger curBlockNum = klaytnService.getBlockNumber();

        if(curBlockNum.subtract(latestEventBlock).compareTo(BigInteger.valueOf(5000)) >= 0){
            BigInteger endBlock = latestEventBlock.add(BigInteger.valueOf(4000));
            while (endBlock.compareTo(curBlockNum) <= 0) {
                EthFilter tempFilter = new EthFilter(
                        DefaultBlockParameter.valueOf(latestEventBlock),
                        DefaultBlockParameter.valueOf(endBlock),
                        CONTRACT_ADDRESS);

                web3jWebsocket.ethLogFlowable(tempFilter).subscribe(this::handledEvent);

                latestEventBlock = endBlock.add(BigInteger.ONE);
                endBlock = latestEventBlock.add(BigInteger.valueOf(4000));
            }
        }

        EthFilter finalFilter = new EthFilter(
                DefaultBlockParameter.valueOf(latestEventBlock),
                DefaultBlockParameterName.LATEST,
                CONTRACT_ADDRESS);
        web3jWebsocket.ethLogFlowable(finalFilter).subscribe(this::handledEvent);
    }

    @Transactional
    private void handledEvent(Log log) {
        String eventHash = log.getTopics().get(0);
        latestEventBlock = log.getBlockNumber();
        blockService.saveLatestBlock(latestEventBlock.add(BigInteger.ONE));
        eventBalancer.eventBalancer(log, eventHash);
    }
}

