package com.undefined.undefined.global.web3.klaytn;

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
    private Event event;

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

        event = new Event("Test", Arrays.<TypeReference<?>>asList(
                new TypeReference<Uint>() {
                },
                new TypeReference<Utf8String>() {
                },new TypeReference<Uint>() {
                }
              ));

        String MY_EVENT_HASH = EventEncoder.encode(event);

        if(eventHash.equals(MY_EVENT_HASH)){
            List<Type> decodeData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

            Uint _num1 = (Uint) decodeData.get(0);
            Utf8String _str = (Utf8String) decodeData.get(1);
            Uint _num2 = (Uint) decodeData.get(2);

            System.out.println(decodeData.get(0).getValue());
            System.out.println(_num1.getValue());
            System.out.println(_str);
            System.out.println(_num2.getValue());


        }
    }
}

