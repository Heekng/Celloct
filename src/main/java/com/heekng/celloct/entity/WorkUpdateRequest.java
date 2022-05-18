package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkUpdateRequest extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "work_update_request_id")
    private Long id;

    @Embedded
    WorkTime workTime;

    @Column(name = "note")
    String note;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "work_id", nullable = false, unique = true)
    private Work work;

    @Builder
    public WorkUpdateRequest(LocalDate workDate, LocalDateTime startDate, LocalDateTime endDate, Work work, String note) {
        this.work = work;
        this.workTime = WorkTime.builder()
                .workDate(workDate)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        this.note = note;
    }
}
