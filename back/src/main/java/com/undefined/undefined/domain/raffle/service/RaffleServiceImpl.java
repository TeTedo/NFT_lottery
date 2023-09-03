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
import com.undefined.undefined.global.web3.klaytn.dto.*;
import com.undefined.undefined.global.web3.klaytn.service.KlaytnService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        List<MultiChooseWinnerDto> dto = new ArrayList<>();
        for(Raffle raffle : endRaffles) {

            if(raffle.getLeftTicket() == raffle.getTotalTicket()) {
                raffle.failedRaffle();
                raffle.endTimeRaffle();
                raffleRepository.save(raffle);
                continue;
            }

            int pick = raffle.getTotalTicket() - raffle.getLeftTicket();
            int randNum = (int) (Math.random() * pick + 1);

            dto.add(new MultiChooseWinnerDto(raffle.getId(), randNum));
        }

        klaytnService.chooseWinner(dto);
    }

    @Override
    public void saveRaffleByEvent(RegisterRaffleDto dto) {
        String tokenUri = klaytnService.getTokenUri(dto);
        raffleRepository.save(dto.toRaffleWithTokenUri(tokenUri));
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
    public void claimAllNftByEvent(ClaimAllNftsDto dto) {
        for(Long raffleId : dto.getRaffleIds()) {
            Raffle raffle = raffleRepository.findById(raffleId)
                    .orElseThrow(RaffleNotFoundException::new);

            raffle.claimNft();

            raffleRepository.save(raffle);
        }
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

    @Override
    public List<RaffleResponse> getDeadLineRaffles() {
        return raffleRepository.findDeadlineRaffles()
                .stream()
                .map(RaffleResponse::of)
                .toList();
    }

    @Override
    public RaffleResponse getPopularRaffle() {
        List<Raffle> popularRaffle = raffleRepository.findPopularRaffle();

        if(popularRaffle.size() == 0) return null;

        return RaffleResponse.of(popularRaffle.get(0));
    }
}
