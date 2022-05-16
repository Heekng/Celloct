package com.heekng.celloct.service;

import com.heekng.celloct.dto.WorkUpdateRequestDto;
import com.heekng.celloct.entity.*;
import com.heekng.celloct.repository.*;
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
    WorkRepository workRepository;
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
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .name("staff1")
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);

        LocalDate workDate = LocalDate.of(2022, 10, 10);
        LocalDateTime startDate = LocalDateTime.of(2022, 10, 10, 5, 10);
        LocalDateTime endDate = LocalDateTime.of(2022, 10, 10, 6, 10);

        Work work = Work.builder()
                .workDate(workDate)
                .startDate(startDate)
                .endDate(endDate)
                .staff(staff)
                .build();
        workRepository.save(work);

        //when
        WorkUpdateRequestDto.AddRequest addRequest = WorkUpdateRequestDto.AddRequest.builder()
                .staffId(staff.getId())
                .workId(work.getId())
                .workDate(workDate)
                .startDate(startDate.minusHours(1))
                .endDate(endDate.plusHours(1))
                .build();
        Long workUpdateRequestId = workUpdateRequestService.addWorkUpdateRequest(addRequest);
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
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .name("staff1")
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);

        LocalDate workDate = LocalDate.of(2022, 10, 10);
        LocalDateTime startDate = LocalDateTime.of(2022, 10, 10, 5, 10);
        LocalDateTime endDate = LocalDateTime.of(2022, 10, 10, 6, 10);

        Work work = Work.builder()
                .workDate(workDate)
                .startDate(startDate)
                .endDate(endDate)
                .staff(staff)
                .build();
        workRepository.save(work);

        WorkUpdateRequest workUpdateRequest = WorkUpdateRequest.builder()
                .work(work)
                .workDate(workDate)
                .startDate(startDate.minusHours(1))
                .endDate(startDate.plusHours(1))
                .build();
        workUpdateRequestRepository.save(workUpdateRequest);

        em.flush();
        em.clear();

        //when
        LocalDateTime updateStartDate = workUpdateRequest.getWorkTime().getStartDate().minusHours(1);
        WorkUpdateRequestDto.UpdateRequest updateRequest = WorkUpdateRequestDto.UpdateRequest.builder()
                .workUpdateRequestId(workUpdateRequest.getId())
                .updateDate(workUpdateRequest.getWorkTime().getWorkDate())
                .updateStartDate(updateStartDate)
                .updateEndDate(workUpdateRequest.getWorkTime().getEndDate())
                .build();

        workUpdateRequestService.updateWorkUpdateRequest(updateRequest);
        //then
        assertThat(updateRequest.getUpdateStartDate()).isEqualTo(updateStartDate);
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

        LocalDate workDate = LocalDate.of(2022, 10, 10);
        LocalDateTime startDate = LocalDateTime.of(2022, 10, 10, 5, 10);
        LocalDateTime endDate = LocalDateTime.of(2022, 10, 10, 6, 10);

        Work work = Work.builder()
                .workDate(workDate)
                .startDate(startDate)
                .endDate(endDate)
                .staff(staff)
                .build();
        workRepository.save(work);

        WorkUpdateRequest workUpdateRequest = WorkUpdateRequest.builder()
                .work(work)
                .workDate(workDate)
                .startDate(startDate.minusHours(1))
                .endDate(endDate)
                .build();
        workUpdateRequestRepository.save(workUpdateRequest);

        //when
        workUpdateRequestService.deleteWorkUpdateRequest(workUpdateRequest.getId());
        Optional<WorkUpdateRequest> workUpdateRequestOptional = workUpdateRequestRepository.findById(workUpdateRequest.getId());
        //then
        assertThat(workUpdateRequestOptional).isEmpty();

    }



}