package com.heekng.celloct.dto;

import com.heekng.celloct.entity.Staff;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StaffDto {

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
    public static class updateEmploymentDateRequest {

        private Long staffId;
        private LocalDate changeEmploymentDate;

        @Builder
        public updateEmploymentDateRequest(Long staffId, LocalDate changeEmploymentDate) {
            this.staffId = staffId;
            this.changeEmploymentDate = changeEmploymentDate;
        }
    }

    @Getter
    @Setter
    public static class staffResponse {
        private Long id;
        private String name;

        @Builder
        public staffResponse(Staff staff) {
            this.id = staff.getId();
            this.name = staff.getName();
        }
    }

    @Getter
    @Setter
    public static class WithMemberResponse {
        private Long staffId;
        private String staffName;
        private Long memberId;
        private String memberName;
        private String email;

        @Builder
        public WithMemberResponse(Staff staff) {
            this.staffId = staff.getId();
            this.staffName = staff.getName();
            this.memberId = staff.getMember().getId();
            this.memberName = staff.getMember().getName();
            this.email = staff.getMember().getEmail();
        }
    }

    @Getter
    @Setter
    public static class DeleteRequest {
        private Long staffId;
        private Long shopId;
        private Long memberId;

        @Builder
        public DeleteRequest(Long staffId, Long shopId, Long memberId) {
            this.staffId = staffId;
            this.shopId = shopId;
            this.memberId = memberId;
        }
    }




}
