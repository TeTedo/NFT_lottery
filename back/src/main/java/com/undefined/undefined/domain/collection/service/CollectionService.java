package com.undefined.undefined.domain.collection.service;

import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectionService {
    Page<CollectionResponse> getCollectionsByPage(Pageable pageable);
}
