package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkTime {

    @Column(nullable = false)
    private LocalDate workDate;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;

    @Builder
    public WorkTime(LocalDate workDate, LocalDateTime startDate, LocalDateTime endDate) {
        this.workDate = workDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void changeWorkTime(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long workTimeHour() {
        Duration duration = Duration.between(startDate, endDate);
        long seconds = duration.getSeconds();
        return seconds / 3600;
    }

    public long workTimeMinute() {
        Duration duration = Duration.between(startDate, endDate);
        long seconds = duration.getSeconds();
        return seconds % 3600;
    }

}
