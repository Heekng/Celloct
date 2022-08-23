package com.heekng.celloct.repository

import com.heekng.celloct.entity.WorkUpdateRequest
import java.time.LocalDate
import java.time.LocalDateTime

interface WorkUpdateRequestRepositoryCustom {

    fun find(
        workDate: LocalDate? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
        note: String? = null,
        workId: Long? = null,
    ): List<WorkUpdateRequest>

    fun findOneQ(
        workDate: LocalDate? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
        note: String? = null,
        workId: Long? = null,
    ): WorkUpdateRequest?

}