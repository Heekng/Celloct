package com.heekng.celloct.service;

import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.entity.Work;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
import com.heekng.celloct.repository.WorkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WorkServiceTest {

    @Autowired
    WorkService workService;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void add() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member member = Member.builder()
                .name("member1")
                .password("member1234")
                .loginId("loginId1234")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);
        LocalDate workDate = LocalDate.of(2020, 5, 5);
        LocalDateTime startDate = LocalDateTime.of(workDate, LocalTime.of(10, 50));
        LocalDateTime endDate = LocalDateTime.of(workDate, LocalTime.of(15, 50));
        Work work = Work.builder()
                .staff(staff)
                .workDate(workDate)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //when
        workService.addWork(work);
        Work findWork = workRepository.findById(work.getId()).get();

        //then
        assertThat(findWork).isEqualTo(work);
    }

    @Test
    void changeWorkTime() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member member = Member.builder()
                .name("member1")
                .password("member1234")
                .loginId("loginId1234")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);
        LocalDate workDate = LocalDate.of(2020, 5, 5);
        LocalDateTime startDate = LocalDateTime.of(workDate, LocalTime.of(10, 50));
        LocalDateTime endDate = LocalDateTime.of(workDate, LocalTime.of(15, 50));
        Work work = Work.builder()
                .staff(staff)
                .workDate(workDate)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        workService.addWork(work);

        //when
        LocalDateTime changeStartDate = startDate.minusHours(1);
        LocalDateTime changeEndDate = endDate.plusHours(1);
        workService.changeWorkTime(work.getId(), changeStartDate, changeEndDate);

        //then
        assertThat(work.getStartDate()).isEqualTo(changeStartDate);
        assertThat(work.getEndDate()).isEqualTo(changeEndDate);
    }

    @Test
    void deleteWorkTest() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member member = Member.builder()
                .name("member1")
                .password("member1234")
                .loginId("loginId1234")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);
        LocalDate workDate = LocalDate.of(2020, 5, 5);
        LocalDateTime startDate = LocalDateTime.of(workDate, LocalTime.of(10, 50));
        LocalDateTime endDate = LocalDateTime.of(workDate, LocalTime.of(15, 50));
        Work work = Work.builder()
                .staff(staff)
                .workDate(workDate)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        workService.addWork(work);

        //when
        workService.deleteWork(work.getId());
        Optional<Work> workOptional = workRepository.findById(work.getId());

        //then
        assertThat(workOptional).isEmpty();
    }
}