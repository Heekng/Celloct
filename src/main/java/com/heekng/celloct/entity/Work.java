package com.heekng.celloct.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Work {

    @Id
    private LocalDate workDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String note;

}
