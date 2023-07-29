package com.undefined.undefined.domain.raffle.service;

import com.undefined.undefined.domain.raffle.exception.RaffleNotFoundException;
import com.undefined.undefined.domain.raffle.model.Raffle;
import com.undefined.undefined.domain.raffle.repository.RaffleRepository;
import com.undefined.undefined.global.web3.klaytn.dto.ChooseWinnerDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimBalanceDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimNftDto;
import com.undefined.undefined.global.web3.klaytn.dto.RegisterRaffleDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RaffleServiceImpl implements  RaffleService{
    private final RaffleRepository raffleRepository;

    @Override
    public void saveRaffleByEvent(RegisterRaffleDto dto) {
        raffleRepository.save(dto.toRaffle());
    }

    @Override
    @Transactional
    public void chooseWinnerByEvent(ChooseWinnerDto dto) {
        Raffle raffle = raffleRepository.findById(dto.getRaffleId())
                        .orElseThrow(()-> new RaffleNotFoundException());

        raffle.chooseWinner(dto.getWinner(), dto.getSettlement());

        raffleRepository.save(raffle);
    }

    @Override
    @Transactional
    public void claimNftByEvent(ClaimNftDto dto) {
        System.out.println(dto.getRaffleId());
        Raffle raffle = raffleRepository.findById(dto.getRaffleId())
                .orElseThrow(()-> new RaffleNotFoundException());

        raffle.claimNft();

        raffleRepository.save(raffle);
    }

    @Override
    @Transactional
    public void claimBalanceByEvent(ClaimBalanceDto dto) {
        List<Raffle> raffleList = raffleRepository.findAllBySeller(dto.getClaimer());

        raffleList.stream().map(r->r.claimBalance()).collect(Collectors.toList());

        raffleRepository.saveAll(raffleList);
    }
}
