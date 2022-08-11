package com.heekng.celloct.repository

import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Shop
import com.heekng.celloct.entity.Staff
import com.heekng.celloct.entity.Work
import com.heekng.celloct.entity.WorkUpdateRequest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
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
        val shopMember = Member("shopMember", "shopMember@gmail.com", null, null)
        memberRepository.save(shopMember)
        val staffMember = Member("staffMember", "staffMember@gmail.com", null, null)
        memberRepository.save(staffMember)
        val shop = Shop(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff(staffMember, shop, "staff")
        staffRepository.save(staff)
        val work = Work(LocalDate.now(), staff, LocalDateTime.now(), LocalDateTime.now().plusHours(1), null)
        workRepository.save(work)
        val workUpdateRequest = WorkUpdateRequest(
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
        val workUpdateRequests = workUpdateRequestRepository.findByWorkId(work.id)
        //then
        assertThat(workUpdateRequests).hasSize(1)
        assertThat(workUpdateRequests[0].id).isEqualTo(workUpdateRequest.id)
    }

}