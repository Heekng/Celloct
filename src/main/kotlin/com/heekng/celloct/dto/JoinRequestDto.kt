package com.heekng.celloct.dto

import com.heekng.celloct.entity.JoinRequest
import java.time.LocalDateTime

class JoinRequestDto {
    data class JoinRequest (
        var memberId: Long,
        val shopId: Long
    )

    data class ManagerShopJoinRequestResponse (
        val joinRequestId: Long?,
        val memberId: Long?,
        val memberName: String,
        val createDate: LocalDateTime?,
    ) {
        constructor(joinRequest: com.heekng.celloct.entity.JoinRequest): this(
            joinRequestId = joinRequest.id,
            memberId = joinRequest.member.id,
            memberName = joinRequest.member.name,
            createDate = joinRequest.createDate,
        )
    }

    data class ApprovalRefusalRequest (val joinRequestId: Long)
}