package com.undefined.undefined.global.web3.klaytn.event.events;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public abstract class ClaimAllNftsEvent {
    private final Event event;

    private Address claimer;

    private DynamicArray<Uint96> raffleIds;

    public ClaimAllNftsEvent() {
        this.event =  new Event("ClaimAllNfts", Arrays.<TypeReference<?>>asList(
                new TypeReference<Address>() {},
                new TypeReference<DynamicArray<Uint96>>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {

        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        String claimer = nonIndexedData.get(0).getValue().toString();
        String raffleIds = nonIndexedData.get(1).getValue().toString();

        System.out.println(claimer);
        System.out.println(raffleIds);

        callBack();
    }

    public abstract void callBack();
}
