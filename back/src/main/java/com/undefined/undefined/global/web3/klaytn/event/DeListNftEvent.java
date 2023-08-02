package com.undefined.undefined.global.web3.klaytn.event;

import com.undefined.undefined.global.web3.klaytn.dto.DeListNftDto;
import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public abstract class DeListNftEvent {
    private final Event event;

    public DeListNftEvent() {
        this.event =  new Event("DeListNft", Arrays.<TypeReference<?>>asList(
                new TypeReference<Address>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        String nftCa = nonIndexedData.get(0).getValue().toString();

        DeListNftDto dto = DeListNftDto.builder()
                .ca(nftCa)
                .build();

        callBack(dto);
    }

    public abstract void callBack(DeListNftDto dto);
}
