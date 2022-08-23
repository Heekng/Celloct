package com.heekng.celloct.repository

import com.heekng.celloct.entity.Work
import java.time.LocalDate
import java.time.LocalDateTime

interface WorkRepositoryCustom {

    fun find(
        note: String? = null,
        staffId: Long? = null,
        workUpdateRequestId: Long? = null,
        workDate: LocalDate? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): List<Work>

    fun findOneQ(
        note: String? = null,
        staffId: Long? = null,
        workUpdateRequestId: Long? = null,
        workDate: LocalDate? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): Work?

}