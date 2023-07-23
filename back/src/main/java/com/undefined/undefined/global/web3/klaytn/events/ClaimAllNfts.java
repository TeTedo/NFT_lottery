package com.undefined.undefined.global.web3.klaytn.events;

import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public class ClaimAllNfts {
    private final Event event;

    private Address claimer;

    private DynamicArray<Uint96> raffleIds;

    public ClaimAllNfts(Event event) {
        this.event =  new Event("ClaimAllNfts", Arrays.<TypeReference<?>>asList(
                new TypeReference<Address>() {},
                new TypeReference<DynamicArray<Uint96>>() {})
        );
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
