package com.undefined.undefined.global.web3.klaytn.events;

import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

@Component
public class ChooseWinnerEvent {
    private final Event event;

    private Uint raffleId;

    private Address winner;

    private Uint winnerTicketIndex;

    private Uint settlement;

    public ChooseWinnerEvent(Event event) {
        this.event =  new Event("ChooseWinner", Arrays.<TypeReference<?>>asList(
                new TypeReference<Uint>() {},
                new TypeReference<Address>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {})
        );
    }

    public void saveData(Log log) {
        List<Type> decodedData = FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        this.raffleId = (Uint) decodedData.get(0);
        this.winner = (Address) decodedData.get(1);
        this.winnerTicketIndex = (Uint) decodedData.get(2);
        this.settlement = (Uint) decodedData.get(3);

        callBack();
    }

    private void callBack() {

    }
}
