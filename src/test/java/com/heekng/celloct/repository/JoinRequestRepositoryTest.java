package com.heekng.celloct.repository;

import com.heekng.celloct.entity.JoinRequest;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
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
class JoinRequestRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    JoinRequestRepository joinRequestRepository;

    @Test
    void create() throws Exception {
        //given
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .build();
        memberRepository.save(member1);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        shopRepository.save(shop);

        JoinRequest joinRequest = JoinRequest.builder()
                .member(member1)
                .shop(shop)
                .build();
        joinRequestRepository.save(joinRequest);

        em.flush();
        em.clear();
        //when
        JoinRequest findJoinRequest = joinRequestRepository.findById(joinRequest.getId()).get();
        //then
        assertThat(joinRequest.getMember().getId()).isEqualTo(findJoinRequest.getMember().getId());
        assertThat(joinRequest.getShop().getId()).isEqualTo(findJoinRequest.getShop().getId());
    }

    @Test
    void findByMemberId() throws Exception {
        //given
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .build();
        memberRepository.save(member1);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        shopRepository.save(shop);

        JoinRequest joinRequest = JoinRequest.builder()
                .member(member1)
                .shop(shop)
                .build();
        joinRequestRepository.save(joinRequest);

        em.flush();
        em.clear();

        //when
        List<JoinRequest> findJoinRequests = joinRequestRepository.findByMemberId(member1.getId());
        //then
        findJoinRequests.forEach(findJoinRequest -> assertThat(findJoinRequest.getMember().getId()).isEqualTo(member1.getId()));
    }

    @Test
    void findByShopId() throws Exception {
        //given
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .build();
        memberRepository.save(member1);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        shopRepository.save(shop);

        JoinRequest joinRequest = JoinRequest.builder()
                .member(member1)
                .shop(shop)
                .build();
        joinRequestRepository.save(joinRequest);

        em.flush();
        em.clear();
        //when
        List<JoinRequest> findJoinRequests = joinRequestRepository.findByShopId(shop.getId());
        //then
        findJoinRequests.forEach(findJoinRequest -> assertThat(findJoinRequest.getShop().getId()).isEqualTo(shop.getId()));
    }

    @Test
    void deleteJoinRequest() throws Exception {
        //given
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .build();
        memberRepository.save(member1);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        shopRepository.save(shop);

        JoinRequest joinRequest = JoinRequest.builder()
                .member(member1)
                .shop(shop)
                .build();
        joinRequestRepository.save(joinRequest);

        em.flush();
        em.clear();
        //when
        JoinRequest findJoinRequest = joinRequestRepository.findById(joinRequest.getId()).get();
        joinRequestRepository.delete(findJoinRequest);
        Optional<JoinRequest> joinRequestOptional = joinRequestRepository.findById(joinRequest.getId());
        //then
        assertThat(joinRequestOptional).isEmpty();
    }

    @Test
    void findByMemberIdAndShopIdTest() throws Exception {
        //given
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .build();
        memberRepository.save(member1);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        shopRepository.save(shop);

        JoinRequest joinRequest = JoinRequest.builder()
                .member(member1)
                .shop(shop)
                .build();
        joinRequestRepository.save(joinRequest);

        em.flush();
        em.clear();
        //when
        List<JoinRequest> joinRequests = joinRequestRepository.findByMemberIdAndShopId(member1.getId(), shop.getId());

        //then
        assertThat(joinRequests.get(0).getShop().getId()).isEqualTo(shop.getId());
        assertThat(joinRequests.get(0).getMember().getId()).isEqualTo(member1.getId());
    }

    @Test
    void findWithMemberByShopIdTest() throws Exception {
        //given
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .build();
        memberRepository.save(member1);

        Shop shop = Shop.builder()
                .phone("010-1234-1234")
                .name("shop1")
                .build();
        shopRepository.save(shop);

        JoinRequest joinRequest = JoinRequest.builder()
                .member(member1)
                .shop(shop)
                .build();
        joinRequestRepository.save(joinRequest);

        em.flush();
        em.clear();
        //when
        List<JoinRequest> joinRequests = joinRequestRepository.findWithMemberByShopId(shop.getId());
        em.flush();
        em.clear();

        //then
        assertThat(joinRequests.get(0).getMember().getId()).isEqualTo(member1.getId());
        assertThat(joinRequests.get(0).getShop().getId()).isEqualTo(shop.getId());
    }

}