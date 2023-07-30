package com.undefined.undefined.domain.collection.repository;

import com.undefined.undefined.domain.collection.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Page<Collection> findCollectionsByPage(Pageable pageable);
}
