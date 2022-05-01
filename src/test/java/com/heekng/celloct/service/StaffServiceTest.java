package com.heekng.celloct.service;

import com.heekng.celloct.dto.StaffDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
class StaffServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    StaffService staffService;
    @Autowired
    EntityManager em;

    /**
     * builder을 통해 객체 생성시 list에 주입하였을 때 영속성 컨텍스트에 남아있는지 확인
     * @throws Exception
     */
    @Test
    @DisplayName("영속성 컨텍스트가 비워지면 연관관계의 주인이 아닌 객체의 리스트에도 비워진다.")
    void test() throws Exception {
        Member member = Member.builder()
                .name("member1")
                .build();
        memberRepository.save(member);
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();

        em.flush();
        em.clear();

        member = memberRepository.findById(member.getId()).get();

        List<Staff> staffList = member.getStaffList();

        for (Staff staffs : staffList) {
            System.out.println("staffs.getEmploymentDate() = " + staffs.getEmploymentDate());
        }
    }

    @Test
    @DisplayName("직원 추가 테스트")
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

        em.flush();
        em.clear();

        //when
        StaffDto.addRequest addRequestDto = StaffDto.addRequest.builder()
                .memberId(member.getId())
                .shopId(shop.getId())
                .build();
        Long savedStaffId = staffService.addStaff(addRequestDto);
        Staff findStaff = staffRepository.findById(savedStaffId).get();

        //then
        assertThat(findStaff).isNotNull();
    }

    @Test
    @DisplayName("가입날짜 변경 테스트")
    void updateEmploymentDateTest() throws Exception {
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
                .member(member)
                .shop(shop)
                .build();
        staffRepository.save(staff);

        em.flush();
        em.clear();

        //when
        LocalDate now = LocalDate.now();
        LocalDate changeDate = now.minusDays(4);
        StaffDto.updateEmploymentDateRequest updateEmploymentDateRequestDto = StaffDto.updateEmploymentDateRequest.builder()
                .staffId(staff.getId())
                .changeEmploymentDate(changeDate)
                .build();
        staffService.updateEmploymentDate(updateEmploymentDateRequestDto);
        Staff findStaff = staffRepository.findById(staff.getId()).get();

        //then
        assertThat(findStaff.getEmploymentDate()).isEqualTo(changeDate);
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteTest() throws Exception {
        //given
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        shopRepository.save(shop);

        Member managerMember = Member.builder()
                .name("managerMember")
                .build();
        memberRepository.save(managerMember);

        Manager manager = Manager.builder()
                .name("managerMember")
                .shop(shop)
                .member(managerMember)
                .build();
        managerRepository.save(manager);

        Member member = Member.builder()
                .name("member1")
                .build();
        memberRepository.save(member);

        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);

        em.flush();
        em.clear();

        //when
        StaffDto.DeleteRequest deleteStaff = StaffDto.DeleteRequest.builder()
                .memberId(managerMember.getId())
                .staffId(staff.getId())
                .shopId(shop.getId())
                .build();
        staffService.deleteStaff(deleteStaff);
        Optional<Staff> staffOptional = staffRepository.findById(staff.getId());
        //then
        assertThat(staffOptional).isEmpty();
    }
}