package com.heekng.celloct.entity

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class WorkTime(
    @Column(nullable = false, name = "work_date")
    val workDate: LocalDate,

    @Column(nullable = false, name = "start_date")
    var startDate: LocalDateTime,

    @Column(nullable = false, name = "end_date")
    var endDate: LocalDateTime,
) {

    companion object {
        fun fixture(
            workDate: LocalDate,
            startDate: LocalDateTime,
            endDate: LocalDateTime,
        ): WorkTime {
            return WorkTime(
                workDate = workDate,
                startDate = startDate,
                endDate = endDate,
            )
        }
    }

    fun changeWorkTime(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ) {
        this.startDate = startDate
        this.endDate = endDate
    }

    fun workTimeHour(): Long {
        val seconds = Duration.between(startDate, endDate).seconds
        return seconds / 3600
    }

    fun workTimeMinute(): Long {
        val seconds = Duration.between(startDate, endDate).seconds
        return seconds % 3600 / 60
    }
}