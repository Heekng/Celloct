package com.heekng.celloct.dto

import com.heekng.celloct.entity.Manager

class ManagerDto {
    data class AddRequest (
        val shopId: Long,
        val memberId: Long
    )

    data class ManagerResponse (val manager: Manager) {
        val id: Long?
        val name: String

        init {
            id = manager.id
            name = manager.name
        }
    }

    data class WithMemberResponse (val manager: Manager) {
        val managerId: Long?
        val managerName: String
        val memberId: Long?
        val memberName: String
        val email: String

        init {
            managerId = manager.id
            managerName = manager.name
            memberId = manager.member.id
            memberName = manager.member.name
            email = manager.member.email
        }
    }

    data class UpdateRequest (var id: Long?, val name: String)

    class DeleteRequest (
        val managerId: Long,
        val shopId: Long,
        val memberId: Long? = null
    )

    data class AddByStaffRequest (val shopId: Long, val staffId: Long)

    data class AddByStaffResponse (val manager: Manager) {
        val managerId: Long?

        init {
            managerId = manager.id
        }
    }
}