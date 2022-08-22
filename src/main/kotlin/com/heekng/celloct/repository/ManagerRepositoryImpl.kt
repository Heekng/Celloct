package com.heekng.celloct.repository

import com.heekng.celloct.entity.Manager
import com.heekng.celloct.entity.QManager.manager
import com.querydsl.jpa.impl.JPAQueryFactory

class ManagerRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ManagerRepositoryCustom {

    override fun find(name: String?, shopId: Long?, memberId: Long?): List<Manager> {
        return queryFactory.select(manager)
            .from(manager)
            .where(
                name?.let { manager.name.eq(name) },
                shopId?.let { manager.shop.id.eq(shopId) },
                memberId?.let { manager.member.id.eq(memberId) },
            )
            .fetch()
    }

}