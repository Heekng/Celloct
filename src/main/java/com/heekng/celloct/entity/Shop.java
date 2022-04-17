package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "shop_id")
    private Long id;

    private String phone;
    private String name;
    private String info;

    @OneToMany(mappedBy = "shop", cascade = REMOVE)
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "shop", cascade = REMOVE)
    private List<JoinRequest> joinRequests = new ArrayList<>();

    @OneToMany(mappedBy = "shop", cascade = ALL)
    private List<Manager> managers = new ArrayList<>();

    @Builder
    public Shop(String phone, String name, String info) {
        this.phone = phone;
        this.name = name;
        this.info = info;
    }

    public void update(String phone, String info) {
        this.phone = phone;
        this.info = info;
    }
}
