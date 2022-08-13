package com.heekng.celloct.dto

import com.heekng.celloct.entity.Manager

class ManagerDto {
    data class AddRequest (
        val shopId: Long,
        val memberId: Long
    )

    data class ManagerResponse (
        val id: Long?,
        val name: String,
    ) {
        constructor(manager: Manager): this(
            id = manager.id,
            name = manager.name,
        )
    }

    data class WithMemberResponse (
        val managerId: Long?,
        val managerName: String,
        val memberId: Long?,
        val memberName: String,
        val email: String,
    ) {
        constructor(manager: Manager): this(
            managerId = manager.id,
            managerName = manager.name,
            memberId = manager.member.id,
            memberName = manager.member.name,
            email = manager.member.email,
        )
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