package com.undefined.undefined.domain.web3.klaytn.service;

import java.math.BigInteger;

public interface BlockService {
    void saveLatestBlock(BigInteger latestBlock);

    BigInteger getLatestBlock();
}
