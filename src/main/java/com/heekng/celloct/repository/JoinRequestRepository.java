package com.heekng.celloct.repository;

import com.heekng.celloct.entity.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {

    List<JoinRequest> findByShopId(Long shopId);

    List<JoinRequest> findByMemberId(Long memberId);

}
