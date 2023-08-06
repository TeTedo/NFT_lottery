package com.undefined.undefined.global.web3.klaytn.event.events;

import com.undefined.undefined.global.web3.klaytn.dto.ClaimNftDto;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;
import java.util.List;

public abstract class ClaimNftEvent {
    private final Event event;

    private Address claimer;

    private Uint96 raffleId;

    public ClaimNftEvent() {
        this.event =  new Event("ClaimNft", Arrays.asList(
                new TypeReference<Address>() {},
                new TypeReference<Uint96>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        String claimer = nonIndexedData.get(0).getValue().toString();
        Long raffleId = Long.parseLong(nonIndexedData.get(1).getValue().toString());

        ClaimNftDto dto = ClaimNftDto.builder()
                .claimer(claimer)
                .raffleId(raffleId)
                .build();

        callBack(dto);
    }

    public abstract void callBack(ClaimNftDto dto);
}
