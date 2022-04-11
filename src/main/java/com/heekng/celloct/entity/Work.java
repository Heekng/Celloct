package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Work {

    @Id @GeneratedValue
    @Column(name = "work_id")
    private Long id;

    private LocalDate workDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String note;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "work_update_request_id")
    private WorkUpdateRequest workUpdateRequest;

    @Builder
    public Work(LocalDate workDate, Staff staff, LocalDateTime startDate, LocalDateTime endDate, String note) {
        this.workDate = workDate;
        this.staff = staff;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
    }

    public void changeWorkTime(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addWorkUpdateRequest(WorkUpdateRequest workUpdateRequest) {
        this.workUpdateRequest = workUpdateRequest;
    }

    public void deleteWorkUpdateRequest() {
        this.workUpdateRequest = null;
    }
}
