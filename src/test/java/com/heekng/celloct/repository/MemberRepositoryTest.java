package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("save 테스트")
    void save() throws Exception {
        Member member = Member.builder()
                .name("member")
                .build();

        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(member.getName()).isEqualTo(findMember.getName());
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    @DisplayName("delete 테스트")
    void delete() throws Exception {
        Member member = Member.builder()
                .name("member")
                .email("member@gmail.com")
                .build();

        memberRepository.save(member);

        memberRepository.delete(member);
        em.flush();
        em.clear();

        Optional<Member> memberOptional = memberRepository.findById(member.getId());

        assertThat(memberOptional).isEmpty();
    }

    @Test
    @DisplayName("read 테스트")
    void read() throws Exception {
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@gmail.com")
                .build();

        Member member2 = Member.builder()
                .name("member2")
                .email("member2@gmail.com")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAll();

        assertThat(members.get(0)).isEqualTo(member1);
        assertThat(members.get(1)).isEqualTo(member2);
        assertThat(members.size()).isEqualTo(2);
    }




}