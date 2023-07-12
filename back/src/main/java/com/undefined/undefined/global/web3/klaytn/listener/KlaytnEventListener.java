package com.undefined.undefined.global.web3.klaytn.listener;

import com.undefined.undefined.global.web3.klaytn.service.KlaytnService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;

@Component
public class KlaytnEventListener {


    private static final String WEBSOCKET_URI = "wss://public-en-baobab.klaytn.net/ws";
    private static final String CONTRACT_ADDRESS = "0x381c7d85673f230de554097372e0FC4F14b5B0a6";

    private final WebSocketService webSocketService;
    private final Web3j web3j;

    @Autowired
    KlaytnService klaytnService;

    public KlaytnEventListener() throws ConnectException {
        webSocketService = new WebSocketService(WEBSOCKET_URI, false);
        webSocketService.connect();

        web3j = Web3j.build(webSocketService);

        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST,
                CONTRACT_ADDRESS);

        web3j.ethLogFlowable(filter).subscribe(this::handledEvent);

    }

    private void handledEvent(Log log) {
        String eventHash = log.getTopics().get(0);
        klaytnService.balancer(log, eventHash);
    }
}

