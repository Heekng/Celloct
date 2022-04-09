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

    @Test
    void addManager() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .password("member1234")
                .build();
        memberService.join(member);

        Shop shop = Shop.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        shopService.makeShop(shop);
        //when
        Manager savedManager = shopService.addManager(shop.getId(), member.getId());

        //then
        assertThat(member.getManagers()).isNotEmpty();
        assertThat(shop.getManagers()).isNotEmpty();
        assertThat(savedManager).isNotNull();
    }
    
    @Test
    void deleteManager() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .password("member1234")
                .build();
        memberService.join(member);

        Shop shop = Shop.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        shopService.makeShop(shop);
        Manager savedManager = shopService.addManager(shop.getId(), member.getId());

        em.flush();
        em.clear();

        //when
        shopService.deleteManager(savedManager.getId());
        em.flush();
        em.clear();
        shop = shopService.findShop(shop.getId());

        //then
        assertThat(shop.getManagers()).isEmpty();
        
    }
}