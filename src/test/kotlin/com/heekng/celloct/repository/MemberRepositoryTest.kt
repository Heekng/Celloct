package com.heekng.celloct.repository

import com.heekng.celloct.entity.Member
import com.heekng.celloct.util.findByIdOrThrow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class MemberRepositoryTest(
    @Autowired
    val memberRepository: MemberRepository,
    @Autowired
    val em: EntityManager
) {

    @Test
    fun saveTest() {
        //given
        val member = Member("member", "member@test.com", null, null)
        memberRepository.save(member)
        em.flush()
        em.clear()
        //when
        val findMember = memberRepository.findByIdOrThrow(member.id)
        //then
        assertThat(findMember.id).isEqualTo(member.id)
    }

    @Test
    fun deleteTest() {
        //given
        val member = Member("member", "member@gmail.com", null, null)
        memberRepository.save(member)
        em.flush()
        em.clear()
        //when
        val findMember = memberRepository.findByIdOrThrow(member.id)
        memberRepository.delete(findMember)
        em.flush()
        em.clear()
        //then
        val memberOrNull = memberRepository.findByIdOrNull(member.id)
        assertThat(memberOrNull).isNull()
    }

    @Test
    fun readTest() {
        //given
        val member1 = Member("member1", "member1@gmail.com", null, null)
        val member2 = Member("member2", "member2@gmail.com", null, null)
        memberRepository.save(member1)
        memberRepository.save(member2)
        em.flush()
        em.clear()
        //when
        val members = memberRepository.findAll()
        //then
        assertThat(members).hasSize(2)
        assertThat(members).extracting("name").containsExactlyInAnyOrder("member1", "member2")
    }

    @Test
    fun findByEmailTest() {
        //given
        val member = Member("member1", "member1@gmail.com", null, null)
        memberRepository.save(member)
        em.flush()
        em.clear()
        //when
        val findMember = memberRepository.findByEmail(member.email).get()
        //then
        assertThat(findMember.id).isEqualTo(member.id)
    }
}