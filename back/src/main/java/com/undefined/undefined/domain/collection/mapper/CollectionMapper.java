package com.undefined.undefined.domain.collection.mapper;

import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;
import com.undefined.undefined.domain.collection.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CollectionMapper {
    public CollectionResponse toCollectionResponse(Collection collection){
        return CollectionResponse.builder()
                .id(collection.getId())
                .ca(collection.getContractAddress())
                .name(collection.getContractName())
                .tokenUri(collection.getTokenUri())
                .build();
    }

    public Page<CollectionResponse> toResponse(Page<Collection> collectionPage) {
        return collectionPage.map(v-> toCollectionResponse(v));
    }
}
