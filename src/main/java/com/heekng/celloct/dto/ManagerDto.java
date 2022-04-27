package com.heekng.celloct.dto;

import com.heekng.celloct.entity.Manager;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Getter
    @Setter
    public static class managerResponse {
        private Long managerId;
        private String name;

        @Builder
        public managerResponse(Manager manager) {
            this.managerId = manager.getId();
            this.name = manager.getName();
        }
    }

}
