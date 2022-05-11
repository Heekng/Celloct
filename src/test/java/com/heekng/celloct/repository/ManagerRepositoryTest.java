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

import java.util.List;
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
                .name("managerName")
                .build();
        managerRepository.save(manager);

        em.flush();
        em.clear();

        //when
        Manager findManager = managerRepository.findById(manager.getId()).get();
        //then
        assertThat(findManager.getMember().getId()).isEqualTo(manager.getMember().getId());
        assertThat(findManager.getShop().getId()).isEqualTo(manager.getShop().getId());
    }

    @Test
    void delete() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@heekng.com")
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
                .name("managerName")
                .build();
        managerRepository.save(manager);
        em.flush();
        em.clear();
        //when
        Manager findManager = managerRepository.findById(manager.getId()).get();
        managerRepository.delete(findManager);
        em.flush();
        em.clear();

        Optional<Manager> managerOptional = managerRepository.findById(manager.getId());
        //then
        assertThat(managerOptional).isEmpty();
    }

    @Test
    void findByMemberIdAndShopIdTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@heekng.com")
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
                .name("managerName")
                .build();
        managerRepository.save(manager);
        em.flush();
        em.clear();
        //when
        Optional<Manager> managers = managerRepository.findByMemberIdAndShopId(member.getId(), shop.getId());

        //then
        assertThat(managers.get().getName()).isEqualTo(manager.getName());
    }

    @Test
    void findByMemberIdTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@heekng.com")
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
                .name("managerName")
                .build();
        managerRepository.save(manager);
        em.flush();
        em.clear();
        //when
        List<Manager> managers = managerRepository.findByMemberId(member.getId());

        //then
        assertThat(managers.get(0).getName()).isEqualTo(manager.getName());
    }

    @Test
    void findWithShopByMemberIdTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@heekng.com")
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
                .name("managerName")
                .build();
        managerRepository.save(manager);
        em.flush();
        em.clear();
        //when
        List<Manager> managers = managerRepository.findWithShopByMemberId(member.getId());
        em.flush();
        em.clear();

        //then
        assertThat(managers.get(0).getShop().getName()).isEqualTo(shop.getName());
    }

    @Test
    void findListByShopIdTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@heekng.com")
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
                .name("managerName")
                .build();
        managerRepository.save(manager);
        em.flush();
        em.clear();
        //when
        List<Manager> managers = managerRepository.findListByShopId(shop.getId());
        //then
        assertThat(managers.get(0).getName()).isEqualTo(manager.getName());
    }

    @Test
    void findWithMemberByIdTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@heekng.com")
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
                .name("managerName")
                .build();
        managerRepository.save(manager);
        em.flush();
        em.clear();
        //when
        Manager findManager = managerRepository.findWithMemberById(manager.getId());
        em.flush();
        em.clear();
        //then
        assertThat(findManager.getMember().getName()).isEqualTo(member.getName());
    }

    @Test
    void findByShopIdAndIdTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@heekng.com")
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
                .name("managerName")
                .build();
        managerRepository.save(manager);
        em.flush();
        em.clear();
        //when
        Optional<Manager> managers = managerRepository.findByShopIdAndId(shop.getId(), manager.getId());

        //then
        assertThat(managers.get().getName()).isEqualTo(manager.getName());
    }

}