package com.heekng.celloct.repository

import com.heekng.celloct.entity.Staff
import java.time.LocalDate

interface StaffRepositoryCustom {

    fun find(
        employmentDate: LocalDate? = null,
        name: String? = null,
        memberId: Long? = null,
        shopId: Long? = null,
    ): List<Staff>

    fun findOneQ(
        employmentDate: LocalDate? = null,
        name: String? = null,
        memberId: Long? = null,
        shopId: Long? = null,
    ): Staff?

}