package com.undefined.undefined.domain.collection.service;

import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;
import com.undefined.undefined.domain.collection.mapper.CollectionMapper;
import com.undefined.undefined.domain.collection.model.Collection;
import com.undefined.undefined.domain.collection.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService{
    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;

    @Override
    public Page<CollectionResponse> getCollectionsByPage(Pageable pageable) {
        Page<Collection> collectionPage = collectionRepository.findCollectionsByPage(pageable);

        return collectionMapper.toResponse(collectionPage);
    }
}
