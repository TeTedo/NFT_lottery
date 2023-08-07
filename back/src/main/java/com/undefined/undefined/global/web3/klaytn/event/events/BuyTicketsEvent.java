package com.undefined.undefined.global.web3.klaytn.event.events;

import com.undefined.undefined.global.web3.klaytn.dto.BuyTicketsDto;
import com.undefined.undefined.global.web3.klaytn.mapper.EventTypeMapper;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.protocol.core.methods.response.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public abstract class BuyTicketsEvent {
    private final Event event;

    public BuyTicketsEvent() {
        event = new Event("BuyTickets", Arrays.asList(
                new TypeReference<Uint>(true) {},
                new TypeReference<Address>(true) {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint128>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) throws IOException{
        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        long raffleId = EventTypeMapper.toIntegerId(log.getTopics().get(1));
        String buyer = EventTypeMapper.toAddress(log.getTopics().get(2));
        int fromIndex = Integer.parseInt(nonIndexedData.get(0).getValue().toString());
        int toIndex = Integer.parseInt(nonIndexedData.get(1).getValue().toString());
        int leftTickets = Integer.parseInt(nonIndexedData.get(2).getValue().toString());

        BuyTicketsDto dto = BuyTicketsDto.builder()
                .raffleId(raffleId)
                .fromIndex(fromIndex)
                .buyer(buyer)
                .toIndex(toIndex)
                .leftTickets(leftTickets)
                .build();

        callBack(dto);
    }

    public abstract void callBack(BuyTicketsDto dto) throws IOException;
}
