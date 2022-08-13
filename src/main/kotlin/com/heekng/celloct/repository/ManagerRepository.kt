package com.heekng.celloct.repository

import com.heekng.celloct.entity.Manager
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ManagerRepository : JpaRepository<Manager, Long> {

    fun findByMemberIdAndShopId(memberId: Long, shopId: Long): Manager?

    fun findByMemberId(memberId: Long): List<Manager>

    @EntityGraph(attributePaths = ["shop"])
    fun findWithShopByMemberId(memberId: Long): List<Manager>

    fun findListByShopId(shopId: Long): List<Manager>

    @EntityGraph(attributePaths = ["member"])
    fun findWithMemberById(id: Long): Manager

    fun findByShopIdAndId(shopId: Long, id: Long): Manager?
}