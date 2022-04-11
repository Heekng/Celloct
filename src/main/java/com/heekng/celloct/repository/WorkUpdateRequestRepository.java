package com.heekng.celloct.repository;

import com.heekng.celloct.entity.WorkUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkUpdateRequestRepository extends JpaRepository<WorkUpdateRequest, Long> {
    List<WorkUpdateRequest> findByWorkId(Long workId);
}
