package com.undefined.undefined.domain.raffle.controller;

import com.undefined.undefined.domain.raffle.dto.request.GetAllRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetMyRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetRafflesByCARequest;
import com.undefined.undefined.domain.raffle.dto.request.GetWinnerRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.response.RaffleResponse;
import com.undefined.undefined.domain.raffle.service.RaffleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/raffles")
public class RaffleController {
    private final RaffleService raffleService;

    @GetMapping
    public ResponseEntity<Page<RaffleResponse>> getAllRaffles(GetAllRafflesRequest request){
        Page<RaffleResponse> response = raffleService.getAllRaffles(request);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/my/{address}")
    public ResponseEntity<Page<RaffleResponse>> getMyRaffles(GetMyRafflesRequest request) {
        Page<RaffleResponse> response = raffleService.getMyRaffles(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{ca}")
    public ResponseEntity<Page<RaffleResponse>> getRafflesByCollection(GetRafflesByCARequest request) {
        Page<RaffleResponse> response = raffleService.getRafflesByCA(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/winner/{winner}")
    public ResponseEntity<Page<RaffleResponse>> getRafflesByWinner(GetWinnerRafflesRequest request){
        Page<RaffleResponse> response = raffleService.getRafflesByWinner(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/deadline")
    public ResponseEntity<List<RaffleResponse>> getDeadlineRaffles(){
        List<RaffleResponse> response = raffleService.getDeadLineRaffles();
        return ResponseEntity.ok().body(response);
    }
}
