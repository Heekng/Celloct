package com.heekng.celloct.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Shop {
    @Id
    @Column(name = "shop_id")
    private Long id;

    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Staff> staffList = new ArrayList<>();
}
