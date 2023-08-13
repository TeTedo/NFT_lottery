package com.undefined.undefined.global.web3.klaytn.service;

import org.web3j.protocol.core.methods.response.Log;

import java.io.IOException;

public interface KlaytnService {

    void chooseWinner(Long raffleId, int randNum) throws IOException;
}
