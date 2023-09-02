package com.undefined.undefined.domain.raffle.service;

import com.undefined.undefined.domain.raffle.dto.request.GetAllRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetMyRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetRafflesByCARequest;
import com.undefined.undefined.domain.raffle.dto.request.GetWinnerRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.response.RaffleResponse;
import com.undefined.undefined.global.web3.klaytn.dto.*;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface RaffleService {
    void chooseWinner() throws IOException;

    void saveRaffleByEvent(RegisterRaffleDto dto);

    void chooseWinnerByEvent(ChooseWinnerDto dto);

    void claimNftByEvent(ClaimNftDto dto);

    void claimAllNftByEvent(ClaimAllNftsDto dto);

    void claimBalanceByEvent(ClaimBalanceDto dto);

    Page<RaffleResponse> getMyRaffles(GetMyRafflesRequest request);

    Page<RaffleResponse> getAllRaffles(GetAllRafflesRequest request);

    Page<RaffleResponse> getRafflesByCA(GetRafflesByCARequest request);

    Page<RaffleResponse> getRafflesByWinner(GetWinnerRafflesRequest request);

    List<RaffleResponse> getDeadLineRaffles();

    RaffleResponse getPopularRaffle();
}
