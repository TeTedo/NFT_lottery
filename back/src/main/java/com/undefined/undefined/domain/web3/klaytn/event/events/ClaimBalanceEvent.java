package com.undefined.undefined.domain.web3.klaytn.event.events;

import com.undefined.undefined.domain.web3.klaytn.dto.ClaimBalanceDto;
import com.undefined.undefined.domain.web3.klaytn.mapper.EventTypeMapper;
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

public abstract class ClaimBalanceEvent {
    private final Event event;

    private Address claimer;

    private Uint amount;

    private Uint afterBalance;

    public ClaimBalanceEvent() {
        this.event =  new Event("ClaimBalance", Arrays.<TypeReference<?>>asList(
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

        String claimer = EventTypeMapper.toAddress(log.getTopics().get(1));
        double amount = Double.parseDouble(nonIndexedData.get(0).getValue().toString());
        double afterBalance = Double.parseDouble(nonIndexedData.get(0).getValue().toString());

        ClaimBalanceDto dto = ClaimBalanceDto.builder()
                .claimer(claimer)
                .afterBalance(afterBalance)
                .amount(amount)
                .build();

        callBack(dto);
    }

    public abstract void callBack(ClaimBalanceDto dto);
}
