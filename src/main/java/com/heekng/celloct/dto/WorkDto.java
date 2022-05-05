package com.heekng.celloct.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkDto {

    @Getter
    @Setter
    public static class AddRequest {

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate workDate;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime startDate;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime endDate;
        private String note;
        private Long memberId;
        private Long shopId;

        @Builder
        public AddRequest(LocalDate workDate, LocalDateTime startDate, LocalDateTime endDate, String note, Long memberId, Long shopId) {
            this.workDate = workDate;
            this.startDate = startDate;
            this.endDate = endDate;
            this.note = note;
            this.memberId = memberId;
            this.shopId = shopId;
        }
    }

    @Getter
    public static class ChangeWorkTimeRequest {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Long workId;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDateTime changeStartDate;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDateTime changeEndDate;

        @Builder
        public ChangeWorkTimeRequest(Long workId, LocalDateTime changeStartDate, LocalDateTime changeEndDate) {
            this.workId = workId;
            this.changeStartDate = changeStartDate;
            this.changeEndDate = changeEndDate;
        }
    }

    @Getter
    @Setter
    public static class CheckExistRequest {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate workDate;
        private Long memberId;
        private Long shopId;
    }


}
