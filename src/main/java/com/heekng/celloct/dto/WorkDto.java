package com.heekng.celloct.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkDto {

    @Getter
    public static class AddRequest {

        private LocalDate workDate;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String note;
        private Long staffId;

        @Builder
        public AddRequest(LocalDate workDate, LocalDateTime startDate, LocalDateTime endDate, String note, Long staffId) {
            this.workDate = workDate;
            this.startDate = startDate;
            this.endDate = endDate;
            this.note = note;
            this.staffId = staffId;
        }
    }

    @Getter
    public static class ChangeWorkTimeRequest {
        private Long workId;
        private LocalDateTime changeStartDate;
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
