package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Staff;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByMemberIdAndShopId(Long memberId, Long shopId);

    List<Staff> findByShopId(Long shopId);

    List<Staff> findByMemberId(Long memberId);

    @EntityGraph(attributePaths = {"member"})
    Staff findWithMemberById(Long id);

    Optional<Staff> findByShopIdAndId(Long shopId, Long id);

    @EntityGraph(attributePaths = {"shop"})
    List<Staff> findWithShopByMemberId(Long memberId);
}
