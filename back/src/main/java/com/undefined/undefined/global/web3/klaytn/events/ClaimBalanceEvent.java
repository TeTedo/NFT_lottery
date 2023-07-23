package com.undefined.undefined.global.web3.klaytn.events;

import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public class ClaimBalanceEvent {
    private final Event event;

    private Address claimer;

    private Uint amount;

    private Uint afterBalance;

    public ClaimBalanceEvent(Event event) {
        this.event =  new Event("ClaimBalance", Arrays.<TypeReference<?>>asList(
                new TypeReference<Address>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {})
        );
    }

    public void saveData(Log log) {
        List<Type> decodedData = FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        this.claimer = (Address) decodedData.get(0);
        this.amount = (Uint) decodedData.get(1);
        this.afterBalance = (Uint) decodedData.get(2);

        callBack();
    }

    private void callBack() {

    }
}
