package com.heekng.celloct.config.oauth.dto;

import com.heekng.celloct.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String picture;

    public SessionMember(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
