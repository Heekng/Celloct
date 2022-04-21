package com.heekng.celloct.repository;

import com.heekng.celloct.entity.JoinRequest;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JoinRequestRepositoryTest {

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
        JoinRequest savedJoinRequest = joinRequestRepository.save(joinRequest);
        //when
        JoinRequest findJoinRequest = joinRequestRepository.findById(savedJoinRequest.getId()).get();
        //then
        assertThat(savedJoinRequest).isEqualTo(findJoinRequest);
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
        JoinRequest savedJoinRequest = joinRequestRepository.save(joinRequest);

        //when
        List<JoinRequest> findJoinRequests = joinRequestRepository.findByMemberId(member1.getId());
        //then
        findJoinRequests.forEach(findJoinRequest -> assertThat(findJoinRequest.getMember()).isEqualTo(member1));
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
        JoinRequest savedJoinRequest = joinRequestRepository.save(joinRequest);
        //when
        List<JoinRequest> findJoinRequests = joinRequestRepository.findByShopId(shop.getId());
        //then
        findJoinRequests.forEach(findJoinRequest -> assertThat(findJoinRequest.getShop()).isEqualTo(shop));
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
        JoinRequest savedJoinRequest = joinRequestRepository.save(joinRequest);
        //when
        joinRequestRepository.delete(savedJoinRequest);
        Optional<JoinRequest> joinRequestOptional = joinRequestRepository.findById(savedJoinRequest.getId());
        //then
        assertThat(joinRequestOptional).isEmpty();
    }

}