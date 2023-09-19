package com.undefined.undefined.domain.web3.klaytn.event.handler;

import com.undefined.undefined.domain.collection.service.CollectionService;
import com.undefined.undefined.domain.web3.klaytn.event.events.DeListNftEvent;
import com.undefined.undefined.domain.web3.klaytn.dto.DeListNftDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeListNftHandler extends DeListNftEvent {
    private final CollectionService collectionService;

    @Override
    public void callBack(DeListNftDto dto){
        collectionService.deListNft(dto);
    }
}
