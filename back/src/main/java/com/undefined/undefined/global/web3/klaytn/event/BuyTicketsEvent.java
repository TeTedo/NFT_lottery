package com.undefined.undefined.global.web3.klaytn.event;

import lombok.Getter;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public abstract class BuyTicketsEvent {
    private final Event event;

    public BuyTicketsEvent() {
        event = new Event("BuyTickets", Arrays.asList(
                new TypeReference<Uint>(true) {},
                new TypeReference<Address>(true) {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint128>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {

        List<Type> indexedData = FunctionReturnDecoder.decode(log.getData(), event.getIndexedParameters());

        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        String raffleId = indexedData.get(0).getValue().toString();
        String buyer = indexedData.get(1).getValue().toString();
        String fromIndex = nonIndexedData.get(0).getValue().toString();
        String toIndex = nonIndexedData.get(1).getValue().toString();
        String leftTickets = nonIndexedData.get(2).getValue().toString();

        System.out.println(raffleId);
        System.out.println(buyer);
        System.out.println(fromIndex);
        System.out.println(toIndex);
        System.out.println(leftTickets);

        callBack();
    }

    public abstract void callBack();
}
