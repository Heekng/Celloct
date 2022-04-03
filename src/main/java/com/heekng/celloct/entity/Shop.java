package com.heekng.celloct.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Shop {
    @Id
    @Column(name = "shop_id")
    private Long id;

    private String phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;
}
