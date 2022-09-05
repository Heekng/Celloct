package com.heekng.celloct.repository

import com.heekng.celloct.entity.QShop.shop
import com.heekng.celloct.entity.QStaff.staff
import com.heekng.celloct.entity.QWork.work
import com.heekng.celloct.entity.QWorkUpdateRequest.workUpdateRequest
import com.heekng.celloct.entity.Work
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDate
import java.time.LocalDateTime

class WorkRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : WorkRepositoryCustom {

    override fun find(
        note: String?,
        staffId: Long?,
        workUpdateRequestId: Long?,
        workDate: LocalDate?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): List<Work> {
        return queryFactory.select(work)
            .from(work)
            .where(
                note?.let { work.note.eq(note) },
                staffId?.let { work.staff.id.eq(staffId) },
                workUpdateRequestId?.let { work.workUpdateRequest.id.eq(workUpdateRequestId) },
                workDate?.let { work.workTime.workDate.eq(workDate) },
                startDate?.let { work.workTime.startDate.eq(startDate) },
                endDate?.let { work.workTime.endDate.eq(endDate) },
            )
            .fetch()
    }

    override fun findOneQ(
        note: String?,
        staffId: Long?,
        workUpdateRequestId: Long?,
        workDate: LocalDate?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): Work? {
        return queryFactory.select(work)
            .from(work)
            .where(
                note?.let { work.note.eq(note) },
                staffId?.let { work.staff.id.eq(staffId) },
                workUpdateRequestId?.let { work.workUpdateRequest.id.eq(workUpdateRequestId) },
                workDate?.let { work.workTime.workDate.eq(workDate) },
                startDate?.let { work.workTime.startDate.eq(startDate) },
                endDate?.let { work.workTime.endDate.eq(endDate) },
            )
            .fetchOne()
    }

    override fun findByShopId(shopId: Long): List<Work> {
        return queryFactory.select(work)
            .from(shop)
            .leftJoin(shop.staffList, staff)
            .leftJoin(staff.works, work)
            .leftJoin(work.workUpdateRequest, workUpdateRequest)
            .fetchJoin()
            .where(shop.id.eq(shopId))
            .orderBy(work.workUpdateRequest.createDate.desc())
            .fetch()
    }

    override fun findOneWithWorkUpdateRequestById(id: Long): Work? {
        return queryFactory.select(work)
            .from(work)
            .leftJoin(work.workUpdateRequest, workUpdateRequest)
            .fetchJoin()
            .where(work.id.eq(id))
            .fetchOne()
    }
}