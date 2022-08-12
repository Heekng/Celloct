package com.heekng.celloct.repository

import com.heekng.celloct.entity.*
import com.heekng.celloct.util.findByIdOrThrow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class WorkRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val memberRepository: MemberRepository,
    private val shopRepository: ShopRepository,
    private val staffRepository: StaffRepository,
    private val workRepository: WorkRepository,
) {

    @Test
    fun createTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        val work = Work(
            workTime = WorkTime(
                workDate = LocalDate.now(),
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1),
            ) ,
            staff = staff,
            workUpdateRequest = null,
        )
        workRepository.save(work)
        em.flush()
        em.clear()
        //when
        val findWork = workRepository.findByIdOrThrow(work.id)
        //then
        assertThat(findWork.id).isEqualTo(work.id)
    }

    @Test
    fun updateWorkTimeTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        val work = Work(
            workTime = WorkTime(
                workDate = LocalDate.now(),
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1),
            ) ,
            staff = staff,
            workUpdateRequest = null,
        )
        workRepository.save(work)
        em.flush()
        em.clear()
        //when
        val findWork = workRepository.findByIdOrThrow(work.id)
        val now = LocalDateTime.now()
        val startDate = now.minusHours(1L)
        val endDate = now;
        findWork.workTime.changeWorkTime(startDate, endDate)
        em.flush()
        em.clear()

        val findWork2 = workRepository.findByIdOrThrow(work.id)
        //then
        assertThat(findWork2.workTime.startDate).isEqualTo(startDate)
        assertThat(findWork2.workTime.endDate).isEqualTo(endDate)
    }

    @Test
    fun deleteTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        val work = Work(
            workTime = WorkTime(
                workDate = LocalDate.now(),
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1),
            ) ,
            staff = staff,
            workUpdateRequest = null,
        )
        workRepository.save(work)
        em.flush()
        em.clear()
        //when
        val findWork = workRepository.findByIdOrThrow(work.id)
        workRepository.delete(findWork)
        em.flush()
        em.clear()
        val workOrNull = workRepository.findByIdOrNull(work.id)
        //then
        assertThat(workOrNull).isNull()
    }

    @Test
    fun findByWorkTimeWorkDateAndStaffIdTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        val work = Work(
            workTime = WorkTime(
                workDate = LocalDate.now(),
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1),
            ) ,
            staff = staff,
            workUpdateRequest = null,
        )
        workRepository.save(work)
        em.flush()
        em.clear()
        //when
        val works = workRepository.findByWorkTimeWorkDateAndStaffId(work.workTime.workDate, staff.id!!)
        //then
        assertThat(works).hasSize(1)
        assertThat(works[0].id).isEqualTo(work.id)
    }

    @Test
    fun findByWorkTimeWorkDateBetweenAndStaffIdTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        val work = Work(
            workTime = WorkTime(
                workDate = LocalDate.now(),
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1),
            ) ,
            staff = staff,
            workUpdateRequest = null,
        )
        workRepository.save(work)
        em.flush()
        em.clear()
        //when
        val works = workRepository.findByWorkTimeWorkDateBetweenAndStaffId(
            work.workTime.workDate,
            work.workTime.workDate,
            staff.id!!
        )
        //then
        assertThat(works).hasSize(1)
        assertThat(works[0].id).isEqualTo(work.id)
    }

    @Test
    fun findByIdAndStaffIdTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        val work = Work(
            workTime = WorkTime(
                workDate = LocalDate.now(),
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1),
            ) ,
            staff = staff,
            workUpdateRequest = null,
        )
        workRepository.save(work)
        em.flush()
        em.clear()
        //when
        val findWork = workRepository.findByIdAndStaffId(work.id!!, staff.id!!)
        //then
        assertThat(findWork!!.id).isEqualTo(work.id)
    }
}