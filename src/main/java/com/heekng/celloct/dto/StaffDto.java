package com.heekng.celloct.dto;

import lombok.Builder;
import lombok.Getter;

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


}
