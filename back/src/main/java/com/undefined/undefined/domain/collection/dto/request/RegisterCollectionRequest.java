package com.undefined.undefined.domain.collection.dto.request;

import com.undefined.undefined.domain.collection.model.Collection;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RegisterCollectionRequest {
    private String contractAddress;
    private String contractName;
    private String contractOwner;
    private String tokenUri;
    private String openseaSlug;
    private double creatorFee;
    private String type;
    private String description;
    private String linkTwitter;
    private String linkDiscord;
    private String linkWebsite;
    private String linkScope;

    @Builder
    public RegisterCollectionRequest(
            String contractAddress, String contractName, String contractOwner,
            String tokenUri, String openseaSlug, double creatorFee,
            String type, String description, String linkTwitter,
            String linkDiscord, String linkWebsite, String linkScope) {
        this.contractAddress = contractAddress;
        this.contractName = contractName;
        this.contractOwner = contractOwner;
        this.tokenUri = tokenUri;
        this.openseaSlug = openseaSlug;
        this.creatorFee = creatorFee;
        this.type = type;
        this.description = description;
        this.linkTwitter = linkTwitter;
        this.linkDiscord = linkDiscord;
        this.linkWebsite = linkWebsite;
        this.linkScope = linkScope;
    }

    public Collection toEntity(){
        return Collection.builder()
                .contractAddress(contractAddress)
                .contractName(contractName)
                .contractOwner(contractOwner)
                .tokenUri(tokenUri)
                .openseaSlug(openseaSlug)
                .creatorFee(creatorFee)
                .type(type)
                .description(description)
                .linkTwitter(linkTwitter)
                .linkDiscord(linkDiscord)
                .linkWebsite(linkWebsite)
                .linkScope(linkScope)
                .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractAddress, contractName);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof RegisterCollectionRequest)) return false;
        RegisterCollectionRequest that = (RegisterCollectionRequest) obj;
        return Objects.equals(contractAddress, that.contractAddress);
    }
}
