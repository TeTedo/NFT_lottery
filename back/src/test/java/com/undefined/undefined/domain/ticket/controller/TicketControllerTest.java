package com.undefined.undefined.domain.ticket.controller;

import com.undefined.undefined.domain.collection.controller.CollectionController;
import com.undefined.undefined.domain.collection.dto.response.CollectionResponse;
import com.undefined.undefined.domain.collection.service.CollectionService;
import com.undefined.undefined.domain.raffle.model.Raffle;
import com.undefined.undefined.domain.ticket.dto.request.GetMyTicketsRequest;
import com.undefined.undefined.domain.ticket.dto.response.TicketResponse;
import com.undefined.undefined.domain.ticket.service.TicketService;
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

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({TicketController.class})
public class TicketControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @BeforeEach
    void setUp (
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("GET /tickets/{address}?size=10&page=0")
    void getCollectionsByPage() throws Exception{
        // given
        int size = 10;
        String wallet = "tempWallet";
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(0,10, sort);
        GetMyTicketsRequest request = GetMyTicketsRequest.builder()
                .pageable(pageable)
                .wallet(wallet)
                .build();

        List<TicketResponse> dummyCollectionList = Arrays.asList(
                TicketResponse.builder().id(1L).raffle(Raffle.builder().build()).tokenUri("temp1").owner("name1").amount(100).build()
        );

        Page<TicketResponse> response = new PageImpl<>(dummyCollectionList, pageable, size);

        Mockito.when(ticketService.getMyTickets(request))
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/tickets/tempWallet?size=10&page=0"));

        // then
        Mockito.verify(ticketService).getMyTickets(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("myTickets",
                        responseFields(
                            fieldWithPath("content").description("Array of MyRaffleResponse objects"),
                            fieldWithPath("content[].id").description("ID of the raffle").type("number"),
                            fieldWithPath("content[].raffle.createdAt").description("Creation time of the raffle").type("string"),
                            fieldWithPath("content[].raffle.updatedAt").description("Last update time of the raffle").type("string"),
                            fieldWithPath("content[].raffle.id").description("ID of the raffle").type("number"),
                            fieldWithPath("content[].raffle.ca").description("Contract address").type("string"),
                            fieldWithPath("content[].raffle.tokenId").description("Token ID").type("number"),
                            fieldWithPath("content[].raffle.tokenUri").description("Token URI").type("string"),
                            fieldWithPath("content[].raffle.totalTicket").description("Total number of tickets").type("number"),
                            fieldWithPath("content[].raffle.leftTicket").description("Number of remaining tickets").type("number"),
                            fieldWithPath("content[].raffle.ticketPrice").description("Ticket price").type("number"),
                            fieldWithPath("content[].raffle.seller").description("Seller").type("string"),
                            fieldWithPath("content[].raffle.winner").description("Winner").type("string"),
                            fieldWithPath("content[].raffle.endTime").description("End time of the raffle").type("string"),
                            fieldWithPath("content[].raffle.settlement").description("Settlement amount").type("number"),
                            fieldWithPath("content[].raffle.end").description("Is the raffle ended").type("boolean"),
                            fieldWithPath("content[].raffle.paid").description("Is the raffle paid").type("boolean"),
                            fieldWithPath("content[].raffle.claimNft").description("Is the NFT claimed").type("boolean"),
                            fieldWithPath("content[].owner").description("Owner of the raffle").type("string"),
                            fieldWithPath("content[].tokenUri").description("Temporary URI").type("string"),
                            fieldWithPath("content[].amount").description("Amount").type("number"),
                            fieldWithPath("totalElements").description("Total number of elements").type("number"),
                            fieldWithPath("totalPages").description("Total number of pages").type("number"),
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
                            fieldWithPath("empty").description("Is the result empty").type("boolean"))));
    }
}
