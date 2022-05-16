package com.heekng.celloct.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class JoinRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class JoinRequest {
        private Long memberId;
        private Long shopId;

        @Builder
        public JoinRequest(Long memberId, Long shopId) {
            this.memberId = memberId;
            this.shopId = shopId;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ManagerShopJoinRequestResponse {
        private Long joinRequestId;
        private Long memberId;
        private String memberName;
        private LocalDateTime createDate;

        @Builder
        public ManagerShopJoinRequestResponse(com.heekng.celloct.entity.JoinRequest joinRequest) {
            this.joinRequestId = joinRequest.getId();
            this.memberId = joinRequest.getMember().getId();
            this.memberName = joinRequest.getMember().getName();
            this.createDate = joinRequest.getCreateDate();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ApprovalRefusalRequest {
        private Long joinRequestId;

        @Builder
        public ApprovalRefusalRequest(Long joinRequestId) {
            this.joinRequestId = joinRequestId;
        }
    }

}
