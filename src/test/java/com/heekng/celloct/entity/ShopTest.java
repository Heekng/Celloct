package com.heekng.celloct.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShopTest {

    @Autowired
    EntityManager em;

    @Test
    void createShop() throws Exception {
        //given
        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        em.persist(shop);

        //when
        Shop findShop = em.find(Shop.class, shop.getId());

        //then
        assertThat(shop).isEqualTo(findShop);
        assertThat(shop.getId()).isEqualTo(findShop.getId());
        assertThat(shop.getPhone()).isEqualTo(findShop.getPhone());
    }

    @Test
    void updateShop() throws Exception {
        //given
        String updatePhone = "010-5678-5678";

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        em.persist(shop);

        //when
        shop.updatePhone(updatePhone);
        Shop findShop = em.find(Shop.class, shop.getId());
        //then
        assertThat(findShop.getPhone()).isEqualTo(updatePhone);
    }

    @Test
    void deleteShop() throws Exception {
        //given
        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();

        em.persist(shop);
        //when
        em.remove(shop);
        Shop findShop = em.find(Shop.class, shop.getId());
        //then
        assertThat(findShop).isNull();
    }
}