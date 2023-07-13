package com.undefined.undefined.global.web3.klaytn.events;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

@Component
public class BuyTicketsEvent {
    private final Event event;
    private Uint raffleId;
    private Address buyer;
    private Uint fromIndex;
    private Uint toIndex;
    private Uint amount;
    private Uint128 leftTickets;

    public BuyTicketsEvent() {
        event = new Event("BuyTickets", Arrays.<TypeReference<?>>asList(
                new TypeReference<Uint>() {},
                new TypeReference<Address>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint128>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> decodedData = FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        raffleId = (Uint) decodedData.get(0);
        buyer = (Address) decodedData.get(1);
        fromIndex = (Uint) decodedData.get(2);
        toIndex = (Uint) decodedData.get(3);
        amount = (Uint) decodedData.get(4);
        leftTickets = (Uint128) decodedData.get(5);

        callBack();
    }

    private void callBack() {

    }
}
