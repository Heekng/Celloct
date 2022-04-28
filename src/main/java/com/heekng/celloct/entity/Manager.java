package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "manager_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Manager(Shop shop, Member member, String name) {
        this.shop = shop;
        this.member = member;
        this.name = name;
        shop.getManagers().add(this);
        member.getManagers().add(this);
    }

    public void updateInfo(String name) {
        this.name = name;
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
