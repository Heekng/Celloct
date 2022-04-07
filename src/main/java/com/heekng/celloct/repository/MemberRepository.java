package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByLoginId(String loginId);

    List<Member> findByLoginIdAndPassword(String loginId, String password);
}
