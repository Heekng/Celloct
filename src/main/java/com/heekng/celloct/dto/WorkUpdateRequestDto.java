package com.heekng.celloct.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkUpdateRequestDto {

    @Getter
    public static class addRequest {
        private Long workId;
        private LocalDate updateDate;
        private LocalDateTime updateStartDate;
        private LocalDateTime updateEndDate;

        @Builder
        public addRequest(Long workId, LocalDate updateDate, LocalDateTime updateStartDate, LocalDateTime updateEndDate) {
            this.workId = workId;
            this.updateDate = updateDate;
            this.updateStartDate = updateStartDate;
            this.updateEndDate = updateEndDate;
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
