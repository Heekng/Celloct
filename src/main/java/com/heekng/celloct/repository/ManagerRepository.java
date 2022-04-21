package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    List<Manager> findByMemberIdAndShopId(Long memberId, Long shopId);
}
