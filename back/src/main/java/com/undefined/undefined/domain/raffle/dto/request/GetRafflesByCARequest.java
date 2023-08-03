package com.undefined.undefined.domain.raffle.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;

@Getter
public class GetRafflesByCARequest {
    private Pageable pageable;
    private String ca;

    @Builder
    public GetRafflesByCARequest(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            @PathVariable("ca") String ca) {
        this.pageable = pageable;
        this.ca = ca;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageable, ca);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof GetRafflesByCARequest)) return false;
        GetRafflesByCARequest that = (GetRafflesByCARequest) obj;
        return Objects.equals(ca, that.ca);
    }
}
