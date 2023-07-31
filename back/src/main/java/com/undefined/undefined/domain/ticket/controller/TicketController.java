package com.undefined.undefined.domain.ticket.controller;

import com.undefined.undefined.domain.ticket.dto.request.GetMyTicketsRequest;
import com.undefined.undefined.domain.ticket.dto.response.TicketResponse;
import com.undefined.undefined.domain.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/{wallet}")
    public ResponseEntity<Page<TicketResponse>> getMyTickets(GetMyTicketsRequest request){
        Page<TicketResponse> response = ticketService.getMyTickets(request);
        return ResponseEntity.ok().body(response);
    }
}
