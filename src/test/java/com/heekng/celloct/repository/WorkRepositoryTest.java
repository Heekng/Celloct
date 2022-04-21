package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.entity.Work;
import org.apache.tomcat.jni.Local;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WorkRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    WorkRepository workRepository;

    @Test
    void create() throws Exception {
        //given
        Member shopMember = Member.builder()
                .name("shopMember")
                .build();
        memberRepository.save(shopMember);

        Member staffMember = Member.builder()
                .name("staffMember")
                .build();
        memberRepository.save(staffMember);

        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(staffMember)
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .staff(staff)
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
        Work saveWork = workRepository.save(work);
        //when
        Work findWork = workRepository.findById(work.getId()).get();
        //then
        assertThat(saveWork).isEqualTo(findWork);
    }

    @Test
    void updateWorkTime() throws Exception {
        //given
        Member shopMember = Member.builder()
                .name("shopMember")
                .build();
        memberRepository.save(shopMember);

        Member staffMember = Member.builder()
                .name("staffMember")
                .build();
        memberRepository.save(staffMember);

        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(staffMember)
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .staff(staff)
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
        Work saveWork = workRepository.save(work);
        //when
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusHours(1L);
        LocalDateTime endDate = now;
        saveWork.changeWorkTime(startDate, endDate);
        Work findWork = workRepository.findById(work.getId()).get();
        //then
        assertThat(findWork.getStartDate()).isEqualTo(startDate);
        assertThat(findWork.getEndDate()).isEqualTo(endDate);
    }

    @Test
    void delete() throws Exception {
        //given
        Member shopMember = Member.builder()
                .name("shopMember")
                .build();
        memberRepository.save(shopMember);

        Member staffMember = Member.builder()
                .name("staffMember")
                .build();
        memberRepository.save(staffMember);

        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(staffMember)
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .staff(staff)
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
        Work saveWork = workRepository.save(work);
        //when
        workRepository.delete(saveWork);
        Optional<Work> workOptional = workRepository.findById(work.getId());
        //then
        assertThat(workOptional).isEmpty();
    }

}