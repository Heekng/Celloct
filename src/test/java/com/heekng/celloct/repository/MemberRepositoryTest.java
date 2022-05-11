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
                .email("member@test.com")
                .build();

        memberRepository.save(member);
        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    @DisplayName("delete 테스트")
    void delete() throws Exception {
        Member member = Member.builder()
                .name("member")
                .email("member@gmail.com")
                .build();

        memberRepository.save(member);
        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).get();
        memberRepository.delete(findMember);
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
        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();

        assertThat(members.get(0).getId()).isEqualTo(member1.getId());
        assertThat(members.get(1).getId()).isEqualTo(member2.getId());
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void findByEmailTest() throws Exception {
        //given
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@gmail.com")
                .build();

        memberRepository.save(member1);
        em.flush();
        em.clear();
        //when
        Member findMember = memberRepository.findByEmail(member1.getEmail()).get();
        //then
        assertThat(findMember.getId()).isEqualTo(member1.getId());

    }

}