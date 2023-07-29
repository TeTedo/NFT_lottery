package com.undefined.undefined.domain.raffle.service;

import com.undefined.undefined.global.web3.klaytn.dto.ChooseWinnerDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimBalanceDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimNftDto;
import com.undefined.undefined.global.web3.klaytn.dto.RegisterRaffleDto;

public interface RaffleService {
    void saveRaffleByEvent(RegisterRaffleDto dto);

    void chooseWinnerByEvent(ChooseWinnerDto dto);

    void claimNftByEvent(ClaimNftDto dto);

    void claimBalanceByEvent(ClaimBalanceDto dto);
}
