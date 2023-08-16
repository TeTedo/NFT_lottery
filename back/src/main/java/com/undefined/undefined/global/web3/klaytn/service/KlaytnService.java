package com.undefined.undefined.global.web3.klaytn.service;

import com.undefined.undefined.global.web3.klaytn.dto.MultiChooseWinnerDto;
import org.web3j.protocol.core.methods.response.Log;

import java.io.IOException;
import java.util.List;

public interface KlaytnService {

    void chooseWinner(Long raffleId, int randNum) throws IOException;

    void chooseWinner(List<MultiChooseWinnerDto> dto) throws IOException;
}
