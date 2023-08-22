package com.undefined.undefined.global.web3.klaytn.event.handler;

import com.undefined.undefined.domain.collection.service.CollectionService;
import com.undefined.undefined.global.web3.klaytn.dto.ListNftDto;
import com.undefined.undefined.global.web3.klaytn.event.events.ListNftEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListNftHandler extends ListNftEvent {
    private final CollectionService collectionService;

    @Override
    public void callBack(ListNftDto dto){
        collectionService.listNft(dto);
    }
}
