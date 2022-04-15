package com.heekng.celloct.service;

import com.heekng.celloct.entity.*;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
import com.heekng.celloct.repository.WorkUpdateRequestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WorkUpdateRequestServiceTest {

    @Autowired
    WorkUpdateRequestService workUpdateRequestService;
    @Autowired
    WorkUpdateRequestRepository workUpdateRequestRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    WorkService workService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("근무시간 수정 요청 추가")
    void addTest() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member member = Member.builder()
                .name("member1")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);


        Work work = Work.builder()
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now().minusHours(2))
                .endDate(LocalDateTime.now().minusHours(1))
                .staff(staff)
                .build();
        workService.addWork(work);

        Long workUpdateRequestId = workUpdateRequestService.addWorkUpdateRequest(work.getId(), work.getWorkDate(), work.getStartDate(), work.getEndDate());
        //when
        WorkUpdateRequest findWorkUpdateRequest = workUpdateRequestRepository.findById(workUpdateRequestId).get();

        //then
        assertThat(work).isEqualTo(findWorkUpdateRequest.getWork());
    }

    @Test
    @DisplayName("근무시간 수정 요청 수정")
    void updateTest() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member member = Member.builder()
                .name("member1")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now().minusHours(2))
                .endDate(LocalDateTime.now().minusHours(1))
                .staff(staff)
                .build();
        workService.addWork(work);

        Long workUpdateRequestId = workUpdateRequestService.addWorkUpdateRequest(work.getId(), work.getWorkDate(), work.getStartDate(), work.getEndDate());

        WorkUpdateRequest findWorkUpdateRequest = workUpdateRequestRepository.findById(workUpdateRequestId).get();

        LocalDateTime updateStartDate = findWorkUpdateRequest.getUpdateStartDate().minusHours(1);
        //when
        workUpdateRequestService.updateWorkUpdateRequest(findWorkUpdateRequest.getId(), findWorkUpdateRequest.getUpdateWorkDate(), updateStartDate, findWorkUpdateRequest.getUpdateEndDate());
        //then
        assertThat(findWorkUpdateRequest.getUpdateStartDate()).isEqualTo(updateStartDate);
    }

    @Test
    @DisplayName("근무시간 수정 요청 삭제")
    void deleteTest() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member member = Member.builder()
                .name("member1")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now().minusHours(2))
                .endDate(LocalDateTime.now().minusHours(1))
                .staff(staff)
                .build();
        workService.addWork(work);

        Long workUpdateRequestId = workUpdateRequestService.addWorkUpdateRequest(work.getId(), work.getWorkDate(), work.getStartDate(), work.getEndDate());
        WorkUpdateRequest workUpdateRequest = workUpdateRequestRepository.findById(workUpdateRequestId).get();
        //when
        workUpdateRequestService.deleteWorkUpdateRequest(workUpdateRequestId);
        Optional<WorkUpdateRequest> workUpdateRequestOptional = workUpdateRequestRepository.findById(workUpdateRequestId);
        //then
        assertThat(workUpdateRequestOptional).isEmpty();

    }



}