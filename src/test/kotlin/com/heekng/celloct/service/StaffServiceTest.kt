package com.heekng.celloct.service

import com.heekng.celloct.dto.StaffDto
import com.heekng.celloct.entity.Manager
import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Shop
import com.heekng.celloct.entity.Staff
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.repository.MemberRepository
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.util.findByIdOrThrow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class StaffServiceTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val shopRepository: ShopRepository,
    private val staffRepository: StaffRepository,
    private val managerRepository: ManagerRepository,
    private val staffService: StaffService,
    private val em: EntityManager,
) {

    @Test
    @DisplayName("영속성 컨텍스트가 비워지면 연관관계의 주인이 아닌 객체의 리스트에도 비워진다.")
    fun test() {
        //given
        val member = Member.fixture("member", "test@gmail.com")
        memberRepository.save(member)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(member, shop, "staff")
        em.flush()
        em.clear()
        //when
        val findMember = memberRepository.findByIdOrThrow(member.id)
        val staffList = member.staffList
        //then
        for (staff in staffList) {
            println("staff.employmentDate = ${staff.employmentDate}")
        }
    }

    @Test
    @DisplayName("직원 추가 테스트")
    fun addTest() {
        //given
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val member = Member.fixture("member", "test@gmail.com")
        memberRepository.save(member)
        em.flush()
        em.clear()
        //when
        val addRequest = StaffDto.AddRequest(shop.id!!, member.id!!)
        val savedStaffId = staffService.addStaff(addRequest)
        val findStaff = staffRepository.findByIdOrThrow(savedStaffId)
        //then
        assertThat(findStaff.shop.id).isEqualTo(shop.id)
        assertThat(findStaff.member.id).isEqualTo(member.id)
    }

    @Test
    @DisplayName("가입날짜 변경 테스트")
    fun updateEmploymentDateTest() {
        //given
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val member = Member.fixture("member", "test@gmail.com")
        memberRepository.save(member)
        val staff = Staff.fixture(member, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val now = LocalDate.now()
        val changeDate = now.minusDays(6)
        val updateEmploymentDateRequest = StaffDto.UpdateEmploymentDateRequest(staff.id!!, changeDate)
        staffService.updateEmploymentDate(updateEmploymentDateRequest)
        val findStaff = staffRepository.findByIdOrThrow(staff.id)
        //then
        assertThat(findStaff.employmentDate).isEqualTo(changeDate)
    }

    @Test
    @DisplayName("삭제 테스트")
    fun deleteTest() {
        //given
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val managerMember = Member.fixture("managerMember", "manager1@gmail.com")
        memberRepository.save(managerMember)
        val manager = Manager.fixture(shop, managerMember, "managerMember")
        managerRepository.save(manager)
        val staffMember = Member.fixture("staffMember", "staff@gmail.com")
        memberRepository.save(staffMember)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val deleteRequest = StaffDto.DeleteRequest(staff.id!!, shop.id!!, managerMember.id!!)
        staffService.deleteStaff(deleteRequest)
        val staffOrNull = staffRepository.findByIdOrNull(staff.id)
        //then
        assertThat(staffOrNull).isNull()
    }

    @Test
    fun updateStaffTest() {
        //given
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val member = Member.fixture("member", "member@gmail.com")
        memberRepository.save(member)
        val staff = Staff.fixture(member, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val updateRequest = StaffDto.UpdateRequest(staff.id!!, "updateStaffName", shop.id!!)
        staffService.updateStaff(updateRequest)
        val findStaff = staffRepository.findByIdOrThrow(staff.id)
        //then
        assertThat(findStaff.name).isEqualTo(updateRequest.name)
    }
}