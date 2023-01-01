package com.heekng.celloct.repository

import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.QMember.member
import com.heekng.celloct.entity.Role
import com.querydsl.jpa.impl.JPAQueryFactory

class MemberRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MemberRepositoryCustom {

    override fun find(name: String?, email: String?, picture: String?, role: Role?): List<Member> {
        return queryFactory.select(member)
            .from(member)
            .where(
                name?.let { member.name.eq(name) },
                email?.let { member.email.eq(email) },
                picture?.let { member.picture.eq(picture) },
                role?.let { member.role.eq(role) },
            )
            .fetch()
    }

}