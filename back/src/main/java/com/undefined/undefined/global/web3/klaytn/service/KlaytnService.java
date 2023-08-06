package com.undefined.undefined.global.web3.klaytn.service;

import org.web3j.protocol.core.methods.response.Log;

public interface KlaytnService {
    public void eventBalancer(Log log, Object eventHash);

    public void chooseWinner();
}
