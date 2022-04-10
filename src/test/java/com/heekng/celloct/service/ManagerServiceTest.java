package com.heekng.celloct.service;

import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ManagerServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ShopService shopService;
    @Autowired
    ManagerService managerService;
    @Autowired
    EntityManager em;

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
        Manager savedManager = managerService.addManager(shop.getId(), member.getId());

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
        Manager savedManager = managerService.addManager(shop.getId(), member.getId());

        em.flush();
        em.clear();

        //when
        managerService.deleteManager(savedManager.getId());
        em.flush();
        em.clear();
        shop = shopService.findShop(shop.getId());

        //then
        assertThat(shop.getManagers()).isEmpty();

    }
}