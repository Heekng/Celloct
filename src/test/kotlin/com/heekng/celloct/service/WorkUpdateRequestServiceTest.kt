package com.heekng.celloct.service

import com.heekng.celloct.dto.WorkUpdateRequestDto.AddRequest
import com.heekng.celloct.dto.WorkUpdateRequestDto.UpdateRequest
import com.heekng.celloct.entity.*
import com.heekng.celloct.repository.*
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
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class WorkUpdateRequestServiceTest @Autowired constructor(
    private val workUpdateRequestService: WorkUpdateRequestService,
    private val workUpdateRequestRepository: WorkUpdateRequestRepository,
    private val shopRepository: ShopRepository,
    private val memberRepository: MemberRepository,
    private val staffRepository: StaffRepository,
    private val workRepository: WorkRepository,
    private val em: EntityManager,
) {

    @Test
    @DisplayName("근무시간 수정 요청 추가 테스트")
    fun addTest() {
        //given
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val member = Member.fixture("member", "test@gmail.com")
        memberRepository.save(member)
        val staff = Staff.fixture(member, shop, "staff")
        staffRepository.save(staff)
        val workDate = LocalDate.of(2022, 10, 10)
        val startDate = LocalDateTime.of(2022, 10, 10, 5, 10)
        val endDate = LocalDateTime.of(2022, 10, 10, 6, 10)
        val work = Work(
            workTime = WorkTime(
                workDate = workDate,
                startDate = startDate,
                endDate = endDate,
            ) ,
            staff = staff,
        )
        workRepository.save(work)
        //when
        val addRequest = AddRequest(work.id, staff.id, workDate, startDate.minusHours(1), endDate.plusHours(1), null)
        val workUpdateRequestId = workUpdateRequestService.addWorkUpdateRequest(addRequest)
        val findWorkUpdateRequest = workUpdateRequestRepository.findByIdOrThrow(workUpdateRequestId)
        //then
        assertThat(findWorkUpdateRequest.work).isEqualTo(work)
    }

    @Test
    @DisplayName("근무시간 요청 수정")
    fun updateTest() {
        //given
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val member = Member.fixture("member", "test@gmail.com")
        memberRepository.save(member)
        val staff = Staff.fixture(member, shop, "staff")
        staffRepository.save(staff)
        val workDate = LocalDate.of(2022, 10, 10)
        val startDate = LocalDateTime.of(2022, 10, 10, 5, 10)
        val endDate = LocalDateTime.of(2022, 10, 10, 6, 10)
        val work = Work(
            workTime = WorkTime(
                workDate = workDate,
                startDate = startDate,
                endDate = endDate,
            ) ,
            staff = staff,
        )
        workRepository.save(work)
        val workUpdateRequest = WorkUpdateRequest.fixture(workDate, startDate.minusHours(1), endDate.plusHours(1), work, null)
        workUpdateRequestRepository.save(workUpdateRequest)
        em.flush()
        em.clear()
        //when
        val updateStartDate = workUpdateRequest.workTime.startDate.minusHours(1)
        val updateRequest = UpdateRequest(
            workUpdateRequest.id,
            workUpdateRequest.workTime.workDate,
            updateStartDate,
            workUpdateRequest.workTime.endDate
        )
        workUpdateRequestService.updateWorkUpdateRequest(updateRequest)
        val findWorkUpdateRequest = workUpdateRequestRepository.findByIdOrThrow(workUpdateRequest.id)
        //then
        assertThat(findWorkUpdateRequest.workTime.startDate).isEqualTo(updateStartDate)
    }

    @Test
    @DisplayName("근무시간 수정 요청 삭제 테스트")
    fun deleteTest() {
        //given
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val member = Member.fixture("member", "test@gmail.com")
        memberRepository.save(member)
        val staff = Staff.fixture(member, shop, "staff")
        staffRepository.save(staff)
        val workDate = LocalDate.of(2022, 10, 10)
        val startDate = LocalDateTime.of(2022, 10, 10, 5, 10)
        val endDate = LocalDateTime.of(2022, 10, 10, 6, 10)
        val work = Work(
            workTime = WorkTime(
                workDate = workDate,
                startDate = startDate,
                endDate = endDate,
            ) ,
            staff = staff,
        )
        workRepository.save(work)
        val workUpdateRequest = WorkUpdateRequest.fixture(workDate, startDate.minusHours(1), endDate.plusHours(1), work, null)
        workUpdateRequestRepository.save(workUpdateRequest)
        em.flush()
        em.clear()
        //when
        workUpdateRequestService.deleteWorkUpdateRequest(workUpdateRequest.id)
        val findWorkUpdateRequestOrNull = workUpdateRequestRepository.findByIdOrNull(workUpdateRequest.id)
        //then
        assertThat(findWorkUpdateRequestOrNull).isNull()
    }
}