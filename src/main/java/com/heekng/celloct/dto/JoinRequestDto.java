package com.heekng.celloct.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class JoinRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class joinRequest {
        private Long memberId;
        private Long shopId;

        @Builder
        public joinRequest(Long memberId, Long shopId) {
            this.memberId = memberId;
            this.shopId = shopId;
        }
    }
}
