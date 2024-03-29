package com.undefined.undefined.domain.web3.klaytn.event.events;

import com.undefined.undefined.domain.web3.klaytn.mapper.EventTypeMapper;
import com.undefined.undefined.domain.web3.klaytn.dto.ChooseWinnerDto;
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

public abstract class ChooseWinnerEvent {
    private final Event event;

    public ChooseWinnerEvent() {
        this.event =  new Event("ChooseWinner", Arrays.<TypeReference<?>>asList(
                new TypeReference<Uint>(true) {},
                new TypeReference<Address>(true) {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {

        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        Long raffleId = EventTypeMapper.toLongId(log.getTopics().get(1));
        String winner = EventTypeMapper.toAddress(log.getTopics().get(2));
        int winnerTicketIndex = Integer.parseInt(nonIndexedData.get(0).getValue().toString());
        double settlement = Double.parseDouble(nonIndexedData.get(1).getValue().toString()) / Math.pow(10,18);

        ChooseWinnerDto dto = ChooseWinnerDto.builder()
                .raffleId(raffleId)
                .winner(winner)
                .winnerTicketIndex(winnerTicketIndex)
                .settlement(settlement)
                .build();

        callBack(dto);
    }

    public abstract void callBack(ChooseWinnerDto dto);
}
