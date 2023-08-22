package com.undefined.undefined.domain.raffle.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.util.Objects;

@Getter
public class GetAllRafflesRequest {
    private Pageable pageable;

    @Builder
    public GetAllRafflesRequest(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof GetAllRafflesRequest)) return false;
        GetAllRafflesRequest that = (GetAllRafflesRequest) obj;
        return Objects.equals(hashCode(), that.hashCode());
    }
}
