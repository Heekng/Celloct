package com.heekng.celloct.repository

import com.heekng.celloct.entity.Manager
import com.heekng.celloct.entity.Member
import com.heekng.celloct.entity.Shop
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
class ManagerRepositoryTest @Autowired constructor(
    private val managerRepository: ManagerRepository,
    private val memberRepository: MemberRepository,
    private val shopRepository: ShopRepository,
    private val em: EntityManager,
) {

    @Test
    fun createTest() {
        //given
        val member = Member.fixture("member1", "member1@heekng.com")
        memberRepository.save(member)
        val shop = Shop.fixture("010-1234-1234", "shop1", null)
        shopRepository.save(shop)
        val manager = Manager.fixture(shop, member, "managerName")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
        val findManager = managerRepository.findByIdOrThrow(manager.id)
        //then
        assertThat(findManager.id).isEqualTo(manager.id)
        assertThat(findManager.member.id).isEqualTo(member.id)
        assertThat(findManager.shop.id).isEqualTo(shop.id)
    }

    @Test
    fun deleteTest() {
        //given
        val member = Member.fixture("member1", "member1@heekng.com")
        memberRepository.save(member)
        val shop = Shop.fixture("010-1234-1234", "shop1", null)
        shopRepository.save(shop)
        val manager = Manager.fixture(shop, member, "managerName")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
        val findManager = managerRepository.findByIdOrThrow(manager.id)
        managerRepository.delete(findManager)
        em.flush()
        em.clear()
        val findManagerOrNull = managerRepository.findByIdOrNull(manager.id)
        //then
        assertThat(findManagerOrNull).isNull()
    }

    @Test
    fun findByMemberIdAndShopIdTest() {
        //given
        val member = Member.fixture("member", "member@heekng.com")
        memberRepository.save(member)
        val shop = Shop.fixture("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        val manager = Manager.fixture(shop, member, "managerName")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
//        val findManager = managerRepository.findByMemberIdAndShopId(member.id!!, shop.id!!)
        val findManager = managerRepository.find(
            memberId = member.id,
            shopId = shop.id
        )[0]
        //then
        assertThat(findManager.id).isEqualTo(manager.id)
    }

    @Test
    fun findByMemberIdTest() {
        //given
        val member = Member.fixture("member", "member@heekng.com")
        memberRepository.save(member)
        val shop = Shop.fixture("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        val manager = Manager.fixture(shop, member, "managerName")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
        val findManagers = managerRepository.find(memberId = member.id)
        //then
        assertThat(findManagers).hasSize(1)
        assertThat(findManagers[0].id).isEqualTo(manager.id)
    }

    @Test
    fun findWithShopByMemberIdTest() {
        //given
        val member = Member.fixture("member", "member@heekng.com")
        memberRepository.save(member)
        val shop = Shop.fixture("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        val manager = Manager.fixture(shop, member, "managerName")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
        val managers = managerRepository.findWithShopByMemberId(member.id!!)
        em.flush()
        em.clear()
        //then
        assertThat(managers).hasSize(1)
        assertThat(managers[0].id).isEqualTo(manager.id)
    }

    @Test
    fun findListByShopIdTest() {
        //given
        val member = Member.fixture("member", "member@heekng.com")
        memberRepository.save(member)
        val shop = Shop.fixture("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        val manager = Manager.fixture(shop, member, "managerName")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
        val managers = managerRepository.find(shopId = shop.id)
        //then
        assertThat(managers).hasSize(1)
        assertThat(managers[0].id).isEqualTo(manager.id)
    }

    @Test
    fun findWithMemberByIdTest() {
        //given
        val member = Member.fixture("member", "member@heekng.com")
        memberRepository.save(member)
        val shop = Shop.fixture("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        val manager = Manager.fixture(shop, member, "managerName")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
        val findManager = managerRepository.findWithMemberById(manager.id!!)
        em.flush()
        em.clear()
        //then
        assertThat(findManager.id).isEqualTo(manager.id)
    }

    @Test
    fun findByShopIdAndIdTest() {
        //given
        val member = Member.fixture("member", "member@heekng.com")
        memberRepository.save(member)
        val shop = Shop.fixture("010-1234-1234", "shop", null)
        shopRepository.save(shop)
        val manager = Manager.fixture(shop, member, "managerName")
        managerRepository.save(manager)
        em.flush()
        em.clear()
        //when
        val findManager = managerRepository.findByShopIdAndId(shop.id!!, manager.id!!)
        //then
        assertThat(findManager!!.id).isEqualTo(manager.id)
    }
}