package com.heekng.celloct.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Work {

    @Id
    private LocalDate workDate;
    private Long staffId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String note;

}
