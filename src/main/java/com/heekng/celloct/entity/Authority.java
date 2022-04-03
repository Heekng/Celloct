package com.heekng.celloct.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@IdClass(AuthorityId.class)
public class Authority {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @Enumerated(EnumType.STRING)
    private Role role;

}
