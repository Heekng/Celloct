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
@IdClass(StaffId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Staff(Member member, Shop shop, LocalDateTime employmentDate) {
        this.member = member;
        this.shop = shop;
        this.employmentDate = employmentDate;
    }
}
