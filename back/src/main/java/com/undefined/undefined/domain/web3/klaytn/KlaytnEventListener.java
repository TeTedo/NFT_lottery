package com.undefined.undefined.domain.web3.klaytn;

import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.websocket.WebSocketService;

import java.math.BigInteger;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;

@Component
public class KlaytnEventListener {

    private static final String WEBSOCKET_URI = "wss://public-en-baobab.klaytn.net/ws";
    private static final String CONTRACT_ADDRESS = "0x5d73e59868C2EcC79c4A59920931d449346B8680";

    private final WebSocketService webSocketService;
    private final Web3j web3j;
    private BigInteger lastBlock = new BigInteger("0");
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
        String eventSignatureHash = EventEncoder.buildEventSignature("Test");

        event = new Event("Test", Arrays.<TypeReference<?>>asList(
                new TypeReference<Uint>() {
                },
                new TypeReference<Utf8String>() {
                },
                new TypeReference<Bool>() {
                },
                new TypeReference<Address>() {
                },
                new TypeReference<Bytes>() {
                },
                new TypeReference<DynamicArray<Uint>>() {
                },
                new TypeReference<Struct>() {
                },
                new TypeReference<Uint>() {} ));

        String MY_EVENT_HASH = EventEncoder.encode(event);

        System.out.println("hihi");
        System.out.println(eventHash + "  + " + eventSignatureHash);
        if(eventHash.equals(eventSignatureHash)){
            List<String> topics = log.getTopics();

            List<Type> nonIndexedValues = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

            // Access the decoded event values

            Utf8String arg2 = (Utf8String) nonIndexedValues.get(0);
            Bool arg3 = (Bool) nonIndexedValues.get(1);

            // Print the event values

            System.out.println("arg2: " + arg2.getValue());

        }



    }
}

