package com.undefined.undefined.global.web3.klaytn.service;

import java.math.BigInteger;

public interface BlockService {
    void saveLatestBlock(BigInteger latestBlock);

    BigInteger getLatestBlock();
}
