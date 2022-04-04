package com.heekng.celloct.repository;

import com.heekng.celloct.entity.JoinRequest;
import com.heekng.celloct.entity.JoinRequestId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, JoinRequestId> {
}
