package com.undefined.undefined.domain.raffle.service;

import com.undefined.undefined.domain.raffle.dto.request.GetAllRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetMyRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetRafflesByCARequest;
import com.undefined.undefined.domain.raffle.dto.request.GetWinnerRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.response.RaffleResponse;
import com.undefined.undefined.global.web3.klaytn.dto.ChooseWinnerDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimBalanceDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimNftDto;
import com.undefined.undefined.global.web3.klaytn.dto.RegisterRaffleDto;
import org.springframework.data.domain.Page;

public interface RaffleService {
    void chooseWinner();

    void saveRaffleByEvent(RegisterRaffleDto dto);

    void chooseWinnerByEvent(ChooseWinnerDto dto);

    void claimNftByEvent(ClaimNftDto dto);

    void claimBalanceByEvent(ClaimBalanceDto dto);

    Page<RaffleResponse> getMyRaffles(GetMyRafflesRequest request);

    Page<RaffleResponse> getAllRaffles(GetAllRafflesRequest request);

    Page<RaffleResponse> getRafflesByCA(GetRafflesByCARequest request);

    Page<RaffleResponse> getRafflesByWinner(GetWinnerRafflesRequest request);

}
