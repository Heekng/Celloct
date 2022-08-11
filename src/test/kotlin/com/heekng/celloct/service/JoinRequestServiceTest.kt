package com.heekng.celloct.service

import com.heekng.celloct.dto.JoinRequestDto.ApprovalRefusalRequest
import com.heekng.celloct.dto.JoinRequestDto.JoinRequest
import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Shop
import com.heekng.celloct.repository.JoinRequestRepository
import com.heekng.celloct.repository.MemberRepository
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.util.findByIdOrThrow
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class JoinRequestServiceTest @Autowired constructor(
    private val em: EntityManager,
    private val memberRepository: MemberRepository,
    private val shopRepository: ShopRepository,
    private val joinRequestRepository: JoinRequestRepository,
    private val staffRepository: StaffRepository,
    private val joinRequestService: JoinRequestService,
) {

    @Test
    fun joinRequestTest() {
        //given
        val member = Member("member", "member@gmail.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop", "shopInfo")
        shopRepository.save(shop)
        em.flush()
        em.clear()
        //when
        val joinRequest = JoinRequest(member.id, shop.id)
        val joinRequestId = joinRequestService.joinRequest(joinRequest)
        val findJoinRequest = joinRequestRepository.findByIdOrThrow(joinRequestId)
        //then
        assertThat(findJoinRequest.shop.id).isEqualTo(shop.id)
        assertThat(findJoinRequest.member.id).isEqualTo(member.id)
    }

    @Test
    fun cancelTest() {
        //given
        val member = Member("member", "member@gmail.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop", "shopInfo")
        shopRepository.save(shop)
        val joinRequest = com.heekng.celloct.entity.JoinRequest(member, shop)
        joinRequestRepository.save(joinRequest)
        em.flush()
        em.clear()
        //when
        joinRequestService.cancel(joinRequest.id)
        val findJoinRequestOrNull = joinRequestRepository.findByIdOrNull(joinRequest.id)
        //then
        assertThat(findJoinRequestOrNull).isNull()
    }

    @Test
    fun approvalTest() {
        //given
        val member = Member("member", "member@gmail.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop", "shopInfo")
        shopRepository.save(shop)
        val joinRequest = com.heekng.celloct.entity.JoinRequest(member, shop)
        joinRequestRepository.save(joinRequest)
        em.flush()
        em.clear()
        //when
        val approvalRefusalRequest = ApprovalRefusalRequest(joinRequest.id)
        val staffId = joinRequestService.approval(approvalRefusalRequest)

        val findJoinRequestOrNull = joinRequestRepository.findByIdOrNull(joinRequest.id)
        val findStaffOrNull = staffRepository.findByIdOrNull(staffId)
        //then
        assertThat(findJoinRequestOrNull).isNull()
        assertThat(findStaffOrNull).isNotNull()
    }

    @Test
    fun refusalTest() {
        //given
        val member = Member("member", "member@gmail.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop", "shopInfo")
        shopRepository.save(shop)
        val joinRequest = com.heekng.celloct.entity.JoinRequest(member, shop)
        joinRequestRepository.save(joinRequest)
        em.flush()
        em.clear()
        //when
        val approvalDto = ApprovalRefusalRequest(joinRequest.id)
        joinRequestService.refusal(approvalDto)
        val findJoinRequestOrNull = joinRequestRepository.findByIdOrNull(joinRequest.id)
        //then
        assertThat(findJoinRequestOrNull).isNull()
    }
}