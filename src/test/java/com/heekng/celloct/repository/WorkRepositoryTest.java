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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WorkRepositoryTest {

    @Autowired
    EntityManager em;
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
                .email("shopMember@gmail.com")
                .build();
        memberRepository.save(shopMember);

        Member staffMember = Member.builder()
                .name("staffMember")
                .email("staffMember@gmail.com")
                .build();
        memberRepository.save(staffMember);

        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(staffMember)
                .name("staff")
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .staff(staff)
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
        workRepository.save(work);

        em.flush();
        em.clear();

        //when
        Work findWork = workRepository.findById(work.getId()).get();

        //then
        assertThat(work.getId()).isEqualTo(findWork.getId());
    }

    @Test
    void updateWorkTime() throws Exception {
        //given
        Member shopMember = Member.builder()
                .name("shopMember")
                .email("shopMember@gmail.com")
                .build();
        memberRepository.save(shopMember);

        Member staffMember = Member.builder()
                .name("staffMember")
                .email("staffMember@gmail.com")
                .build();
        memberRepository.save(staffMember);

        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(staffMember)
                .name("staff")
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .staff(staff)
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
        workRepository.save(work);

        em.flush();
        em.clear();
        //when
        Work findWork = workRepository.findById(work.getId()).get();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusHours(1L);
        LocalDateTime endDate = now;
        findWork.getWorkTime().changeWorkTime(startDate, endDate);
        em.flush();
        em.clear();

        Work findWork2 = workRepository.findById(work.getId()).get();
        //then
        assertThat(findWork2.getWorkTime().getStartDate()).isEqualTo(startDate);
        assertThat(findWork2.getWorkTime().getEndDate()).isEqualTo(endDate);
    }

    @Test
    void delete() throws Exception {
        //given
        Member shopMember = Member.builder()
                .name("shopMember")
                .email("shopMember@gmail.com")
                .build();
        memberRepository.save(shopMember);

        Member staffMember = Member.builder()
                .name("staffMember")
                .email("staffMember@gmail.com")
                .build();
        memberRepository.save(staffMember);

        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(staffMember)
                .name("staff")
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .staff(staff)
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
        workRepository.save(work);

        em.flush();
        em.clear();
        //when
        Work findWork = workRepository.findById(work.getId()).get();
        workRepository.delete(findWork);
        em.flush();
        em.clear();

        Optional<Work> workOptional = workRepository.findById(work.getId());
        //then
        assertThat(workOptional).isEmpty();
    }

    @Test
    void findByWorkTimeWorkDateAndStaffIdTest() throws Exception {
        //given
        Member shopMember = Member.builder()
                .name("shopMember")
                .email("shopMember@gmail.com")
                .build();
        memberRepository.save(shopMember);

        Member staffMember = Member.builder()
                .name("staffMember")
                .email("staffMember@gmail.com")
                .build();
        memberRepository.save(staffMember);

        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(staffMember)
                .name("staff")
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .staff(staff)
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
        workRepository.save(work);

        em.flush();
        em.clear();
        //when
        List<Work> works = workRepository.findByWorkTimeWorkDateAndStaffId(work.getWorkTime().getWorkDate(), staff.getId());
        //then
        assertThat(works.get(0).getId()).isEqualTo(work.getId());
    }

    @Test
    void findByWorkTimeWorkDateBetweenAndStaffIdTest() throws Exception {
        //given
        Member shopMember = Member.builder()
                .name("shopMember")
                .email("shopMember@gmail.com")
                .build();
        memberRepository.save(shopMember);

        Member staffMember = Member.builder()
                .name("staffMember")
                .email("staffMember@gmail.com")
                .build();
        memberRepository.save(staffMember);

        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(staffMember)
                .name("staff")
                .build();
        staffRepository.save(staff);

        Work work = Work.builder()
                .staff(staff)
                .workDate(LocalDate.now())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
        workRepository.save(work);

        em.flush();
        em.clear();
        //when
        List<Work> works = workRepository.findByWorkTimeWorkDateBetweenAndStaffId(work.getWorkTime().getWorkDate(), work.getWorkTime().getWorkDate(), staff.getId());

        //then
        assertThat(works.get(0).getId()).isEqualTo(work.getId());
    }

}