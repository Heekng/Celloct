package com.heekng.celloct.repository

import com.heekng.celloct.entity.Shop
import org.springframework.data.jpa.repository.JpaRepository

interface ShopRepository : JpaRepository<Shop, Long>, ShopRepositoryCustom {

    fun findListByNameContaining(name: String): List<Shop>

}