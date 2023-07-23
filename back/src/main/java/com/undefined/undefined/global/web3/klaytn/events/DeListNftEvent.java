package com.undefined.undefined.global.web3.klaytn.events;

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

public class DeListNftEvent {
    private final Event event;

    private Address nftCa;

    public DeListNftEvent(Event event) {
        this.event =  new Event("DeListNft", Arrays.<TypeReference<?>>asList(
                new TypeReference<Address>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> decodedData = FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        this.nftCa = (Address) decodedData.get(0);

        callBack();
    }

    private void callBack() {

    }
}
