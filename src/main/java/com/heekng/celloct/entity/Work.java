package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Work extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "work_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate workDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;
    private String note;

    @OneToOne(fetch = LAZY, cascade = REMOVE)
    @JoinColumn(name = "work_update_request_id", nullable = false)
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
