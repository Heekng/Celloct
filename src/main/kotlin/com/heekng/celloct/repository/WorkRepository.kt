package com.heekng.celloct.repository

import com.heekng.celloct.entity.Work
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface WorkRepository : JpaRepository<Work, Long> {

    fun findByWorkTimeWorkDateAndStaffId(workDate: LocalDate, staffId: Long): List<Work>

    fun findByWorkTimeWorkDateBetweenAndStaffId(start: LocalDate, end: LocalDate, staffId: Long): List<Work>

    fun findByIdAndStaffId(id: Long, staffId: Long): Work?

}