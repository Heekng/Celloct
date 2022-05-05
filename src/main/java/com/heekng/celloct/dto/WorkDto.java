package com.heekng.celloct.dto;

import com.heekng.celloct.entity.Work;
import com.heekng.celloct.entity.WorkTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Getter
    @Setter
    public static class FindWorkRequest {
        private Long staffId;
        private Long shopId;
        private Integer year;
        private Integer month;

        @Builder
        public FindWorkRequest(Long staffId, Long shopId, Integer year, Integer month) {
            this.staffId = staffId;
            this.shopId = shopId;
            this.year = year;
            this.month = month;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class WorkResponse {
        private Long workId;
        private LocalDate workDate;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String note;

        @Builder
        public WorkResponse(Work work) {
            this.workId = work.getId();
            this.workDate = work.getWorkTime().getWorkDate();
            this.startDate = work.getWorkTime().getStartDate();
            this.endDate = work.getWorkTime().getEndDate();
            this.note = work.getNote();
        }
    }




}
