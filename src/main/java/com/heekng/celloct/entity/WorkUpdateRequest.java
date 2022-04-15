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
public class WorkUpdateRequest {

    @Id @GeneratedValue
    @Column(name = "work_update_request_id")
    private Long id;

    private LocalDate updateWorkDate;
    private LocalDateTime updateStartDate;
    private LocalDateTime updateEndDate;

    @OneToOne(mappedBy = "workUpdateRequest", fetch = LAZY)
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
