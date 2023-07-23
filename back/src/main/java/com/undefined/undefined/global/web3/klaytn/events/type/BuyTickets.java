package com.undefined.undefined.global.web3.klaytn.events.type;

import lombok.Getter;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

@Getter
public abstract class BuyTickets {
    private final Event event;
    private Uint raffleId;
    private Address buyer;
    private Uint fromIndex;
    private Uint toIndex;
    private Uint128 leftTickets;

    public BuyTickets() {
        event = new Event("BuyTickets", Arrays.<TypeReference<?>>asList(
                new TypeReference<Uint>() {},
                new TypeReference<Address>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint128>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> decodedData = FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        this.raffleId = (Uint) decodedData.get(0);
        this.buyer = (Address) decodedData.get(1);
        this.fromIndex = (Uint) decodedData.get(2);
        this.toIndex = (Uint) decodedData.get(3);
        this.leftTickets = (Uint128) decodedData.get(4);

        callBack();
    }

    public abstract void callBack();
}
