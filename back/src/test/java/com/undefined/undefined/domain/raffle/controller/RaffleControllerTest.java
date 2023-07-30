package com.undefined.undefined.domain.raffle.controller;

import com.undefined.undefined.domain.collection.controller.CollectionController;
import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;
import com.undefined.undefined.domain.collection.service.CollectionService;
import com.undefined.undefined.domain.raffle.dto.request.GetMyRaffleListRequest;
import com.undefined.undefined.domain.raffle.dto.response.MyRaffleResponse;
import com.undefined.undefined.domain.raffle.service.RaffleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({RaffleController.class})
public class RaffleControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RaffleService raffleService;

    @BeforeEach
    void setUp (
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("GET /raffles/my/{address}?size=10&page=0")
    void getMyRaffle() throws Exception{
        // given
        int size = 10;
        String address = "tempAddress";
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(0,10, sort);
        GetMyRaffleListRequest request = GetMyRaffleListRequest.builder()
                .pageable(pageable)
                .address(address)
                .build();

        List<MyRaffleResponse> dummy = Arrays.asList(
                MyRaffleResponse.builder().id(1L).ca("ca").tokenUri("tokenUri").tokenId(1).totalTicket(100).leftTicket(50).ticketPrice(0.1).endTime(LocalDateTime.now()).isEnd(true).isPaid(false).settlement(10).createdAt(LocalDateTime.now()).build()
        );

        Page<MyRaffleResponse> response = new PageImpl<>(dummy, pageable, size);

        Mockito.when(raffleService.getMyRaffle(request))
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/raffles/my/tempAddress?size=10&page=0"));

        // then
        Mockito.verify(raffleService).getMyRaffle(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("myRaffle",
                        responseFields(
                                fieldWithPath("content").description("Array of MyRaffleResponse objects"),
                                fieldWithPath("content[].id").description("id").type("Long"),
                                fieldWithPath("content[].ca").description("contract address").type("String"),
                                fieldWithPath("content[].tokenId").description("id").type("number"),
                                fieldWithPath("content[].tokenUri").description("tokenUri").type("string"),
                                fieldWithPath("content[].totalTicket").description("Total number of tickets").type("number"),
                                fieldWithPath("content[].leftTicket").description("Number of remaining tickets").type("number"),
                                fieldWithPath("content[].ticketPrice").description("Ticket price").type("number"),
                                fieldWithPath("content[].endTime").description("End time of the raffle").type("string"),
                                fieldWithPath("content[].settlement").description("Settlement amount").type("number"),
                                fieldWithPath("content[].createdAt").description("Creation time of the raffle").type("string"),
                                fieldWithPath("content[].end").description("Is the raffle ended").type("boolean"),
                                fieldWithPath("content[].paid").description("Is the raffle paid").type("boolean"),
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
}
