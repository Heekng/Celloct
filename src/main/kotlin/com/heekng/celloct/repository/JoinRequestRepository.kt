package com.heekng.celloct.repository

import com.heekng.celloct.entity.JoinRequest
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface JoinRequestRepository : JpaRepository<JoinRequest, Long>, JoinRequestRepositoryCustom {

    @EntityGraph(attributePaths = ["member"])
    fun findWithMemberByShopId(shopId: Long): List<JoinRequest>

}