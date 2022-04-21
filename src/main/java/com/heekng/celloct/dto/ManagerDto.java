package com.heekng.celloct.dto;

import lombok.Builder;
import lombok.Getter;

public class ManagerDto {

    @Getter
    public static class addRequest {
        private Long shopId;
        private Long memberId;

        @Builder
        public addRequest(Long shopId, Long memberId) {
            this.shopId = shopId;
            this.memberId = memberId;
        }
    }

}
