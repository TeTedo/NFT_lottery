package com.undefined.undefined.domain.raffle.service;

import com.undefined.undefined.domain.raffle.dto.request.GetAllRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetMyRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.request.GetRafflesByCARequest;
import com.undefined.undefined.domain.raffle.dto.request.GetWinnerRafflesRequest;
import com.undefined.undefined.domain.raffle.dto.response.RaffleResponse;
import com.undefined.undefined.domain.raffle.exception.RaffleNotFoundException;
import com.undefined.undefined.domain.raffle.mapper.RaffleMapper;
import com.undefined.undefined.domain.raffle.model.Raffle;
import com.undefined.undefined.domain.raffle.repository.RaffleRepository;
import com.undefined.undefined.global.web3.klaytn.dto.ChooseWinnerDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimBalanceDto;
import com.undefined.undefined.global.web3.klaytn.dto.ClaimNftDto;
import com.undefined.undefined.global.web3.klaytn.dto.RegisterRaffleDto;
import com.undefined.undefined.global.web3.klaytn.service.KlaytnService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RaffleServiceImpl implements  RaffleService{
    private final RaffleRepository raffleRepository;
    private final RaffleMapper raffleMapper;
    private final KlaytnService klaytnService;

    @Override
    public void chooseWinner() throws IOException {
        List<Raffle> endRaffles = raffleRepository.findEndRaffle();

        for(Raffle raffle : endRaffles) {
            int pick = raffle.getTotalTicket() - raffle.getLeftTicket();
            int randNum = (int) (Math.random() * pick + 1);

            klaytnService.chooseWinner(raffle.getId(), randNum);
        }
    }

    @Override
    public void saveRaffleByEvent(RegisterRaffleDto dto) {
        raffleRepository.save(dto.toRaffle());
    }

    @Override
    @Transactional
    public void chooseWinnerByEvent(ChooseWinnerDto dto) {
        Raffle raffle = raffleRepository.findById(dto.getRaffleId())
                        .orElseThrow(RaffleNotFoundException::new);

        raffle.chooseWinner(dto.getWinner(), dto.getSettlement());
        raffleRepository.save(raffle);
    }

    @Override
    @Transactional
    public void claimNftByEvent(ClaimNftDto dto) {
        Raffle raffle = raffleRepository.findById(dto.getRaffleId())
                .orElseThrow(RaffleNotFoundException::new);

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

    @Override
    public Page<RaffleResponse> getMyRaffles(GetMyRafflesRequest request) {
        Page<Raffle> response = raffleRepository.findBySellerAndPage( request.getPageable(), request.getAddress());


        return raffleMapper.toRaffleResponse(response);
    }

    @Override
    public Page<RaffleResponse> getAllRaffles(GetAllRafflesRequest request) {
        Page<Raffle> rafflePage = raffleRepository.findAllByPage(request.getPageable());
        return raffleMapper.toRaffleResponse(rafflePage);
    }

    @Override
    public Page<RaffleResponse> getRafflesByCA(GetRafflesByCARequest request) {
        Page<Raffle> rafflePage = raffleRepository.findByCaAndPage(request.getPageable(), request.getCa());
        return raffleMapper.toRaffleResponse(rafflePage);
    }

    @Override
    public Page<RaffleResponse> getRafflesByWinner(GetWinnerRafflesRequest request) {
        Page<Raffle> rafflePage = raffleRepository.findByWinnerAndPage(request.getPageable(), request.getWinner());
        return raffleMapper.toRaffleResponse(rafflePage);
    }
}
