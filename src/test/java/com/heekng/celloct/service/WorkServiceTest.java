package com.heekng.celloct.service;

import com.heekng.celloct.dto.WorkDto;
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

import javax.persistence.EntityManager;
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
    @Autowired
    EntityManager em;

    /**
     * 근무시간 추가
     * @throws Exception
     */
    @Test
    void add() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member member = Member.builder()
                .name("member1")
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .name("staff1")
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);

        em.flush();
        em.clear();

        //when
        LocalDate workDate = LocalDate.of(2020, 5, 5);
        LocalDateTime startDate = LocalDateTime.of(workDate, LocalTime.of(10, 50));
        LocalDateTime endDate = LocalDateTime.of(workDate, LocalTime.of(15, 50));
        WorkDto.AddRequest addRequest = WorkDto.AddRequest.builder()
                .staffId(staff.getId())
                .workDate(workDate)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        Long workId = workService.addWork(addRequest);
        Work findWork = workRepository.findById(workId).get();

        //then
        assertThat(findWork).isNotNull();
    }

    /**
     * 근무시간 수정
     * @throws Exception
     */
    @Test
    void changeWorkTime() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member member = Member.builder()
                .name("member1")
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .name("staff1")
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
        workRepository.save(work);

        em.flush();
        em.clear();

        //when
        LocalDateTime changeStartDate = startDate.minusHours(1);
        LocalDateTime changeEndDate = endDate.plusHours(1);
        WorkDto.ChangeWorkTimeRequest changeWorkTimeRequest = WorkDto.ChangeWorkTimeRequest.builder()
                .workId(work.getId())
                .changeStartDate(changeStartDate)
                .changeEndDate(changeEndDate)
                .build();
        workService.changeWorkTime(changeWorkTimeRequest);
        Work findWork = workRepository.findById(work.getId()).get();

        //then
        assertThat(findWork.getStartDate()).isEqualTo(changeStartDate);
        assertThat(findWork.getEndDate()).isEqualTo(changeEndDate);
    }

    /**
     * 근무 삭제
     * @throws Exception
     */
    @Test
    void deleteWorkTest() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member member = Member.builder()
                .name("member1")
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .name("staff1")
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
        workRepository.save(work);

        em.flush();
        em.clear();

        //when
        workService.deleteWork(work.getId());
        Optional<Work> workOptional = workRepository.findById(work.getId());

        //then
        assertThat(workOptional).isEmpty();
    }
}