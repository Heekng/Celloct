package com.heekng.celloct.repository

import com.heekng.celloct.entity.Manager
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ManagerRepository : JpaRepository<Manager, Long>, ManagerRepositoryCustom {

    @EntityGraph(attributePaths = ["shop"])
    fun findWithShopByMemberId(memberId: Long): List<Manager>

    @EntityGraph(attributePaths = ["member"])
    fun findWithMemberById(id: Long): Manager

    fun findByShopIdAndId(shopId: Long, id: Long): Manager?
}