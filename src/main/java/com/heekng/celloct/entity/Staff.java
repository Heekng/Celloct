package com.heekng.celloct.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@IdClass(StaffId.class)
public class Staff {

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Work> works = new ArrayList<>();

    private LocalDateTime employmentDate;
}
