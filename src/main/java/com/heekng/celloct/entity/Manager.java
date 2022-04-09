package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager {

    @Id @GeneratedValue
    @Column(name = "manager_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Manager(Shop shop, Member member) {
        this.shop = shop;
        this.member = member;
        shop.getManagers().add(this);
        member.getManagers().add(this);
    }

    public void addShop(Shop shop) {
        this.shop = shop;
        shop.getManagers().add(this);
    }

    public void addMember(Member member) {
        this.member = member;
        member.getManagers().add(this);
    }
}
