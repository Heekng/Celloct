package com.heekng.celloct.dto;

import com.heekng.celloct.entity.Manager;
import lombok.Builder;
import lombok.Getter;
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
        private Long id;
        private String name;

        @Builder
        public managerResponse(Manager manager) {
            this.id = manager.getId();
            this.name = manager.getName();
        }
    }

    @Getter
    @Setter
    public static class withMemberResponse {
        private Long managerId;
        private String managerName;
        private Long memberId;
        private String memberName;
        private String email;

        @Builder
        public withMemberResponse(Manager manager) {
            this.managerId = manager.getId();
            this.managerName = manager.getName();
            this.memberId = manager.getMember().getId();
            this.memberName = manager.getMember().getName();
            this.email = manager.getMember().getEmail();
        }
    }

    @Getter
    @Setter
    public static class updateRequest {
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    public static class DeleteRequest {
        private Long managerId;
        private Long shopId;

        @Builder
        public DeleteRequest(Long managerId, Long shopId) {
            this.managerId = managerId;
            this.shopId = shopId;
        }
    }




}
