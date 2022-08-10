package com.heekng.celloct.repository

import com.heekng.celloct.entity.JoinRequest
import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Shop
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
class JoinRequestRepositoryTest(
    @Autowired
    val em: EntityManager,
    @Autowired
    val shopRepository: ShopRepository,
    @Autowired
    val memberRepository: MemberRepository,
    @Autowired
    val joinRequestRepository: JoinRequestRepository,
) {

    @Test
    fun createTest() {
        //given
        val member = Member("member1", "member1@test.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop1", null)
        shopRepository.save(shop)
        val joinRequest = JoinRequest(member, shop)
        joinRequestRepository.save(joinRequest)
        em.flush()
        em.clear()
        //when
        val findJoinRequest = joinRequestRepository.findByIdOrThrow(joinRequest.id)
        //then
        assertThat(findJoinRequest.id).isEqualTo(joinRequest.id)
        assertThat(findJoinRequest.member.id).isEqualTo(member.id)
        assertThat(findJoinRequest.shop.id).isEqualTo(shop.id)
    }

    @Test
    fun findByMemberIdTest() {
        //given
        val member = Member("member", "member1@Test.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        val joinRequest = JoinRequest(member, shop)
        joinRequestRepository.save(joinRequest)
        em.flush()
        em.clear()
        //when
        val findJoinRequests = joinRequestRepository.findByMemberId(member.id)
        //then
        assertThat(findJoinRequests).extracting("member").extracting("id").containsExactlyInAnyOrder(member.id)
    }

    @Test
    fun findByShopIdTest() {
        //given
        val member1 = Member("member1", "member1@Test.com", null, null)
        memberRepository.save(member1)
        val shop = Shop("010-1234-1234", "shop1", null)
        shopRepository.save(shop)
        val joinRequest = JoinRequest(member1, shop)
        joinRequestRepository.save(joinRequest)
        em.flush()
        em.clear()
        //when
        val findJoinRequests = joinRequestRepository.findByShopId(shop.id)
        //then
        assertThat(findJoinRequests).extracting("shop").extracting("id").containsExactlyInAnyOrder(shop.id)
    }

    @Test
    fun deleteJoinRequestTest() {
        //given
        val member = Member("member1", "member1@Test.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop1", null)
        shopRepository.save(shop)
        val joinRequest = JoinRequest(member, shop)
        joinRequestRepository.save(joinRequest)
        em.flush()
        em.clear()
        //when
        val findJoinRequest = joinRequestRepository.findByIdOrThrow(joinRequest.id)
        joinRequestRepository.delete(findJoinRequest)
        val joinRequestOrNull = joinRequestRepository.findByIdOrNull(joinRequest.id)
        //then
        assertThat(joinRequestOrNull).isNull()
    }

    @Test
    fun findByMemberIdAndShopIdTest() {
        //given
        val member = Member("member1", "member1@test.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop1", null)
        shopRepository.save(shop)
        val joinRequest = JoinRequest(member, shop)
        joinRequestRepository.save(joinRequest)
        em.flush()
        em.clear()
        //when
        val findJoinRequests = joinRequestRepository.findByMemberIdAndShopId(member.id, shop.id)
        //then
        assertThat(findJoinRequests).hasSize(1)
        assertThat(findJoinRequests[0].member.id).isEqualTo(member.id)
        assertThat(findJoinRequests[0].shop.id).isEqualTo(shop.id)
    }

    @Test
    fun findWithMemberByShopIdTest() {
        //given
        val member = Member("member1", "member1@test.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop1", null)
        shopRepository.save(shop)
        val joinRequest = JoinRequest(member, shop)
        joinRequestRepository.save(joinRequest)
        em.flush()
        em.clear()
        //when
        val findJoinRequests = joinRequestRepository.findWithMemberByShopId(shop.id)
        em.flush()
        em.clear()
        //then
        assertThat(findJoinRequests).hasSize(1)
        assertThat(findJoinRequests[0].member.id).isEqualTo(member.id)
        assertThat(findJoinRequests[0].shop.id).isEqualTo(shop.id)
    }

}