package com.undefined.undefined.domain.collection.model;

import com.undefined.undefined.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "collections")
@Getter
@NoArgsConstructor
public class Collection extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_address", length = 66, nullable = false)
    private String contractAddress;

    @Column(name = "contract_name", length = 100, nullable = false)
    private String contractName;

    @Column(name = "contract_owner", length = 66, nullable = false)
    private String contractOwner;

    @Column(name = "token_uri", length = 255, nullable = true)
    private String tokenUri;

    @Column(name = "opensea_slug", length = 255, nullable = false)
    private String openseaSlug;

    @Column(name = "creator_fee", nullable = false)
    private double creatorFee;

    @Column(nullable = false)
    private String type;  // erc, kip 등 체인 확장성을 고려한 type

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean isActive;

    @Column(nullable = false)
    private String description;

    @Column(name = "link_twitter", length = 255)
    private String linkTwitter;

    @Column(name = "link_discord", length = 255)
    private String linkDiscord;

    @Column(name = "link_website", length = 255)
    private String linkWebsite;

    @Column(name = "link_scope", length = 255)
    private String linkScope;

    @Builder

    public Collection(
            Long id, String contractAddress, String contractName,
            String contractOwner, String tokenUri, boolean isActive,
            String openseaSlug, double creatorFee,
            String type, String description,
            String linkTwitter, String linkDiscord,
            String linkWebsite, String linkScope) {

        this.id = id;
        this.contractAddress = contractAddress;
        this.contractName = contractName;
        this.contractOwner = contractOwner;
        this.tokenUri = tokenUri;
        this.openseaSlug = openseaSlug;
        this.creatorFee = creatorFee;
        this.type = type;
        this.isActive = isActive;
        this.description = description;
        this.linkTwitter = linkTwitter;
        this.linkDiscord = linkDiscord;
        this.linkWebsite = linkWebsite;
        this.linkScope = linkScope;
    }
}
