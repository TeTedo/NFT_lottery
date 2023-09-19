package com.undefined.undefined.domain.web3.klaytn.service;

import com.undefined.undefined.domain.web3.klaytn.model.Block;
import com.undefined.undefined.domain.web3.klaytn.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockServiceImpl implements BlockService{

    private final BlockRepository blockRepository;
    private final KlaytnService klaytnService;

    @Override
    @Transactional
    public void saveLatestBlock(BigInteger latestBlock) {
        blockRepository.updateBlockNum(latestBlock);
    }

    @Override
    public BigInteger getLatestBlock(){
        Optional<Block> latestBlock = blockRepository.findLatestBlockNum();

        if(latestBlock.isEmpty()) return klaytnService.getBlockNumber();

        return latestBlock.get().getBlockNum();
    }
}
