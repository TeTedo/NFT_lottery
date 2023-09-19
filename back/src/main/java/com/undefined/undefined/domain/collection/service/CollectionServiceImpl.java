package com.undefined.undefined.domain.collection.service;

import com.undefined.undefined.domain.collection.dto.request.RegisterCollectionRequest;
import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;
import com.undefined.undefined.domain.collection.mapper.CollectionMapper;
import com.undefined.undefined.domain.collection.model.Collection;
import com.undefined.undefined.domain.collection.repository.CollectionRepository;
import com.undefined.undefined.domain.web3.klaytn.dto.DeListNftDto;
import com.undefined.undefined.domain.web3.klaytn.dto.ListNftDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectionServiceImpl implements CollectionService{
    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;

    @Override
    public Page<CollectionResponse> getCollectionsByPage(Pageable pageable) {
        Page<Collection> collectionPage = collectionRepository.findCollectionByPage(pageable);

        return collectionMapper.toResponse(collectionPage);
    }

    @Override
    @Transactional
    public void registerCollection(RegisterCollectionRequest request) {
        collectionRepository.save(request.toEntity());
    }

    @Override
    @Transactional
    public void listNft(ListNftDto dto) {
        collectionRepository.listNft(dto.getCa(),dto.getType());
    }

    @Override
    @Transactional
    public void deListNft(DeListNftDto dto) {
        collectionRepository.deListNft(dto.getCa(),dto.getType());
    }
}
