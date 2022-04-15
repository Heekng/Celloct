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
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;
    private String picture;

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private List<JoinRequest> joinRequests = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private List<Manager> managers = new ArrayList<>();

    @Builder
    public Member(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

}
