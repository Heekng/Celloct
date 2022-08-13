package com.heekng.celloct.dto

import com.heekng.celloct.entity.Work
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime

class WorkDto {
    data class AddRequest (
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val workDate: LocalDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val startDate: LocalDateTime,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val endDate: LocalDateTime,
        val note: String? = null,
        var memberId: Long,
        var shopId: Long
    )

    data class ChangeWorkTimeRequest (
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val workId: Long,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val changeStartDate: LocalDateTime,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val changeEndDate: LocalDateTime
    )

    data class CheckExistRequest(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        val workDate: LocalDate,
        var memberId: Long,
        var shopId: Long,
    )

    data class FindWorkRequest (
        val staffId: Long,
        val shopId: Long,
        val year: Int,
        val month: Int
    )

    data class WorkResponse (val work: Work) {
        val workId: Long?
        val workDate: LocalDate
        val startDate: LocalDateTime
        val endDate: LocalDateTime
        val note: String?
        val hour: Long
        val minute: Long

        init {
            workId = work.id
            workDate = work.workTime.workDate
            startDate = work.workTime.startDate
            endDate = work.workTime.endDate
            note = work.note
            hour = work.workTime.workTimeHour()
            minute = work.workTime.workTimeMinute()
        }
    }

    data class WorkDetailResponse (val work: Work) {
        val workId: Long?
        val workDate: LocalDate
        val startDate: LocalDateTime
        val endDate: LocalDateTime
        val note: String?
        val lastModifiedDate: LocalDateTime?

        init {
            workId = work.id
            workDate = work.workTime.workDate
            startDate = work.workTime.startDate
            endDate = work.workTime.endDate
            note = work.note
            lastModifiedDate = work.lastModifiedDate
        }
    }

    data class UpdateRequest(
        var staffId: Long,
        val workId: Long,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val startDate: LocalDateTime,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val endDate: LocalDateTime,
        val note: String? = null,
    )

    data class DeleteRequest (var staffId: Long, val workId: Long)
}