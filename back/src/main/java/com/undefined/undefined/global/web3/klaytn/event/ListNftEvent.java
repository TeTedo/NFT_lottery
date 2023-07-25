package com.undefined.undefined.global.web3.klaytn.event;

import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public abstract class ListNftEvent {
    private final Event event;

    private Address nftCa;

    private Address creator;

    private Uint8 creatorFeeNumerator;

    public ListNftEvent() {
        this.event =  new Event("ListNft", Arrays.<TypeReference<?>>asList(
                new TypeReference<Address>() {},
                new TypeReference<Address>() {},
                new TypeReference<Uint8>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        String nftCa = nonIndexedData.get(0).getValue().toString();
        String creator = nonIndexedData.get(1).getValue().toString();
        String creatorFeeNumerator = nonIndexedData.get(2).getValue().toString();


        callBack();
    }

    public abstract void callBack();
}
