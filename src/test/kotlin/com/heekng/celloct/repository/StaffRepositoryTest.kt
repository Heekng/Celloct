package com.heekng.celloct.repository

import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Shop
import com.heekng.celloct.entity.Staff
import com.heekng.celloct.util.findByIdOrThrow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class StaffRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val staffRepository: StaffRepository,
    private val memberRepository: MemberRepository,
    private val shopRepository: ShopRepository,
) {
    @Test
    fun saveTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val findStaff = staffRepository.findByIdOrThrow(staff.id)
        //then
        assertThat(findStaff.id).isEqualTo(staff.id)
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
        em.flush()
        em.clear()
        //when
        val findStaff = staffRepository.findByIdOrThrow(staff.id)
        staffRepository.delete(findStaff)
        em.flush()
        em.clear()
        val findStaffOrNull = staffRepository.findByIdOrNull(staff.id)
        //then
        assertThat(findStaffOrNull).isNull()
    }

    @Test
    fun findByMemberIdAndShopIdTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val findStaff = staffRepository.findOneQ(
            memberId = staffMember.id,
            shopId = shop.id
        )
        //then
        assertThat(findStaff!!.id).isEqualTo(staff.id)
    }

    @Test
    fun findByShopIdTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val staffs = staffRepository.find(shopId = shop.id)
        //then
        assertThat(staffs).hasSize(1)
        assertThat(staffs[0].id).isEqualTo(staff.id)
    }

    @Test
    fun findByMemberIdTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val staffs = staffRepository.find(memberId = staffMember.id)
        //then
        assertThat(staffs).hasSize(1)
        assertThat(staffs[0].id).isEqualTo(staff.id)
    }

    @Test
    fun findWithMemberByIdTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val findStaff = staffRepository.findWithMemberById(staff.id!!)
        em.flush()
        em.clear()
        //then
        assertThat(findStaff.member.id).isEqualTo(staffMember.id)
    }
    
    @Test
    fun findByShopIdAndIdTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val findStaff = staffRepository.findByShopIdAndId(shop.id!!, staff.id!!)
        //then
        assertThat(findStaff!!.id).isEqualTo(staff.id)
    }

    @Test
    fun findWithShopByMemberIdTest() {
        //given
        val shopMember = Member.fixture("shopMember", "shopMember@gmail.com")
        memberRepository.save(shopMember)
        val staffMember = Member.fixture("staffMember", "staffMember@gmail.com")
        memberRepository.save(staffMember)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff = Staff.fixture(staffMember, shop, "staff")
        staffRepository.save(staff)
        em.flush()
        em.clear()
        //when
        val staffs = staffRepository.findWithShopByMemberId(staffMember.id!!)
        em.flush()
        em.clear()
        //then
        assertThat(staffs).hasSize(1)
        assertThat(staffs[0].id).isEqualTo(staff.id)
        assertThat(staffs[0].shop.id).isEqualTo(shop.id)
    }
}