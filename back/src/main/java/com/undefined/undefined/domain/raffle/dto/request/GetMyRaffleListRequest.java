package com.undefined.undefined.domain.raffle.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;

@Getter
public class GetMyRaffleListRequest {
    private Pageable pageable;
    private String address;

    @Builder
    public GetMyRaffleListRequest(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            @PathVariable("address") String address) {
        this.pageable = pageable;
        this.address = address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageable, address);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof GetMyRaffleListRequest)) return false;
        GetMyRaffleListRequest that = (GetMyRaffleListRequest) obj;
        return Objects.equals(address, that.address);
    }
}
