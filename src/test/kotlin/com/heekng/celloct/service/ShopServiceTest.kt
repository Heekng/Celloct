package com.heekng.celloct.service

import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Shop
import com.heekng.celloct.entity.Staff
import com.heekng.celloct.repository.MemberRepository
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.repository.StaffRepository
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
class ShopServiceTest @Autowired constructor(
    private val em: EntityManager,
    private val shopService: ShopService,
    private val shopRepository: ShopRepository,
    private val memberRepository: MemberRepository,
    private val staffRepository: StaffRepository,
) {

    @Test
    fun makeShopTest() {
        //given
        val member = Member.fixture("member", "member@gmail.com")
        memberRepository.save(member)
        em.flush()
        em.clear()
        //when
        val createRequest = ShopDto.CreateRequest("shop", "010-1234-1234", null, "manager")
        val shopId = shopService.makeShop(createRequest, member.id)
        val shop = shopRepository.findByIdOrThrow(shopId)
        //then
        assertThat(createRequest.name).isEqualTo(shop.name)
        assertThat(createRequest.phone).isEqualTo(shop.phone)
    }

    @Test
    fun findListByNameContainingTest() {
        //given
        val member1 = Member.fixture("member1", "member1@gmail.com")
        memberRepository.save(member1)
        val member2 = Member.fixture("member2", "member2@gmail.com")
        memberRepository.save(member2)
        val member3 = Member.fixture("member3", "member3@gmail.com")
        memberRepository.save(member3)
        val shop = Shop.fixture(null, "shop", null)
        shopRepository.save(shop)
        val staff1 = Staff.fixture(member1, shop, "staff1")
        staffRepository.save(staff1)
        val staff2 = Staff.fixture(member2, shop, "staff2")
        staffRepository.save(staff2)
        val staff3 = Staff.fixture(member3, shop, "staff3")
        staffRepository.save(staff3)
        em.flush()
        em.clear()
        //when
        val listResponse = shopService.findListResponseListByNameContaining("op")
        //then
        assertThat(listResponse).hasSize(1)
        assertThat(listResponse[0].staffCount).isEqualTo(3)
    }

    @Test
    fun existNameTest() {
        //given
        val member = Member.fixture("member", "member@gmail.com")
        memberRepository.save(member)
        val shop = Shop.fixture("010-1234-1234", "shop", "information")
        shopRepository.save(shop)
        em.flush()
        em.clear()
        //when
        val result = shopService.existName("shop")
        //then
        assertThat(result).isTrue()
    }

    @Test
    fun updateShopTest() {
        //given
        val shop = Shop.fixture("010-1234-1234", "shop", "information")
        shopRepository.save(shop)
        em.flush()
        em.clear()
        //when
        val updateRequest = ShopDto.UpdateRequest(shop.id, "010-4321-4312", "updateInfo")
        shopService.updateShop(updateRequest)
        em.flush()
        em.clear()
        val findShop = shopRepository.findByIdOrThrow(shop.id)
        //then
        assertThat(findShop.id).isEqualTo(shop.id)
        assertThat(findShop.phone).isEqualTo(updateRequest.phone)
        assertThat(findShop.info).isEqualTo(updateRequest.info)
    }

    @Test
    fun deleteTest() {
        //given
        val shop = Shop.fixture("010-1234-1234", "shop", "information")
        shopRepository.save(shop)
        em.flush()
        em.clear()
        //when
        shopService.delete(shop.id)
        val shopOrNull = shopRepository.findByIdOrNull(shop.id)
        //then
        assertThat(shopOrNull).isNull()
    }
}