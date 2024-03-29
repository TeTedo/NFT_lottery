package com.undefined.undefined.domain.web3.klaytn.event.events;

import com.undefined.undefined.domain.web3.klaytn.dto.RegisterRaffleDto;
import com.undefined.undefined.domain.web3.klaytn.mapper.EventTypeMapper;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.protocol.core.methods.response.Log;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public abstract class RegisterRaffleEvent {
    private final Event event;

    public RegisterRaffleEvent() {
        this.event =  new Event("RegisterRaffle", Arrays.asList(
                new TypeReference<Uint>(true) {},
                new TypeReference<Uint>() {},
                new TypeReference<Address>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Uint>() {},
                new TypeReference<Address>(true) {})
        );
    }

    public String getEventHash() {
        return EventEncoder.encode(event);
    }

    public void saveData(Log log) {
        List<Type> nonIndexedData = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        Long raffleId = EventTypeMapper.toLongId(log.getTopics().get(1));
        int tokenId = Integer.parseInt(nonIndexedData.get(0).getValue().toString());
        String nftCa = nonIndexedData.get(1).getValue().toString();
        double ticketPrice = Double.parseDouble(nonIndexedData.get(2).getValue().toString()) / Math.pow(10,18);
        int totalTickets = Integer.parseInt(nonIndexedData.get(3).getValue().toString());
        LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(nonIndexedData.get(4).getValue().toString())),
                TimeZone.getDefault().toZoneId());
        String seller = EventTypeMapper.toAddress(log.getTopics().get(2));

        RegisterRaffleDto dto = RegisterRaffleDto.builder()
                .raffleId(raffleId)
                .tokenId(tokenId)
                .nftCa(nftCa)
                .ticketPrice(ticketPrice)
                .totalTickets(totalTickets)
                .endTime(endTime)
                .seller(seller)
                .build();

        callBack(dto);
    }

    public abstract void callBack(RegisterRaffleDto dto);
}
