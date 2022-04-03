package com.heekng.celloct.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Staff {

    @Id
    @Column(name = "staff_id")
    private Long id;

    private LocalDateTime employmentDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Work> works = new ArrayList<>();


}
