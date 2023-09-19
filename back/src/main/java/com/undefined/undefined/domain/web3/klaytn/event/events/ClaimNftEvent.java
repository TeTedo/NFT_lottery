package com.undefined.undefined.domain.web3.klaytn.event.events;

import com.undefined.undefined.domain.web3.klaytn.dto.ClaimNftDto;
import com.undefined.undefined.domain.web3.klaytn.mapper.EventTypeMapper;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.protocol.core.methods.response.Log;

import java.util.Arrays;

public abstract class ClaimNftEvent {
    private final Event event;

    private Address claimer;

    private Uint96 raffleId;

    public ClaimNftEvent() {
        this.event =  new Event("ClaimNft", Arrays.asList(
                new TypeReference<Address>(true) {},
                new TypeReference<Uint>(true) {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {

        String claimer = EventTypeMapper.toAddress(log.getTopics().get(1));
        Long raffleId = EventTypeMapper.toLongId(log.getTopics().get(2));

        ClaimNftDto dto = ClaimNftDto.builder()
                .claimer(claimer)
                .raffleId(raffleId)
                .build();

        callBack(dto);
    }

    public abstract void callBack(ClaimNftDto dto);
}
