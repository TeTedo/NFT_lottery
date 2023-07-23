package com.undefined.undefined.global.web3.klaytn.events;

import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

@Component
public class ClaimAllNftsEvent {
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
        List<Type> decodedData = FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        this.claimer = (Address) decodedData.get(0);
        this.raffleIds = (DynamicArray<Uint96>) decodedData.get(1);

        callBack();
    }

    private void callBack() {

    }
}
