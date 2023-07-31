package com.undefined.undefined.domain.ticket.service;

import com.undefined.undefined.domain.raffle.exception.RaffleNotFoundException;
import com.undefined.undefined.domain.raffle.model.Raffle;
import com.undefined.undefined.domain.raffle.repository.RaffleRepository;
import com.undefined.undefined.domain.ticket.dto.request.GetMyTicketsRequest;
import com.undefined.undefined.domain.ticket.dto.response.TicketResponse;
import com.undefined.undefined.domain.ticket.mapper.TicketMapper;
import com.undefined.undefined.domain.ticket.model.Ticket;
import com.undefined.undefined.domain.ticket.repository.TicketRepository;
import com.undefined.undefined.global.web3.klaytn.dto.BuyTicketsDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{
    private final TicketRepository ticketRepository;
    private final RaffleRepository raffleRepository;
    private final TicketMapper ticketMapper;

    @Override
    @Transactional
    public void saveTicketByEvent(BuyTicketsDto dto) {
        Raffle raffle = raffleRepository.findById(dto.getRaffleId())
                .orElseThrow(()-> new RaffleNotFoundException()).updateLeftTicket(dto.getAmount());

        dto.setRaffle(raffle);

        raffleRepository.save(raffle);

        Optional<Ticket> existedTicket =  ticketRepository.findByRaffleIdAndOwner(dto.getRaffleId(), dto.getBuyer());

        if(existedTicket.isPresent()) {
            Ticket ticket = existedTicket.get();
            ticket.updateAmount(dto.getAmount());
            ticketRepository.save(ticket);
        } else {
            ticketRepository.save(dto.toTicket());
        }

    }

    @Override
    public Page<TicketResponse> getMyTickets(GetMyTicketsRequest request) {
        Page<Ticket> ticketPage = ticketRepository.findMyTicket(request.getPageable(), request.getWallet());
        return ticketMapper.toTicketResponse(ticketPage);
    }
}
