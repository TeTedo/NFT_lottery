package com.undefined.undefined.domain.web3.klaytn.service;

import com.undefined.undefined.domain.web3.klaytn.dto.MultiChooseWinnerDto;
import com.undefined.undefined.domain.web3.klaytn.dto.RegisterRaffleDto;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public interface KlaytnService {
    void chooseWinner(List<MultiChooseWinnerDto> dto) throws IOException;

    BigInteger getBlockNumber();

    String getTokenUri(RegisterRaffleDto dto);
}
