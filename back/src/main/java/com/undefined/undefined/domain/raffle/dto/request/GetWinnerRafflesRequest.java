package com.undefined.undefined.domain.raffle.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;

@Getter
public class GetWinnerRafflesRequest{
    private Pageable pageable;
    private String winner;

    @Builder
    public GetWinnerRafflesRequest(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            @PathVariable("winner") String winner
    ) {
        this.pageable = pageable;
        this.winner = winner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageable, winner);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof GetWinnerRafflesRequest)) return false;
        GetWinnerRafflesRequest that = (GetWinnerRafflesRequest) obj;
        return Objects.equals(winner, that.winner);
    }
}
