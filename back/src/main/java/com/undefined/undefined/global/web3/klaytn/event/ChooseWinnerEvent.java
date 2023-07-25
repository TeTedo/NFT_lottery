package com.undefined.undefined.global.web3.klaytn.event;

import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public abstract class ChooseWinnerEvent {
    private final Event event;

    public ChooseWinnerEvent() {
        this.event =  new Event("ChooseWinner", Arrays.<TypeReference<?>>asList(
                new TypeReference<Uint>() {},
                new TypeReference<Address>(true) {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> indexedData = FunctionReturnDecoder.decode(log.getData(), event.getIndexedParameters());

        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        String raffleId = nonIndexedData.get(0).getValue().toString();
        String winner = indexedData.get(0).getValue().toString();
        String winnerTicketIndex = nonIndexedData.get(1).getValue().toString();
        String settlement = nonIndexedData.get(2).getValue().toString();

        System.out.println(raffleId);
        System.out.println(winner);
        System.out.println(winnerTicketIndex);
        System.out.println(settlement);

        callBack();
    }

    public abstract void callBack();
}
