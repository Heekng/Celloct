package com.heekng.celloct.repository;

import com.heekng.celloct.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WorkUpdateRequestRepositoryTest {
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
    @Autowired
    WorkUpdateRequestRepository workUpdateRequestRepository;

    @Test
    void findByWorkIdTest() throws Exception {
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

        WorkUpdateRequest workUpdateRequest = WorkUpdateRequest.builder()
                .work(work)
                .workDate(work.getWorkTime().getWorkDate())
                .startDate(work.getWorkTime().getStartDate().minusHours(1))
                .endDate(work.getWorkTime().getEndDate().plusHours(1))
                .build();
        workUpdateRequestRepository.save(workUpdateRequest);

        em.flush();
        em.clear();
        //when
        List<WorkUpdateRequest> workUpdateRequests = workUpdateRequestRepository.findByWorkId(work.getId());

        //then
        Assertions.assertThat(workUpdateRequests.get(0).getWorkTime().getStartDate()).isEqualTo(workUpdateRequest.getWorkTime().getStartDate());
    }
}