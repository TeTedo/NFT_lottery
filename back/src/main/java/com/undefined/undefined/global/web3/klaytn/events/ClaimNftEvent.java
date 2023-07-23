package com.undefined.undefined.global.web3.klaytn.events;

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

public class ClaimNftEvent {
    private final Event event;

    private Address claimer;

    private Uint96 raffleId;

    public ClaimNftEvent(Event event) {
        this.event =  new Event("ClaimNft", Arrays.<TypeReference<?>>asList(
                new TypeReference<Address>() {},
                new TypeReference<Uint96>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> decodedData = FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        this.claimer = (Address) decodedData.get(0);
        this.raffleId = (Uint96) decodedData.get(1);

        callBack();
    }

    private void callBack() {

    }
}
