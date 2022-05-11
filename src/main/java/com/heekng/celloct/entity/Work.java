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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "work_work_date_staff_id_unique",
                        columnNames = {"work_date", "staff_id"}
                )
        }
)
public class Work extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "work_id")
    private Long id;

    @Embedded
    private WorkTime workTime;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @OneToOne(mappedBy = "work", fetch = LAZY, cascade = REMOVE)
    @JoinColumn(name = "work_update_request_id")
    private WorkUpdateRequest workUpdateRequest;

    @Builder
    public Work(LocalDate workDate, Staff staff, LocalDateTime startDate, LocalDateTime endDate, String note) {
        this.staff = staff;
        this.note = note;
        this.workTime = WorkTime.builder()
                .workDate(workDate)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public void addWorkUpdateRequest(WorkUpdateRequest workUpdateRequest) {
        this.workUpdateRequest = workUpdateRequest;
    }

    public void deleteWorkUpdateRequest() {
        this.workUpdateRequest = null;
    }
}
