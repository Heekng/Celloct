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

    @Column(nullable = false)
    private LocalDate updateWorkDate;

    @Column(nullable = false)
    private LocalDateTime updateStartDate;

    @Column(nullable = false)
    private LocalDateTime updateEndDate;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "work_id", nullable = false)
    private Work work;

    @Builder
    public WorkUpdateRequest(LocalDate updateWorkDate, LocalDateTime updateStartDate, LocalDateTime updateEndDate, Work work) {
        this.updateWorkDate = updateWorkDate;
        this.updateStartDate = updateStartDate;
        this.updateEndDate = updateEndDate;
        this.work = work;
    }

    public void updateWorkUpdateRequest(LocalDate updateWorkDate, LocalDateTime updateStartDate, LocalDateTime updateEndDate) {
        this.updateWorkDate = updateWorkDate;
        this.updateStartDate = updateStartDate;
        this.updateEndDate = updateEndDate;
    }
}
