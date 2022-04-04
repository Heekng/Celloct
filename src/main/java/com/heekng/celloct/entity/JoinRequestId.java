package com.heekng.celloct.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class JoinRequestId implements Serializable {
    private Member member;
    private Shop shop;
}
