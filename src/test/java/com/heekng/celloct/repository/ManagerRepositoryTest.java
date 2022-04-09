package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ManagerRepositoryTest {

    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    EntityManager em;

    @Test
    void create() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@heekng.com")
                .password("member1234")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Manager manager = Manager.builder()
                .member(member)
                .shop(shop)
                .build();
        managerRepository.save(manager);

        //when
        Manager findManager = managerRepository.findById(manager.getId()).get();
        //then
        assertThat(findManager.getMember()).isEqualTo(manager.getMember());
        assertThat(findManager.getShop()).isEqualTo(manager.getShop());
    }

    @Test
    void delete() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@heekng.com")
                .password("member1234")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Manager manager = Manager.builder()
                .member(member)
                .shop(shop)
                .build();
        managerRepository.save(manager);
        //when
        managerRepository.delete(manager);
        Optional<Manager> managerOptional = managerRepository.findById(manager.getId());
        //then
        assertThat(managerOptional).isEmpty();
    }

}