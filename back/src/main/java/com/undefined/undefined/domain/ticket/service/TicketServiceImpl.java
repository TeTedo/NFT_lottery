package com.undefined.undefined.domain.ticket.service;

import com.undefined.undefined.domain.collection.model.Collection;
import com.undefined.undefined.domain.collection.repository.CollectionRepository;
import com.undefined.undefined.domain.raffle.exception.RaffleNotFoundException;
import com.undefined.undefined.domain.raffle.model.Raffle;
import com.undefined.undefined.domain.raffle.repository.RaffleRepository;
import com.undefined.undefined.domain.ticket.dto.request.GetMyTicketsRequest;
import com.undefined.undefined.domain.ticket.dto.response.TicketResponse;
import com.undefined.undefined.domain.ticket.mapper.TicketMapper;
import com.undefined.undefined.domain.ticket.model.Ticket;
import com.undefined.undefined.domain.ticket.repository.TicketRepository;
import com.undefined.undefined.global.web3.klaytn.dto.BuyTicketsDto;
import com.undefined.undefined.global.web3.klaytn.service.KlaytnService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService{
    private final TicketRepository ticketRepository;
    private final RaffleRepository raffleRepository;
    private final TicketMapper ticketMapper;
    private final CollectionRepository collectionRepository;

    @Override
    public void saveTicketByEvent(BuyTicketsDto dto) {
        Raffle raffle = raffleRepository.findById(dto.getRaffleId())
                .orElseThrow(RaffleNotFoundException::new).updateLeftTicket(dto.getAmount());

        dto.setRaffle(raffle);

        Optional<Ticket> existedTicket =  ticketRepository.findByRaffleIdAndOwner(dto.getRaffleId(), dto.getBuyer());

        if(existedTicket.isPresent()) {
            Ticket ticket = existedTicket.get();
            ticket.updateAmount(dto.getAmount());
            ticketRepository.save(ticket);
        } else {
            ticketRepository.save(dto.toTicket());
        }

        if(raffle.getLeftTicket() == 0) {
            raffle.endTimeRaffle();
        }

        raffleRepository.save(raffle);
    }

    @Override
    public Page<TicketResponse> getMyTickets(GetMyTicketsRequest request) {
        Page<Ticket> ticketPage = ticketRepository.findMyTicket(request.getPageable(), request.getWallet());
        return ticketPage.map(ticket -> {
            Optional<Collection> collection = collectionRepository.findByContractAddress(ticket.getRaffle().getCa());
            if(collection.isEmpty()) return ticketMapper.toTicketResponse(ticket,"");
            return ticketMapper.toTicketResponse(ticket, collection.get().getContractName());
        });
    }

    @Override
    public List<TicketResponse> getTicketInfoByRaffleId(Long raffleId) {
        List<Ticket> ticketList = ticketRepository.getTicketInfoByRaffleId(raffleId);
        return ticketList.stream().map(TicketResponse::of).toList();
    }
}
