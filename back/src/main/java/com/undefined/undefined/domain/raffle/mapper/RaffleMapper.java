package com.undefined.undefined.domain.raffle.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.undefined.undefined.domain.raffle.dto.response.RaffleResponse;
import com.undefined.undefined.domain.raffle.model.Raffle;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaffleMapper {
    private final ObjectMapper objectMapper;

    public RaffleResponse toRaffleResponse(Raffle raffle) {
        return objectMapper.convertValue(raffle, RaffleResponse.class);
    }

    public Page<RaffleResponse> toRaffleResponse(Page<Raffle> rafflePage) {
        return rafflePage.map(this::toRaffleResponse);
    }
}
