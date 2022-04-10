package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Staff {

    @Id @GeneratedValue
    @Column(name = "staff_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Work> works = new ArrayList<>();

    private LocalDateTime employmentDate;

    @Builder
    public Staff(Member member, Shop shop) {
        this.member = member;
        this.shop = shop;
        this.employmentDate = LocalDateTime.now();
        shop.getStaffList().add(this);
        member.getStaffList().add(this);
    }

    public void changeEmploymentDate(LocalDateTime changeEmploymentDate) {
        this.employmentDate = changeEmploymentDate;
    }

}
