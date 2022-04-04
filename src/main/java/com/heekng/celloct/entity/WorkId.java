package com.heekng.celloct.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode
public class WorkId implements Serializable {

    private LocalDate workDate;
    private Staff staff;
}
