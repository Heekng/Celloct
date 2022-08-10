package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class JavaShopRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    ShopRepository shopRepository;

    @Test
    void findByNameTest() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop")
                .phone("01012341234")
                .info("testShop")
                .build();
        shopRepository.save(shop);
        em.flush();
        em.clear();

        //when
        Shop findShop = shopRepository.findByName(shop.getName()).get();

        //then
        assertThat(findShop.getId()).isEqualTo(shop.getId());
    }

    @Test
    void findListByNameContainingTest() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop")
                .phone("01012341234")
                .info("testShop")
                .build();
        shopRepository.save(shop);
        em.flush();
        em.clear();

        //when
        List<Shop> shops = shopRepository.findListByNameContaining(shop.getName().substring(1, 3));

        //then
        assertThat(shops.get(0).getId()).isEqualTo(shop.getId());
    }

}