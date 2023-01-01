package com.heekng.celloct.service

import com.heekng.celloct.dto.ManagerDto.AddByStaffRequest
import com.heekng.celloct.dto.ManagerDto.AddRequest
import com.heekng.celloct.dto.ManagerDto.DeleteRequest
import com.heekng.celloct.dto.ManagerDto.UpdateRequest
import com.heekng.celloct.entity.Manager
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.repository.MemberRepository
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.util.fail
import com.heekng.celloct.util.findByIdOrThrow
import com.heekng.celloct.util.whenNotEmpty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ManagerService(
    private val shopRepository: ShopRepository,
    private val managerRepository: ManagerRepository,
    private val memberRepository: MemberRepository,
    private val staffRepository: StaffRepository,
) {

    @Transactional
    fun addManager(addRequestDto: AddRequest): Manager {
        val shop = shopRepository.findByIdOrThrow(addRequestDto.shopId)
        val member = memberRepository.findByIdOrThrow(addRequestDto.memberId)
        validateExistManager(addRequestDto.shopId, addRequestDto.memberId)
        val manager = Manager(
            shop = shop,
            member = member,
            name = member.name,
        )
        return managerRepository.save(manager)
    }

    @Transactional
    fun deleteManager(deleteRequest: DeleteRequest) {
        val manager = managerRepository.findByShopIdAndId(deleteRequest.shopId, deleteRequest.managerId) ?: fail()
        if (manager.id == deleteRequest.memberId) fail()
        managerRepository.delete(manager)
    }

    @Transactional
    fun updateManager(updateRequest: UpdateRequest) {
        val manager = managerRepository.findByIdOrThrow(updateRequest.id)
        manager.updateInfo(updateRequest.name)
    }

    fun findByMemberId(memberId: Long): List<Manager> {
        return managerRepository.find(memberId = memberId)
    }

    fun addByStaff(addByStaffRequest: AddByStaffRequest): Manager {
        val staff = staffRepository.findByIdOrThrow(addByStaffRequest.staffId)
        val shop = shopRepository.findByIdOrThrow(addByStaffRequest.shopId)
        val staffMember = staff.member
        val manager = Manager(
            shop = shop,
            member = staffMember,
            name = staff.name,
        )
        managerRepository.save(manager)
        return manager
    }

    private fun validateExistManager(shopId: Long, memberId: Long) {
        managerRepository.find(shopId = shopId, memberId = memberId,).whenNotEmpty { fail() }
    }

}