package com.undefined.undefined.global.web3.klaytn.events;

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

@Component
public class ListNftEvent {
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
        List<Type> decodedData = FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        this.nftCa = (Address) decodedData.get(0);
        this.creator = (Address) decodedData.get(1);
        this.creatorFeeNumerator = (Uint8) decodedData.get(2);

        System.out.println(this.nftCa);
        System.out.println(this.creator);
        System.out.println(this.creatorFeeNumerator);
        callBack();
    }

    private void callBack() {

    }
}
