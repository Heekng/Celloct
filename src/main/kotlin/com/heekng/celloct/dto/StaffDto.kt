package com.heekng.celloct.dto

import com.heekng.celloct.entity.Staff
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDate

class StaffDto {
    data class AddRequest (val shopId: Long, val memberId: Long)

    data class UpdateEmploymentDateRequest (
        val staffId: Long,
        val changeEmploymentDate: LocalDate
    )

    data class StaffResponse (val staff: Staff) {
        val id: Long?
        val name: String

        init {
            id = staff.id
            name = staff.name
        }
    }

    data class WithMemberResponse (val staff: Staff) {
        val staffId: Long?
        val staffName: String
        val memberId: Long?
        val memberName: String
        val email: String

        init {
            staffId = staff.id
            staffName = staff.name
            memberId = staff.member.id
            memberName = staff.member.name
            email = staff.member.email
        }
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

    data class StaffDetailResponse (val staff: Staff) {
        val id: Long?
        val name: String
        val employmentDate: LocalDate

        init {
            id = staff.id
            name = staff.name
            employmentDate = staff.employmentDate
        }
    }
}