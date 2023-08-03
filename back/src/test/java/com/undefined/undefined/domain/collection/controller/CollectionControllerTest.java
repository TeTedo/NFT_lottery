package com.undefined.undefined.domain.collection.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.undefined.undefined.domain.collection.dto.request.RegisterCollectionRequest;
import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;

import com.undefined.undefined.domain.collection.service.CollectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;


import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;


@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({CollectionController.class})
public class CollectionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CollectionService collectionService;

    @BeforeEach
    void setUp (
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("GET /collections?size=10&page=0")
    void getCollectionsByPage() throws Exception{
        // given
        int size = 10;
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable request = PageRequest.of(0,10, sort);

        List<CollectionResponse> dummyCollectionList = Arrays.asList(
                CollectionResponse.builder().id(1L).ca("ca1").tokenUri("temp1").name("name1").build(),
                CollectionResponse.builder().id(1L).ca("ca2").tokenUri("temp2").name("name2").build()
        );

        Page<CollectionResponse> response = new PageImpl<>(dummyCollectionList, request, size);

        Mockito.when(collectionService.getCollectionsByPage(request))
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/collections?size=10&page=0"));

        // then
        Mockito.verify(collectionService).getCollectionsByPage(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("collection",
                        responseFields(
                                fieldWithPath("content").description("Array of CollectionResponse objects"),
                                fieldWithPath("content[].id").description("id").type("Long"),
                                fieldWithPath("content[].ca").description("contract address").type("String"),
                                fieldWithPath("content[].name").description("contract name").type("String"),
                                fieldWithPath("content[].tokenUri").description("tokenUri").type("String"),
                                fieldWithPath("totalElements").description("Total number of elements").type("Number"),
                                fieldWithPath("totalPages").description("Total number of pages").type("Number"),
                                fieldWithPath("pageable").description("Pagination information"),
                                fieldWithPath("pageable.sort.empty").description("Is the sort empty").type("boolean"),
                                fieldWithPath("pageable.sort.sorted").description("Is the sort sorted").type("boolean"),
                                fieldWithPath("pageable.sort.unsorted").description("Is the sort unsorted").type("boolean"),
                                fieldWithPath("pageable.offset").description("Offset of the page").type("number"),
                                fieldWithPath("pageable.pageNumber").description("Page number").type("number"),
                                fieldWithPath("pageable.pageSize").description("Page size").type("number"),
                                fieldWithPath("pageable.paged").description("Is paged").type("boolean"),
                                fieldWithPath("pageable.unpaged").description("Is unpaged").type("boolean"),
                                fieldWithPath("last").description("Is the last page").type("boolean"),
                                fieldWithPath("size").description("Number of elements in the current page").type("number"),
                                fieldWithPath("number").description("Current page number").type("number"),
                                fieldWithPath("sort.empty").description("Is the sort empty").type("boolean"),
                                fieldWithPath("sort.sorted").description("Is the sort sorted").type("boolean"),
                                fieldWithPath("sort.unsorted").description("Is the sort unsorted").type("boolean"),
                                fieldWithPath("first").description("Is the first page").type("boolean"),
                                fieldWithPath("numberOfElements").description("Total number of elements in the result").type("number"),
                                fieldWithPath("empty").description("Is the result empty").type("boolean"),
                                fieldWithPath("content").description("List of collection items").type("Array of CollectionResponse"))));
    }

    @Test
    @DisplayName("POST /collections")
    void registerCollection() throws Exception {
        // given
        RegisterCollectionRequest request = RegisterCollectionRequest.builder()
                .contractAddress("contractAddress")
                .contractName("contractName")
                .contractOwner("contractOwner")
                .tokenUri("tokenUri")
                .openseaSlug("openseSlug")
                .creatorFee(2.5)
                .type("Klaytn")
                .description("description")
                .linkTwitter("twitter")
                .linkDiscord("discord")
                .linkWebsite("website")
                .linkScope("scope")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/collections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("registerCollection",
                        requestFields(
                                fieldWithPath("contractAddress").type(JsonFieldType.STRING).description("The contract address"),
                                fieldWithPath("contractName").type(JsonFieldType.STRING).description("The contract name"),
                                fieldWithPath("contractOwner").type(JsonFieldType.STRING).description("The contract owner"),
                                fieldWithPath("tokenUri").type(JsonFieldType.STRING).description("The token URI"),
                                fieldWithPath("openseaSlug").type(JsonFieldType.STRING).description("The OpenSea slug"),
                                fieldWithPath("creatorFee").type(JsonFieldType.NUMBER).description("The creator fee"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("Klaytn"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("The description"),
                                fieldWithPath("linkTwitter").type(JsonFieldType.STRING).description("The Twitter link"),
                                fieldWithPath("linkDiscord").type(JsonFieldType.STRING).description("The Discord link"),
                                fieldWithPath("linkWebsite").type(JsonFieldType.STRING).description("The website link"),
                                fieldWithPath("linkScope").type(JsonFieldType.STRING).description("The scope link"))));
    }
}
