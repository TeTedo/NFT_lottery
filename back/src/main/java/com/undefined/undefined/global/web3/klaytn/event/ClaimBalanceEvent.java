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

public abstract class ClaimBalanceEvent {
    private final Event event;

    private Address claimer;

    private Uint amount;

    private Uint afterBalance;

    public ClaimBalanceEvent() {
        this.event =  new Event("ClaimBalance", Arrays.<TypeReference<?>>asList(
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

        String claimer = indexedData.get(0).getValue().toString();
        String amount = nonIndexedData.get(0).getValue().toString();
        String afterBalance = nonIndexedData.get(0).getValue().toString();

        System.out.println(claimer);
        System.out.println(amount);
        System.out.println(afterBalance);

        callBack();
    }

    public abstract void callBack();
}
