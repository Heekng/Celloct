package com.heekng.celloct.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class StaffId implements Serializable {
    private Member member;
    private Shop shop;
}
