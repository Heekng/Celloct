package com.heekng.celloct.repository

import com.heekng.celloct.entity.Shop

interface ShopRepositoryCustom {

    fun find(
        phone: String? = null,
        name: String? = null,
        info: String? = null,
    ): List<Shop>

}