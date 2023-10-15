package com.undefined.undefined.domain.collection.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;
import com.undefined.undefined.domain.collection.model.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectionMapper {
    private final ObjectMapper objectMapper;

    public CollectionResponse toCollectionResponse(Collection collection){
        return objectMapper.convertValue(collection, CollectionResponse.class);
    }
    public Page<CollectionResponse> toResponse(Page<Collection> collectionPage) {
        return collectionPage.map(this::toCollectionResponse);
    }
}
