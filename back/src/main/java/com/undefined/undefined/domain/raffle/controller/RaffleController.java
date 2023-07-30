package com.undefined.undefined.domain.raffle.controller;

import com.undefined.undefined.domain.raffle.dto.request.GetMyRaffleListRequest;
import com.undefined.undefined.domain.raffle.dto.response.MyRaffleResponse;
import com.undefined.undefined.domain.raffle.service.RaffleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/raffles")
public class RaffleController {
    private final RaffleService raffleService;

    @GetMapping("/my/{address}")
    public ResponseEntity<Page<MyRaffleResponse>> getMyRaffle(GetMyRaffleListRequest request) {
        Page<MyRaffleResponse> response = raffleService.getMyRaffle(request);
        return ResponseEntity.ok().body(response);
    }
}
