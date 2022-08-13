package com.heekng.celloct.repository

import com.heekng.celloct.entity.JoinRequest
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface JoinRequestRepository : JpaRepository<JoinRequest, Long> {

    fun findByShopId(shopId: Long): List<JoinRequest>

    fun findByMemberId(memberId: Long): List<JoinRequest>

    fun findByMemberIdAndShopId(memberId: Long, shopId: Long): List<JoinRequest>

    @EntityGraph(attributePaths = ["member"])
    fun findWithMemberByShopId(shopId: Long): List<JoinRequest>

}