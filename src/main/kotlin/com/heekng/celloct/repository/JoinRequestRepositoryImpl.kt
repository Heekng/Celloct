package com.heekng.celloct.repository

import com.heekng.celloct.entity.JoinRequest
import com.heekng.celloct.entity.QJoinRequest.*
import com.querydsl.jpa.impl.JPAQueryFactory

class JoinRequestRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : JoinRequestRepositoryCustom {

    override fun find(
        shopId: Long?,
        memberId: Long?,
    ): List<JoinRequest> {
        return queryFactory.select(joinRequest)
            .from(joinRequest)
            .where(
                shopId?.let { joinRequest.shop.id.eq(shopId) },
                memberId?.let { joinRequest.member.id.eq(memberId) },
            )
            .fetch()
    }

}