package com.undefined.undefined.domain.raffle.controller;

import com.undefined.undefined.domain.raffle.dto.request.GetAllRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetMyRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetRafflesByCARequest;
import com.undefined.undefined.domain.raffle.dto.request.GetWinnerRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.response.RaffleResponse;
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
import java.util.ArrayList;
import java.util.Arrays;
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
        GetMyRafflesRequest request = GetMyRafflesRequest.builder()
                .pageable(pageable)
                .address(address)
                .build();

        List<RaffleResponse> dummy = Arrays.asList(
                RaffleResponse.builder().id(1L).ca("ca").tokenUri("tokenUri").tokenId(1).totalTicket(100).leftTicket(50).ticketPrice(0.1).endTime(LocalDateTime.now()).isEnd(true).isPaid(false).isFailed(false).settlement(10).createdAt(LocalDateTime.now()).build()
        );

        Page<RaffleResponse> response = new PageImpl<>(dummy, pageable, size);

        Mockito.when(raffleService.getMyRaffles(request))
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/raffles/my/{wallet-address}?size=10&page=0",address));

        // then
        Mockito.verify(raffleService).getMyRaffles(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("myRaffle",
                        responseFields(
                                fieldWithPath("content").description("Array of RaffleResponse objects"),
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
                                fieldWithPath("content[].failed").description("Is the raffle failed").type("boolean"),
                                fieldWithPath("content[].claimNft").description("Is the nft claimed").type("boolean"),
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
    @DisplayName("GET /raffles?size=10&page=0")
    void getAllRaffles() throws Exception{
        // given
        int size = 10;
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(0,10, sort);
        GetAllRafflesRequest request = GetAllRafflesRequest.builder()
                .pageable(pageable)
                .build();

        List<RaffleResponse> dummy = Arrays.asList(
                RaffleResponse.builder().id(1L).ca("ca").tokenUri("tokenUri").tokenId(1).totalTicket(100).leftTicket(50).ticketPrice(0.1).endTime(LocalDateTime.now()).isEnd(true).isPaid(false).isFailed(false).settlement(10).createdAt(LocalDateTime.now()).build()
        );

        Page<RaffleResponse> response = new PageImpl<>(dummy, pageable, size);

        Mockito.when(raffleService.getAllRaffles(request))
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/raffles?size=10&page=0"));

        // then
        Mockito.verify(raffleService).getAllRaffles(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("allRaffles",
                        responseFields(
                                fieldWithPath("content").description("Array of RaffleResponse objects"),
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
                                fieldWithPath("content[].failed").description("Is the raffle failed").type("boolean"),
                                fieldWithPath("content[].claimNft").description("Is the nft claimed").type("boolean"),
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
    @DisplayName("GET /raffles/{contractAddress}?size=10&page=0")
    void getRafflesByCA() throws Exception{
        // given
        int size = 10;
        String ca = "tempContractAddress";
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(0,10, sort);
        GetRafflesByCARequest request = GetRafflesByCARequest.builder()
                .pageable(pageable)
                .ca(ca)
                .build();

        List<RaffleResponse> dummy = Arrays.asList(
                RaffleResponse.builder().id(1L).ca("ca").tokenUri("tokenUri").tokenId(1).totalTicket(100).leftTicket(50).ticketPrice(0.1).endTime(LocalDateTime.now()).isEnd(true).isPaid(false).isFailed(false).settlement(10).createdAt(LocalDateTime.now()).build()
        );

        Page<RaffleResponse> response = new PageImpl<>(dummy, pageable, size);

        Mockito.when(raffleService.getRafflesByCA(request))
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/raffles/{contract-address}?size=10&page=0",ca));

        // then
        Mockito.verify(raffleService).getRafflesByCA(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("raffleByCA",
                        responseFields(
                                fieldWithPath("content").description("Array of RaffleResponse objects"),
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
                                fieldWithPath("content[].failed").description("Is the raffle failed").type("boolean"),
                                fieldWithPath("content[].claimNft").description("Is the nft claimed").type("boolean"),
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
    @DisplayName("GET /raffles/winner/{winnerAddress}?size=10&page=0")
    void getRafflesByWinner() throws Exception{
        // given
        int size = 10;
        String winner = "tempContractAddress";
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(0,10, sort);
        GetWinnerRafflesRequest request = GetWinnerRafflesRequest.builder()
                .pageable(pageable)
                .winner(winner)
                .build();

        List<RaffleResponse> dummy = Arrays.asList(
                RaffleResponse.builder().id(1L).ca("ca").tokenUri("tokenUri").tokenId(1).totalTicket(100).leftTicket(50).ticketPrice(0.1).endTime(LocalDateTime.now()).isEnd(true).isPaid(false).isFailed(false).settlement(10).createdAt(LocalDateTime.now()).build()
        );

        Page<RaffleResponse> response = new PageImpl<>(dummy, pageable, size);

        Mockito.when(raffleService.getRafflesByWinner(request))
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/raffles/winner/{contract-address}?size=10&page=0",winner));

        // then
        Mockito.verify(raffleService).getRafflesByWinner(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("raffleByWinner",
                        responseFields(
                                fieldWithPath("content").description("Array of RaffleResponse objects"),
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
                                fieldWithPath("content[].failed").description("Is the raffle failed").type("boolean"),
                                fieldWithPath("content[].claimNft").description("Is the nft claimed").type("boolean"),
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
    @DisplayName("GET /raffles/deadline")
    void getDeadLineRaffles() throws Exception{
        // given
        List<RaffleResponse> response = new ArrayList<>();
        RaffleResponse data = RaffleResponse.builder()
                .id(1L)
                .ca("test")
                .tokenId(1)
                .tokenUri("test")
                .totalTicket(1)
                .leftTicket(1)
                .ticketPrice(1)
                .endTime(LocalDateTime.now())
                .isEnd(false)
                .isPaid(false)
                .isClaimNft(false)
                .isFailed(false)
                .settlement(1)
                .createdAt(LocalDateTime.now())
                .build();
        response.add(data);
        response.add(data);

        Mockito.when(raffleService.getDeadLineRaffles())
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/raffles/deadline"));

        // then
        Mockito.verify(raffleService).getDeadLineRaffles();

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("getDeadlineRaffles",
                        responseFields(
                                fieldWithPath("[].id").description("The ID of the item."),
                                fieldWithPath("[].ca").description("The CA value."),
                                fieldWithPath("[].tokenId").description("The token ID."),
                                fieldWithPath("[].tokenUri").description("The URI of the token."),
                                fieldWithPath("[].totalTicket").description("The total number of tickets."),
                                fieldWithPath("[].leftTicket").description("The number of left tickets."),
                                fieldWithPath("[].ticketPrice").description("The price of a ticket."),
                                fieldWithPath("[].endTime").description("The end time."),
                                fieldWithPath("[].settlement").description("The settlement value."),
                                fieldWithPath("[].createdAt").description("The creation timestamp."),
                                fieldWithPath("[].claimNft").description("Indicates whether NFT is claimed."),
                                fieldWithPath("[].end").description("Indicates whether the process is ended."),
                                fieldWithPath("[].paid").description("Indicates whether the payment is made."),
                                fieldWithPath("[].failed").description("Indicates whether the raffle is made."))));

    }

    @Test
    @DisplayName("GET /raffles/popular")
    void getPopularRaffle() throws Exception{
        // given
        RaffleResponse response = RaffleResponse.builder()
                .id(1L)
                .ca("test")
                .tokenId(1)
                .tokenUri("test")
                .totalTicket(1)
                .leftTicket(1)
                .ticketPrice(1)
                .endTime(LocalDateTime.now())
                .isEnd(false)
                .isPaid(false)
                .isClaimNft(false)
                .isFailed(false)
                .settlement(1)
                .createdAt(LocalDateTime.now())
                .build();


        Mockito.when(raffleService.getPopularRaffle())
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/raffles/popular"));

        // then
        Mockito.verify(raffleService).getPopularRaffle();

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("getPopularRaffle",
                        responseFields(
                                fieldWithPath("id").description("The ID of the item."),
                                fieldWithPath("ca").description("The CA value."),
                                fieldWithPath("tokenId").description("The token ID."),
                                fieldWithPath("tokenUri").description("The URI of the token."),
                                fieldWithPath("totalTicket").description("The total number of tickets."),
                                fieldWithPath("leftTicket").description("The number of left tickets."),
                                fieldWithPath("ticketPrice").description("The price of a ticket."),
                                fieldWithPath("endTime").description("The end time."),
                                fieldWithPath("settlement").description("The settlement value."),
                                fieldWithPath("createdAt").description("The creation timestamp."),
                                fieldWithPath("claimNft").description("Indicates whether NFT is claimed."),
                                fieldWithPath("end").description("Indicates whether the process is ended."),
                                fieldWithPath("paid").description("Indicates whether the payment is made."),
                                fieldWithPath("failed").description("Indicates whether the raffle is made."))));

    }
}
