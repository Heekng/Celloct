package com.heekng.celloct.service;

import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ShopServiceTest {

    @Autowired
    ShopService shopService;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    void make() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        //when
        Long shopId = shopService.makeShop(shop);
        Shop findShop = shopService.findShop(shopId);
        //then
        assertThat(findShop).isEqualTo(shop);
    }
}