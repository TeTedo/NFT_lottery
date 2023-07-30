package com.undefined.undefined.domain.collection.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CollectionResponse {
    private long id;
    private String ca;
    private String token_uri;
    private String name;

    @Builder
    public CollectionResponse(long id, String ca, String token_uri, String name) {
        this.id = id;
        this.ca = ca;
        this.token_uri = token_uri;
        this.name = name;
    }
}
