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

    private String loginId;
    private String email;
    private String password;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<JoinRequest> joinRequests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Manager> managers = new ArrayList<>();

    @Builder
    public Member(String loginId, String email, String password, String name) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
