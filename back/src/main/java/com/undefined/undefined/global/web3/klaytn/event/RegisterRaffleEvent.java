package com.undefined.undefined.global.web3.klaytn.event;

import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint80;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public abstract class RegisterRaffleEvent {
    private final Event event;

    public RegisterRaffleEvent() {
        this.event =  new Event("RegisterRaffle", Arrays.asList(
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Address>() {},
                new TypeReference<Uint128>() {},
                new TypeReference<Uint80>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Address>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        String raffleId = nonIndexedData.get(0).getValue().toString();
        String tokenId = nonIndexedData.get(1).getValue().toString();
        String nftCa = nonIndexedData.get(2).getValue().toString();
        String ticketPrice = nonIndexedData.get(3).getValue().toString();
        String totalTickets = nonIndexedData.get(4).getValue().toString();
        String endTime = nonIndexedData.get(5).getValue().toString();
        String seller = nonIndexedData.get(6).getValue().toString();

        System.out.println(raffleId);
        System.out.println(tokenId);
        System.out.println(nftCa);
        System.out.println(ticketPrice);
        System.out.println(totalTickets);
        System.out.println(endTime);
        System.out.println(seller);

        callBack();
    }

    public abstract void callBack();
}
