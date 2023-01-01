package com.heekng.celloct.repository

import com.heekng.celloct.entity.Manager

interface ManagerRepositoryCustom {

    fun find(
        name: String? = null,
        shopId: Long? = null,
        memberId: Long? = null,
    ): List<Manager>

}