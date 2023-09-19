package com.undefined.undefined.domain.collection.service;

import com.undefined.undefined.domain.collection.dto.request.RegisterCollectionRequest;
import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;
import com.undefined.undefined.domain.web3.klaytn.dto.DeListNftDto;
import com.undefined.undefined.domain.web3.klaytn.dto.ListNftDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectionService {
    Page<CollectionResponse> getCollectionsByPage(Pageable pageable);

    void registerCollection(RegisterCollectionRequest request);

    void listNft(ListNftDto dto);

    void deListNft(DeListNftDto dto);
}
