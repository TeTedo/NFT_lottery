package com.undefined.undefined.domain.ticket.dto.request;

import com.undefined.undefined.domain.raffle.dto.request.GetMyRafflesRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;

@Getter
public class GetMyTicketsRequest {
    private Pageable pageable;
    private String wallet;

    @Builder
    public GetMyTicketsRequest(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            @PathVariable("wallet") String wallet
    ) {
        this.pageable = pageable;
        this.wallet = wallet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageable, wallet);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof GetMyTicketsRequest)) return false;
        GetMyTicketsRequest that = (GetMyTicketsRequest) obj;
        return Objects.equals(wallet, that.wallet);
    }
}
