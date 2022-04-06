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
@IdClass(WorkId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Work {

    @Id
    private LocalDate workDate;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "member_id"),
            @JoinColumn(name = "shop_id")
    })
    private Staff staff;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String note;

    @Builder
    public Work(LocalDate workDate, Staff staff, LocalDateTime startDate, LocalDateTime endDate, String note) {
        this.workDate = workDate;
        this.staff = staff;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
    }
}
