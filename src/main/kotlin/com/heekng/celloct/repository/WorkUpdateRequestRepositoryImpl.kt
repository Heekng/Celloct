package com.heekng.celloct.repository

import com.heekng.celloct.entity.QWorkUpdateRequest.workUpdateRequest
import com.heekng.celloct.entity.WorkUpdateRequest
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDate
import java.time.LocalDateTime

class WorkUpdateRequestRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : WorkUpdateRequestRepositoryCustom {

    override fun find(
        workDate: LocalDate?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
        note: String?,
        workId: Long?
    ): List<WorkUpdateRequest> {
        return queryFactory.select(workUpdateRequest)
            .from(workUpdateRequest)
            .where(
                note?.let { workUpdateRequest.note.eq(note) },
                workDate?.let { workUpdateRequest.workTime.workDate.eq(workDate) },
                startDate?.let { workUpdateRequest.workTime.startDate.eq(startDate) },
                endDate?.let { workUpdateRequest.workTime.endDate.eq(endDate) },
                workId?.let { workUpdateRequest.work.id.eq(workId) },
            )
            .fetch()
    }

    override fun findOneQ(
        workDate: LocalDate?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
        note: String?,
        workId: Long?
    ): WorkUpdateRequest? {
        return queryFactory.select(workUpdateRequest)
            .from(workUpdateRequest)
            .where(
                note?.let { workUpdateRequest.note.eq(note) },
                workDate?.let { workUpdateRequest.workTime.workDate.eq(workDate) },
                startDate?.let { workUpdateRequest.workTime.startDate.eq(startDate) },
                endDate?.let { workUpdateRequest.workTime.endDate.eq(endDate) },
                workId?.let { workUpdateRequest.work.id.eq(workId) },
            )
            .fetchOne()
    }
}