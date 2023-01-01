package com.heekng.celloct.repository

import com.heekng.celloct.entity.JoinRequest

interface JoinRequestRepositoryCustom {

    fun find(
        shopId: Long? = null,
        memberId: Long? = null,
    ): List<JoinRequest>

}