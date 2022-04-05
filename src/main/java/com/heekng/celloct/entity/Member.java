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
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Shop> shops = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<JoinRequest> joinRequests = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, List<Authority> authorities, List<Shop> shops, List<JoinRequest> joinRequests) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
        this.shops = shops;
        this.joinRequests = joinRequests;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
