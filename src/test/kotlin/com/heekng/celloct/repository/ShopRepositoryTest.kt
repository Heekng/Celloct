package com.heekng.celloct.repository

import com.heekng.celloct.entity.Shop
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ShopRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val shopRepository: ShopRepository,
) {

    @Test
    fun findByNameTest() {
        //given
        val shop = Shop.fixture("010-1234-1234", "shop", "shop")
        shopRepository.save(shop)
        em.flush()
        em.clear()
        //when
        val findShop = shopRepository.findByName(shop.name)
        //then
        assertThat(findShop!!.id).isEqualTo(shop.id)
    }

    @Test
    fun findListByNameContainingTest() {
        //given
        val shop = Shop.fixture("010-1234-1234", "shop", "shop")
        shopRepository.save(shop)
        em.flush()
        em.clear()
        //when
        val shops = shopRepository.findListByNameContaining(shop.name.substring(1, 3))
        //then
        assertThat(shops).hasSize(1)
        assertThat(shops[0].id).isEqualTo(shop.id)
    }
}