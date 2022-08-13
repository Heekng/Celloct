package com.heekng.celloct.repository

import com.heekng.celloct.entity.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class WorkUpdateRequestRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val memberRepository: MemberRepository,
    private val shopRepository: ShopRepository,
    private val staffRepository: StaffRepository,
    private val workRepository: WorkRepository,
    private val workUpdateRequestRepository: WorkUpdateRequestRepository,
) {
    @Test
    fun findByWorkIdTest() {
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
        val workUpdateRequest = WorkUpdateRequest.fixture(
            work.workTime.workDate,
            work.workTime.startDate.minusHours(1),
            work.workTime.endDate.plusHours(1),
            work,
            null
        )
        workUpdateRequestRepository.save(workUpdateRequest)
        em.flush()
        em.clear()
        //when
        val workUpdateRequests = workUpdateRequestRepository.findByWorkId(work.id!!)
        //then
        assertThat(workUpdateRequests).hasSize(1)
        assertThat(workUpdateRequests[0].id).isEqualTo(workUpdateRequest.id)
    }

}