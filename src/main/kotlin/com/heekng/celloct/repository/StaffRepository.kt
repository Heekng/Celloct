package com.heekng.celloct.repository

import com.heekng.celloct.entity.Staff
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface StaffRepository : JpaRepository<Staff, Long>, StaffRepositoryCustom {

    @EntityGraph(attributePaths = ["member"])
    fun findWithMemberById(id: Long): Staff

    fun findByShopIdAndId(shopId: Long, id: Long): Staff?

    @EntityGraph(attributePaths = ["shop"])
    fun findWithShopByMemberId(memberId: Long): List<Staff>
}