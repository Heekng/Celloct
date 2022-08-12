package com.heekng.celloct.repository

import com.heekng.celloct.entity.WorkUpdateRequest
import org.springframework.data.jpa.repository.JpaRepository

interface WorkUpdateRequestRepository : JpaRepository<WorkUpdateRequest, Long> {

    fun findByWorkId(workId: Long): List<WorkUpdateRequest>

}