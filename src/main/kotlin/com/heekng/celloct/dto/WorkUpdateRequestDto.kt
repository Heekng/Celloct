package com.heekng.celloct.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime

class WorkUpdateRequestDto {
    data class AddRequest (
        var workId: Long,
        var staffId: Long,
        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val workDate: LocalDate,
        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val startDate: LocalDateTime,
        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val endDate: LocalDateTime,
        val note: String? = null
    )

    data class UpdateRequest (
        val workUpdateRequestId: Long,
        val updateDate: LocalDate,
        val updateStartDate: LocalDateTime,
        val updateEndDate: LocalDateTime
    )
}