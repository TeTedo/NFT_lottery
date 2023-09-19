package com.undefined.undefined.domain.collection.repository;

import com.undefined.undefined.domain.collection.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query("""
            SELECT c
            FROM Collection c
            """)
    Page<Collection> findCollectionByPage(Pageable pageable);

    @Query("""
            UPDATE Collection c
            SET isActive = true
            WHERE c.contractAddress = :ca
            AND c.type = :type
            """)
    boolean listNft(@Param("ca") String ca, @Param("type") String type);

    @Query("""
            UPDATE Collection c
            SET isActive = false
            WHERE c.contractAddress = :ca
            AND c.type = :type
            """)
    boolean deListNft(@Param("ca") String ca, @Param("type") String type);

    Optional<Collection> findByContractAddress(String ca);
}
