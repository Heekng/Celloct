package com.heekng.celloct.dto

import com.heekng.celloct.entity.WorkUpdateRequest
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime

class WorkUpdateRequestDto {
    data class AddRequest (
        var workId: Long,
        var staffId: Long,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val workDate: LocalDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val startDate: LocalDateTime,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val endDate: LocalDateTime,
        val note: String? = null
    )

    data class UpdateRequest (
        val workUpdateRequestId: Long,
        val updateDate: LocalDate,
        val updateStartDate: LocalDateTime,
        val updateEndDate: LocalDateTime
    )

    data class WorkUpdateRequestListResponse (
        val staffName: String,
        val workUpdateRequestId: Long,
        val workId: Long,
        val createDate: LocalDateTime,
        val workDate: LocalDate,
    ) {
        constructor(workUpdateRequest: WorkUpdateRequest): this(
            staffName = workUpdateRequest.work.staff.name,
            workUpdateRequestId = workUpdateRequest.id!!,
            workId = workUpdateRequest.work.id!!,
            createDate = workUpdateRequest.createDate!!,
            workDate = workUpdateRequest.work.workTime.workDate,
        )
    }
}