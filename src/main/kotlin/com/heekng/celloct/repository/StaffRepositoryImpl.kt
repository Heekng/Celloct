package com.heekng.celloct.repository

import com.heekng.celloct.entity.QStaff.staff
import com.heekng.celloct.entity.Staff
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDate

class StaffRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : StaffRepositoryCustom {

    override fun find(
        employmentDate: LocalDate?,
        name: String?,
        memberId: Long?,
        shopId: Long?
    ): List<Staff> {
        return queryFactory.select(staff)
            .from(staff)
            .where(
                employmentDate?.let { staff.employmentDate.eq(employmentDate) },
                name?.let { staff.name.eq(name) },
                memberId?.let { staff.member.id.eq(memberId) },
                shopId?.let { staff.shop.id.eq(shopId) },
            )
            .fetch()
    }

    override fun findOneQ(employmentDate: LocalDate?, name: String?, memberId: Long?, shopId: Long?): Staff? {
        return queryFactory.select(staff)
            .from(staff)
            .where(
                employmentDate?.let { staff.employmentDate.eq(employmentDate) },
                name?.let { staff.name.eq(name) },
                memberId?.let { staff.member.id.eq(memberId) },
                shopId?.let { staff.shop.id.eq(shopId) },
            )
            .fetchOne()
    }

}