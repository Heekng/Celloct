package com.heekng.celloct.repository

import com.heekng.celloct.entity.WorkUpdateRequest
import org.springframework.data.jpa.repository.JpaRepository

interface WorkUpdateRequestRepository : JpaRepository<WorkUpdateRequest, Long>, WorkUpdateRequestRepositoryCustom {

}