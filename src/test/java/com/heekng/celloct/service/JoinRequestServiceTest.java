package com.heekng.celloct.service;

import com.heekng.celloct.dto.JoinRequestDto;
import com.heekng.celloct.entity.JoinRequest;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.JoinRequestRepository;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
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
class JoinRequestServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    JoinRequestRepository joinRequestRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    JoinRequestService joinRequestService;

    @Test
    void joinRequestTest() throws Exception {
        //given
        Member member = Member.builder()
                .email("member@gmail.com")
                .name("member")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .phone("01012341234")
                .info("shopInfo")
                .name("shop")
                .build();
        shopRepository.save(shop);

        em.flush();
        em.clear();

        //when
        JoinRequestDto.joinRequest joinRequest = JoinRequestDto.joinRequest.builder()
                .shopId(shop.getId())
                .memberId(member.getId())
                .build();
        Long joinRequestId = joinRequestService.joinRequest(joinRequest);
        JoinRequest findJoinRequest = joinRequestRepository.findById(joinRequestId).get();
        //then
        assertThat(findJoinRequest.getShop().getId()).isEqualTo(shop.getId());
        assertThat(findJoinRequest.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    void cancelTest() throws Exception {
        //given
        Member member = Member.builder()
                .email("member@gmail.com")
                .name("member")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .phone("01012341234")
                .info("shopInfo")
                .name("shop")
                .build();
        shopRepository.save(shop);

        JoinRequest joinRequest = JoinRequest.builder()
                .shop(shop)
                .member(member)
                .build();
        joinRequestRepository.save(joinRequest);

        em.flush();
        em.clear();
        //when
        joinRequestService.cancel(joinRequest.getId());
        Optional<JoinRequest> joinRequestOptional = joinRequestRepository.findById(joinRequest.getId());
        //then
        assertThat(joinRequestOptional).isEmpty();
    }

    @Test
    void approvalTest() throws Exception {
        //given
        Member member = Member.builder()
                .email("member@gmail.com")
                .name("member")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .phone("01012341234")
                .info("shopInfo")
                .name("shop")
                .build();
        shopRepository.save(shop);

        JoinRequest joinRequest = JoinRequest.builder()
                .shop(shop)
                .member(member)
                .build();
        joinRequestRepository.save(joinRequest);

        em.flush();
        em.clear();
        //when
        JoinRequestDto.ApprovalRefusalRequest approvalRefusalRequest = JoinRequestDto.ApprovalRefusalRequest.builder()
                .joinRequestId(joinRequest.getId())
                .build();
        Long staffId = joinRequestService.approval(approvalRefusalRequest);

        Optional<JoinRequest> joinRequestOptional = joinRequestRepository.findById(joinRequest.getId());
        Optional<Staff> staffOptional = staffRepository.findById(staffId);
        //then
        assertThat(joinRequestOptional).isEmpty();
        assertThat(staffOptional).isNotEmpty();
    }

    @Test
    void refusalTest() throws Exception {
        //given
        Member member = Member.builder()
                .email("member@gmail.com")
                .name("member")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .phone("01012341234")
                .info("shopInfo")
                .name("shop")
                .build();
        shopRepository.save(shop);

        JoinRequest joinRequest = JoinRequest.builder()
                .shop(shop)
                .member(member)
                .build();
        joinRequestRepository.save(joinRequest);

        em.flush();
        em.clear();
        //when
        JoinRequestDto.ApprovalRefusalRequest approvalDto = JoinRequestDto.ApprovalRefusalRequest.builder()
                .joinRequestId(joinRequest.getId())
                .build();
        joinRequestService.refusal(approvalDto);

        Optional<JoinRequest> joinRequestOptional = joinRequestRepository.findById(joinRequest.getId());
        //then
        assertThat(joinRequestOptional).isEmpty();
    }
}