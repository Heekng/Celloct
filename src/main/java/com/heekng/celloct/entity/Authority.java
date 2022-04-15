package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@IdClass(AuthorityId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Authority(Member member, Role role) {
        this.member = member;
        this.role = role;
    }
}
