package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Staff extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "staff_id")
    private Long id;

    private LocalDate employmentDate;
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "staff", cascade = REMOVE)
    private List<Work> works = new ArrayList<>();


    @Builder
    public Staff(Member member, Shop shop, String name) {
        this.member = member;
        this.shop = shop;
        this.employmentDate = LocalDate.now();
        this.name = name;
        shop.getStaffList().add(this);
        member.getStaffList().add(this);
    }

    public void changeEmploymentDate(LocalDate changeEmploymentDate) {
        this.employmentDate = changeEmploymentDate;
    }

    public void updateInfo(String name) {
        this.name = name;
    }

}
