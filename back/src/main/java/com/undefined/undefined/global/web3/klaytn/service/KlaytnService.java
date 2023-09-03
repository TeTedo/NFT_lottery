package com.undefined.undefined.global.web3.klaytn.service;

import com.undefined.undefined.global.web3.klaytn.dto.MultiChooseWinnerDto;
import com.undefined.undefined.global.web3.klaytn.dto.RegisterRaffleDto;
import org.web3j.protocol.core.methods.response.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public interface KlaytnService {
    void chooseWinner(List<MultiChooseWinnerDto> dto) throws IOException;

    BigInteger getBlockNumber();

    String getTokenUri(RegisterRaffleDto dto);
}
