package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {

    @Id @GeneratedValue
    @Column(name = "shop_id")
    private Long id;

    private String phone;
    private String name;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<JoinRequest> joinRequests = new ArrayList<>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Manager> managers = new ArrayList<>();

    @Builder
    public Shop(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }
}
