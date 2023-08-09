package com.undefined.undefined.global.web3.klaytn.service;

import java.math.BigInteger;

public interface BlockService {
    public void saveLatestBlock(BigInteger latestBlock);

    public BigInteger getLatestBlock();


}
