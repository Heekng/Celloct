package com.heekng.celloct.service;

import com.heekng.celloct.dto.ManagerDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
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
    MemberRepository memberRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ManagerService managerService;
    @Autowired
    EntityManager em;

    @Test
    void addManager() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        shopRepository.save(shop);
        //when
        ManagerDto.addRequest addRequestDto = ManagerDto.addRequest.builder()
                .shopId(shop.getId())
                .memberId(member.getId())
                .build();
        Manager savedManager = managerService.addManager(addRequestDto);

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
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        shopRepository.save(shop);

        Manager manager = Manager.builder()
                .shop(shop)
                .member(member)
                .name("manager1")
                .build();
        Manager savedManager = managerRepository.save(manager);

        em.flush();
        em.clear();

        //when
        ManagerDto.DeleteRequest deleteRequest = ManagerDto.DeleteRequest.builder()
                .managerId(manager.getId())
                .shopId(shop.getId())
                .build();
        managerService.deleteManager(deleteRequest);
        em.flush();
        em.clear();
        shop = shopRepository.findById(shop.getId()).get();

        //then
        assertThat(shop.getManagers()).isEmpty();

    }
}