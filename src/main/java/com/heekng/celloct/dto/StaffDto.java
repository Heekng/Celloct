package com.heekng.celloct.dto;

import com.heekng.celloct.entity.Staff;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class StaffDto {

    @Getter
    public static class AddRequest {

        private Long shopId;
        private Long memberId;

        @Builder
        public AddRequest(Long shopId, Long memberId) {
            this.shopId = shopId;
            this.memberId = memberId;
        }
    }

    @Getter
    public static class UpdateEmploymentDateRequest {

        private Long staffId;
        private LocalDate changeEmploymentDate;

        @Builder
        public UpdateEmploymentDateRequest(Long staffId, LocalDate changeEmploymentDate) {
            this.staffId = staffId;
            this.changeEmploymentDate = changeEmploymentDate;
        }
    }

    @Getter
    @Setter
    public static class StaffResponse {
        private Long id;
        private String name;

        @Builder
        public StaffResponse(Staff staff) {
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

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long staffId;
        private String name;
        private Long shopId;
        private Long memberId;
        private String staffEmploymentDate;
    }

    @Getter
    @Setter
    public static class StaffDetailResponse {
        private Long id;
        private String name;
        private LocalDate employmentDate;

        @Builder
        public StaffDetailResponse(Staff staff) {
            this.id = staff.getId();
            this.name = staff.getName();
            this.employmentDate = staff.getEmploymentDate();
        }
    }


}
