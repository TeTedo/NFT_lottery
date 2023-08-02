package com.undefined.undefined.domain.collection.controller;

import com.undefined.undefined.domain.collection.dto.request.RegisterCollectionRequest;
import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;
import com.undefined.undefined.domain.collection.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping
    public ResponseEntity<Page<CollectionResponse>> getCollectionsByPage(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<CollectionResponse> response = collectionService.getCollectionsByPage(pageable);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public void registerCollection(@RequestBody RegisterCollectionRequest request){
        collectionService.registerCollection(request);
    }
}
