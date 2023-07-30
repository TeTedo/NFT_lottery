package com.undefined.undefined.domain.collection.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CollectionResponse {
    private long id;
    private String ca;
    private String tokenUri;
    private String name;

    @Builder
    public CollectionResponse(long id, String ca, String tokenUri, String name) {
        this.id = id;
        this.ca = ca;
        this.tokenUri = tokenUri;
        this.name = name;
    }
}
