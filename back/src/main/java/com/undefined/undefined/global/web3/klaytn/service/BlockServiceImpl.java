package com.undefined.undefined.global.web3.klaytn.service;

import com.undefined.undefined.global.web3.klaytn.model.Block;
import com.undefined.undefined.global.web3.klaytn.repository.BlockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService{

    private final BlockRepository blockRepository;

    @Override
    @Transactional
    public void saveLatestBlock(BigInteger latestBlock) {
        blockRepository.updateBlockNum(latestBlock);
    }

    @Override
    public BigInteger getLatestBlock(){
        Optional<Block> latestBlock = blockRepository.findLatestBlockNum();

        if(latestBlock.isEmpty()) return BigInteger.ZERO;

        return latestBlock.get().getBlockNum();
    }
}
