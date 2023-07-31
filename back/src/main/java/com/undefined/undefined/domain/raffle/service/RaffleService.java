package com.undefined.undefined.domain.raffle.service;

import com.undefined.undefined.domain.raffle.dto.request.GetAllRaffleListRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetMyRaffleListRequest;
import com.undefined.undefined.domain.raffle.dto.response.RaffleResponse;
import com.undefined.undefined.global.web3.klaytn.dto.ChooseWinnerDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimBalanceDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimNftDto;
import com.undefined.undefined.global.web3.klaytn.dto.RegisterRaffleDto;
import org.springframework.data.domain.Page;

public interface RaffleService {
    void saveRaffleByEvent(RegisterRaffleDto dto);

    void chooseWinnerByEvent(ChooseWinnerDto dto);

    void claimNftByEvent(ClaimNftDto dto);

    void claimBalanceByEvent(ClaimBalanceDto dto);

    Page<RaffleResponse> getMyRaffle(GetMyRaffleListRequest request);

    Page<RaffleResponse> getAllRaffles(GetAllRaffleListRequest request);
}
