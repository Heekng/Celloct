package com.heekng.celloct.repository

import com.heekng.celloct.entity.Staff
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface StaffRepository : JpaRepository<Staff, Long> {

    fun findByMemberIdAndShopId(memberId: Long, shopId: Long): Staff?

    fun findByShopId(shopId: Long): List<Staff>

    fun findByMemberId(memberId: Long): List<Staff>

    @EntityGraph(attributePaths = ["member"])
    fun findWithMemberById(id: Long): Staff

    fun findByShopIdAndId(shopId: Long, id: Long): Staff?

    @EntityGraph(attributePaths = ["shop"])
    fun findWithShopByMemberId(memberId: Long): List<Staff>
}