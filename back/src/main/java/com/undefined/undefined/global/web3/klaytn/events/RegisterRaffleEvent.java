package com.undefined.undefined.global.web3.klaytn.events;

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

public class RegisterRaffleEvent {
    private final Event event;

    private Uint tokenId;

    private Uint96 raffleId;

    private Uint128 ticketPrice;

    private Uint endTime;

    private Uint80 totalTickets;

    private Address nftCa;

    private Uint80 leftTickets;

    private Address seller;

    private Address winner;

    public RegisterRaffleEvent(Event event) {
        this.event =  new Event("RegisterRaffle", Arrays.<TypeReference<?>>asList(
                new TypeReference<Uint>() {},
                new TypeReference<Uint96>() {},
                new TypeReference<Uint128>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint80>() {},
                new TypeReference<Address>() {},
                new TypeReference<Uint80>() {},
                new TypeReference<Address>() {},
                new TypeReference<Address>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> decodedData = FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        this.tokenId = (Uint) decodedData.get(0);
        this.raffleId = (Uint96) decodedData.get(1);
        this.ticketPrice = (Uint128) decodedData.get(2);
        this.endTime = (Uint) decodedData.get(3);
        this.totalTickets = (Uint80) decodedData.get(4);
        this.nftCa = (Address) decodedData.get(5);
        this.leftTickets = (Uint80) decodedData.get(6);
        this.seller = (Address) decodedData.get(7);
        this.winner = (Address) decodedData.get(8);





        callBack();
    }

    private void callBack() {

    }
}
