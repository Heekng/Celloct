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
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private List<JoinRequest> joinRequests = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private List<Manager> managers = new ArrayList<>();

    @Builder
    public Member(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member updatePicture(String picture) {
        this.picture = picture;
        return this;
    }
}
