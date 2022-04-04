package com.heekng.celloct.entity;

import lombok.Getter;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@IdClass(WorkId.class)
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

}
