package com.undefined.undefined.domain.collection.controller;

import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;

import com.undefined.undefined.domain.collection.service.CollectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;


import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({CollectionController.class})
public class CollectionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CollectionService collectionService;

    @MockBean
    private Page<CollectionResponse> collectionResponsePage;

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
        Pageable request = Pageable.ofSize(10).withPage(0);

        Mockito.when(collectionService.getCollectionsByPage(request))
                .thenReturn(collectionResponsePage);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/collections?size=10&page=0"));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document(
                        "collection",
                        responseFields(
                                fieldWithPath("id").description("id").type("Long"),
                                fieldWithPath("ca").description("contract address").type("String"),
                                fieldWithPath("name").description("contract name").type("String"),
                                fieldWithPath("token_uri").description("token_uri").type("String"))));
    }
}
