package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findByMemberIdAndShopId(Long memberId, Long shopId);
}
