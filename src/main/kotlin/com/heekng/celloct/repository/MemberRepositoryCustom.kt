package com.heekng.celloct.repository

import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Role

interface MemberRepositoryCustom {

    fun find(
        name: String? = null,
        email: String? = null,
        picture: String? = null,
        role: Role? = null,
    ): List<Member>
}