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
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .password("member1Password")
                .build();
        em.persist(member1);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .member(member1)
                .build();

        em.persist(shop);
        //when
        Shop findShop = em.find(Shop.class, shop.getId());

        //then
        assertThat(shop).isEqualTo(findShop);
        assertThat(shop.getId()).isEqualTo(findShop.getId());
        assertThat(shop.getMember()).isEqualTo(findShop.getMember());
        assertThat(shop.getPhone()).isEqualTo(findShop.getPhone());
    }

    @Test
    void updateShop() throws Exception {
        //given
        String updatePhone = "010-5678-5678";
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .password("member1Password")
                .build();
        em.persist(member1);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .member(member1)
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
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .password("member1Password")
                .build();
        em.persist(member1);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .member(member1)
                .build();

        em.persist(shop);
        //when
        em.remove(shop);
        Shop findShop = em.find(Shop.class, shop.getId());
        //then
        assertThat(findShop).isNull();
    }
}