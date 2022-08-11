package com.heekng.celloct.service

import com.heekng.celloct.dto.ManagerDto
import com.heekng.celloct.dto.ManagerDto.AddByStaffRequest
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
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ManagerServiceTest(
    @Autowired
    private val memberRepository: MemberRepository,
    @Autowired
    private val shopRepository: ShopRepository,
    @Autowired
    private val managerRepository: ManagerRepository,
    @Autowired
    private val staffRepository: StaffRepository,
    @Autowired
    private val managerService: ManagerService,
    @Autowired
    private val em: EntityManager,
) {

    @Test
    fun addManagerTest() {
        //given
        val member = Member("member", "test@gmail.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        em.flush()
        em.clear()
        //when
        val addRequest = ManagerDto.AddRequest(shop.id, member.id)
        val savedManager = managerService.addManager(addRequest)
        val findMember = memberRepository.findByIdOrThrow(member.id)
        val findShop = shopRepository.findByIdOrThrow(shop.id)
        //then
        assertThat(findMember.managers).isNotEmpty
        assertThat(findShop.managers).isNotEmpty
        assertThat(savedManager.id).isNotNull()
    }

    @Test
    fun deleteManager() {
        //given
        val member = Member("member", "test@gmail.com", null, null)
        memberRepository.save(member)
        val shop = Shop(null, "shop", null)
        shopRepository.save(shop)
        val manager = Manager(shop, member, "manager")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
        val deleteRequest = ManagerDto.DeleteRequest(manager.id, shop.id, null)
        managerService.deleteManager(deleteRequest)
        em.flush()
        em.clear()
        val findShop = shopRepository.findByIdOrThrow(shop.id)
        //then
        assertThat(findShop.managers).isEmpty()
    }

    @Test
    fun updateManagerTest() {
        //given
        val member = Member("member", "test@gmail.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        val manager = Manager(shop, member, "manager")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
        val updateRequest = ManagerDto.UpdateRequest(manager.id, "changeManagerName")
        managerService.updateManager(updateRequest)
        val findManager = managerRepository.findByIdOrThrow(manager.id)
        //then
        assertThat(findManager.id).isEqualTo(manager.id)
        assertThat(findManager.name).isEqualTo(updateRequest.name)
    }

    @Test
    fun addByStaffTest() {
        //given
        val member = Member("member", "test@gmail.com", null, null)
        memberRepository.save(member)
        val shop = Shop("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        val staff = Staff(member, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val addByStaffRequest = AddByStaffRequest(shop.id, staff.id)
        val savedManager = managerService.addByStaff(addByStaffRequest)
        //then
        assertThat(savedManager.name).isEqualTo(staff.name)
    }

}