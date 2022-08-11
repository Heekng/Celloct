package com.heekng.celloct.service

import com.heekng.celloct.dto.WorkDto.*
import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Shop
import com.heekng.celloct.entity.Staff
import com.heekng.celloct.entity.Work
import com.heekng.celloct.repository.MemberRepository
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.repository.WorkRepository
import com.heekng.celloct.util.findByIdOrThrow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class WorkServiceTest @Autowired constructor(
    private val workService: WorkService,
    private val staffRepository: StaffRepository,
    private val workRepository: WorkRepository,
    private val shopRepository: ShopRepository,
    private val memberRepository: MemberRepository,
    private val em: EntityManager,
) {

    @Test
    @DisplayName("근무시간 추가 테스트")
    fun addTest() {
        //given
        val shop = Shop(null, "shop", null)
        shopRepository.save(shop)
        val member = Member("member", "test@gmail.com", null, null)
        memberRepository.save(member)
        val staff = Staff(member, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val workDate = LocalDate.of(2020, 5, 5)
        val startDate = LocalDateTime.of(workDate, LocalTime.of(10, 50))
        val endDate = LocalDateTime.of(workDate, LocalTime.of(15, 50))
        val addRequest = AddRequest(workDate, startDate, endDate, null, member.id, shop.id)
        val workId = workService.addWork(addRequest)
        val findWork = workRepository.findByIdOrNull(workId)
        //then
        assertThat(findWork).isNotNull
    }

    @Test
    @DisplayName("근무시간 수정 테스트")
    fun changeWorkTimeTest() {
        //given
        val shop = Shop(null, "shop", null)
        shopRepository.save(shop)
        val member = Member("member", "test@gmail.com", null, null)
        memberRepository.save(member)
        val staff = Staff(member, shop, "staff")
        staffRepository.save(staff)
        val workDate = LocalDate.of(2020, 5, 5)
        val startDate = LocalDateTime.of(workDate, LocalTime.of(10, 50))
        val endDate = LocalDateTime.of(workDate, LocalTime.of(15, 50))
        val work = Work(workDate, staff, startDate, endDate, null)
        workRepository.save(work)
        em.flush()
        em.clear()
        //when
        val changeStartDate = startDate.minusHours(1)
        val changeEndDate = endDate.plusHours(1)
        val changeWorkTimeRequest = ChangeWorkTimeRequest(work.id, changeStartDate, changeEndDate)
        workService.changeWorkTime(changeWorkTimeRequest)
        val findWork = workRepository.findByIdOrThrow(work.id)
        //then
        assertThat(findWork.workTime.startDate).isEqualTo(changeStartDate)
        assertThat(findWork.workTime.endDate).isEqualTo(changeEndDate)
    }

    @Test
    @DisplayName("근무 삭제 테스트")
    fun deleteWorkTest() {
        //given
        val shop = Shop(null, "shop", null)
        shopRepository.save(shop)
        val member = Member("member", "test@gmail.com", null, null)
        memberRepository.save(member)
        val staff = Staff(member, shop, "staff")
        staffRepository.save(staff)
        val workDate = LocalDate.of(2020, 5, 5)
        val startDate = LocalDateTime.of(workDate, LocalTime.of(10, 50))
        val endDate = LocalDateTime.of(workDate, LocalTime.of(15, 50))
        val work = Work(workDate, staff, startDate, endDate, null)
        workRepository.save(work)
        em.flush()
        em.clear()
        //when
        val deleteRequest = DeleteRequest(staff.id, work.id)
        workService.deleteWork(deleteRequest)
        val findWorkOrNull = workRepository.findByIdOrNull(work.id)
        //then
        assertThat(findWorkOrNull).isNull()
    }

}