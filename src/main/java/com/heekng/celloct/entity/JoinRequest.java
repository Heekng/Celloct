package com.heekng.celloct.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@IdClass(JoinRequestId.class)
public class JoinRequest {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
