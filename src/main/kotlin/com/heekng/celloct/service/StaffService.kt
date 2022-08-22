package com.heekng.celloct.service

import com.heekng.celloct.dto.StaffDto
import com.heekng.celloct.entity.Staff
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.repository.MemberRepository
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.util.fail
import com.heekng.celloct.util.findByIdOrThrow
import com.heekng.celloct.util.whenEmpty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StaffService(
    private val staffRepository: StaffRepository,
    private val shopRepository: ShopRepository,
    private val memberRepository: MemberRepository,
    private val managerRepository: ManagerRepository,
) {

    fun findByMemberId(memberId: Long): List<Staff> {
        return staffRepository.findByMemberId(memberId)
    }

    @Transactional
    fun addStaff(addRequestDto: StaffDto.AddRequest): Long {
        val shop = shopRepository.findByIdOrThrow(addRequestDto.shopId)
        val member = memberRepository.findByIdOrThrow(addRequestDto.memberId)
        validateExistStaff(addRequestDto.shopId, addRequestDto.memberId)
        val staff = Staff(
            member = member,
            shop = shop,
            name = member.name,
        )
        staffRepository.save(staff)
        return staff.id!!
    }

    @Transactional
    fun updateEmploymentDate(updateEmploymentDateRequestDto: StaffDto.UpdateEmploymentDateRequest) {
        val staff = staffRepository.findByIdOrThrow(updateEmploymentDateRequestDto.staffId)
        staff.changeEmploymentDate(updateEmploymentDateRequestDto.changeEmploymentDate)
    }

    @Transactional
    fun deleteStaff(deleteRequest: StaffDto.DeleteRequest) {
        managerRepository.find(
            memberId = deleteRequest.memberId,
            shopId = deleteRequest.shopId,
        ).whenEmpty { fail() }
        val staff = staffRepository.findByIdOrThrow(deleteRequest.staffId)
        staffRepository.delete(staff)
    }

    @Transactional
    fun updateStaff(updateRequest: StaffDto.UpdateRequest) {
        val findStaff = staffRepository.findByShopIdAndId(updateRequest.shopId, updateRequest.staffId) ?: fail()
        findStaff.updateInfo(updateRequest.name)
    }

    private fun validateExistStaff(shopId: Long, staffMemberId: Long) {
        val staff = staffRepository.findByMemberIdAndShopId(staffMemberId, shopId)
        if (staff != null) fail()
    }

}