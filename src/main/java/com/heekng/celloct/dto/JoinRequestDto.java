package com.heekng.celloct.dto;

import com.heekng.celloct.entity.JoinRequest;
import lombok.Builder;
import lombok.Getter;

public class JoinRequestDto {

    @Getter
    public static class joinRequestDto {
        private Long memberId;
        private Long shopId;

        @Builder
        public joinRequestDto(Long memberId, Long shopId) {
            this.memberId = memberId;
            this.shopId = shopId;
        }
    }
}
