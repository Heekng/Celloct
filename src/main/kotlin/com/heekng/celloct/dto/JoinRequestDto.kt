package com.heekng.celloct.dto

import java.time.LocalDateTime

class JoinRequestDto {
    data class JoinRequest (
        var memberId: Long,
        val shopId: Long
    )

    data class ManagerShopJoinRequestResponse (val joinRequest: com.heekng.celloct.entity.JoinRequest) {
        val joinRequestId: Long?
        val memberId: Long?
        val memberName: String
        val createDate: LocalDateTime?

        init {
            joinRequestId = joinRequest.id
            memberId = joinRequest.member.id
            memberName = joinRequest.member.name
            createDate = joinRequest.createDate
        }
    }

    data class ApprovalRefusalRequest (val joinRequestId: Long)
}