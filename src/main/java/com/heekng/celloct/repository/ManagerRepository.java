package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Manager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByMemberIdAndShopId(Long memberId, Long shopId);

    List<Manager> findByMemberId(Long memberId);

    List<Manager> findListByShopId(Long shopId);

    @EntityGraph(attributePaths = {"member"})
    Manager findWithMemberById(Long id);
}
