package com.undefined.undefined.global.web3.klaytn.event;

import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public abstract class ClaimNftEvent {
    private final Event event;

    private Address claimer;

    private Uint96 raffleId;

    public ClaimNftEvent() {
        this.event =  new Event("ClaimNft", Arrays.asList(
                new TypeReference<Address>() {},
                new TypeReference<Uint96>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        String claimer = nonIndexedData.get(0).getValue().toString();
        String raffleId = nonIndexedData.get(1).getValue().toString();

        System.out.println("ClaimNft"+claimer);
        System.out.println("ClaimNft"+raffleId);

        callBack();
    }

    public abstract void callBack();
}
