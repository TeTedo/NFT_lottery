package com.undefined.undefined.global.web3.klaytn.event.events;

import com.undefined.undefined.global.web3.klaytn.dto.ClaimAllNftsDto;
import com.undefined.undefined.global.web3.klaytn.mapper.EventTypeMapper;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.protocol.core.methods.response.Log;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public abstract class ClaimAllNftsEvent {
    private final Event event;

    public ClaimAllNftsEvent() {
        this.event =  new Event("ClaimAllNfts", Arrays.<TypeReference<?>>asList(
                new TypeReference<Address>(true) {},
                new TypeReference<DynamicArray<Uint>>() {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {

        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        String claimer = EventTypeMapper.toAddress(log.getTopics().get(1));
        DynamicArray<Uint> raffleIdsArray = (DynamicArray<Uint>) nonIndexedData.get(1);

        List<Uint> raffleIdsList = raffleIdsArray.getValue();

        Long[] intRaffleIds = new Long[raffleIdsList.size()];

        for (int i = 0; i < raffleIdsList.size(); i++) {
            Uint raffleIdValue = raffleIdsList.get(i);
            BigInteger value = raffleIdValue.getValue();
            intRaffleIds[i] = value.longValue();
        }

        ClaimAllNftsDto dto = ClaimAllNftsDto.builder()
                .claimer(claimer)
                .raffleIds(intRaffleIds)
                .build();

        callBack(dto);
    }

    public abstract void callBack(ClaimAllNftsDto dto);
}
