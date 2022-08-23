package com.heekng.celloct.repository

import com.heekng.celloct.entity.QShop
import com.heekng.celloct.entity.QShop.*
import com.heekng.celloct.entity.Shop
import com.querydsl.jpa.impl.JPAQueryFactory

class ShopRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ShopRepositoryCustom {

    override fun find(
        phone: String?,
        name: String?,
        info: String?
    ): List<Shop> {
        return queryFactory.select(shop)
            .from(shop)
            .where(
                phone?.let { shop.phone.eq(phone) },
                name?.let { shop.name.eq(name) },
                info?.let { shop.info.eq(info) },
            )
            .fetch()
    }

}