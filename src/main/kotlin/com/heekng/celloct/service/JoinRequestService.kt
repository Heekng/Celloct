package com.heekng.celloct.service

import com.heekng.celloct.dto.JoinRequestDto
import com.heekng.celloct.entity.JoinRequest
import com.heekng.celloct.entity.Staff
import com.heekng.celloct.repository.JoinRequestRepository
import com.heekng.celloct.repository.MemberRepository
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class JoinRequestService(
    private val joinRequestRepository: JoinRequestRepository,
    private val memberRepository: MemberRepository,
    private val shopRepository: ShopRepository,
    private val staffRepository: StaffRepository,
) {

    @Transactional
    fun joinRequest(joinRequestDto: JoinRequestDto.JoinRequest): Long {
        val findMember = memberRepository.findByIdOrThrow(joinRequestDto.memberId)
        val findShop = shopRepository.findByIdOrThrow(joinRequestDto.shopId)
        validateDuplicateJoinRequest(joinRequestDto.memberId, joinRequestDto.shopId)
        validateDuplicateStaff(joinRequestDto.memberId, joinRequestDto.shopId)
        val joinRequest = JoinRequest(
            member = findMember,
            shop = findShop,
        )
        val savedJoinRequest = joinRequestRepository.save(joinRequest)
        return savedJoinRequest.id!!
    }

    @Transactional
    fun cancel(joinRequestId: Long) {
        val joinRequest = joinRequestRepository.findByIdOrThrow(joinRequestId)
        joinRequestRepository.delete(joinRequest)
    }

    @Transactional
    fun approval(approvalDto: JoinRequestDto.ApprovalRefusalRequest): Long {
        val findJoinRequest = joinRequestRepository.findByIdOrThrow(approvalDto.joinRequestId)
        val member = findJoinRequest.member
        val shop = findJoinRequest.shop
        validateDuplicateStaff(member.id!!, shop.id!!)
        val staff = Staff(
            member = member,
            shop = shop,
            name = member.name,
        )
        staffRepository.save(staff)
        joinRequestRepository.delete(findJoinRequest)
        return staff.id!!
    }

    @Transactional
    fun refusal(refusalDto: JoinRequestDto.ApprovalRefusalRequest) {
        val findJoinRequest = joinRequestRepository.findByIdOrThrow(refusalDto.joinRequestId)
        joinRequestRepository.delete(findJoinRequest)
    }

    private fun validateDuplicateJoinRequest(memberId: Long, shopId: Long) {
        val findJoinRequests = joinRequestRepository.findByMemberIdAndShopId(memberId, shopId)
        if (findJoinRequests.isNotEmpty()) {
            throw IllegalStateException("이미 가입신청한 매장입니다.")
        }
    }

    private fun validateDuplicateStaff(memberId: Long, shopId: Long) {
        val staff = staffRepository.findByMemberIdAndShopId(memberId, shopId)
        if (staff != null) {
            throw IllegalStateException("이미 가입된 회원입니다.")
        }
    }
}