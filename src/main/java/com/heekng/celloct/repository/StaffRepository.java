package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Staff;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findByMemberIdAndShopId(Long memberId, Long shopId);

    List<Staff> findByShopId(Long shopId);

    List<Staff> findByMemberId(Long memberId);

    @EntityGraph(attributePaths = {"member"})
    Staff findWithMemberById(Long id);
}
