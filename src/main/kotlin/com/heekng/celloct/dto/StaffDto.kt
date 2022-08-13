package com.heekng.celloct.dto

import com.heekng.celloct.entity.Staff
import java.time.LocalDate

class StaffDto {
    data class AddRequest (val shopId: Long, val memberId: Long)

    data class UpdateEmploymentDateRequest (
        val staffId: Long,
        val changeEmploymentDate: LocalDate
    )

    data class StaffResponse (
        val id: Long?,
        val name: String,
    ) {
        constructor(staff: Staff): this(
            id = staff.id,
            name = staff.name,
        )
    }

    data class WithMemberResponse (
        val staffId: Long?,
        val staffName: String,
        val memberId: Long?,
        val memberName: String,
        val email: String,
    ) {
        constructor(staff: Staff): this(
            staffId = staff.id,
            staffName = staff.name,
            memberId = staff.member.id,
            memberName = staff.member.name,
            email = staff.member.email,
        )
    }

    data class DeleteRequest (
        val staffId: Long,
        val shopId: Long,
        val memberId: Long
    )

    data class UpdateRequest (
        var staffId: Long,
        val name: String,
        var shopId: Long,
        val memberId: Long? = null,
        val staffEmploymentDate: String? = null,
    )

    data class StaffDetailResponse (
        val id: Long?,
        val name: String,
        val employmentDate: LocalDate,
    ) {
        constructor(staff: Staff): this(
            id = staff.id,
            name = staff.name,
            employmentDate = staff.employmentDate,
        )
    }
}