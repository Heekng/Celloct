package com.heekng.celloct.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkUpdateRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AddRequest {
        private Long workId;
        private Long staffId;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate workDate;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime startDate;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime endDate;
        private String note;

        @Builder
        public AddRequest(Long workId, Long staffId, LocalDate workDate, LocalDateTime startDate, LocalDateTime endDate, String note) {
            this.workId = workId;
            this.staffId = staffId;
            this.workDate = workDate;
            this.startDate = startDate;
            this.endDate = endDate;
            this.note = note;
        }
    }

    @Getter
    public static class updateRequest {
        private Long workUpdateRequestId;
        private LocalDate updateDate;
        private LocalDateTime updateStartDate;
        private LocalDateTime updateEndDate;

        @Builder
        public updateRequest(Long workUpdateRequestId, LocalDate updateDate, LocalDateTime updateStartDate, LocalDateTime updateEndDate) {
            this.workUpdateRequestId = workUpdateRequestId;
            this.updateDate = updateDate;
            this.updateStartDate = updateStartDate;
            this.updateEndDate = updateEndDate;
        }
    }


}
