package com.heekng.celloct.service;

import com.heekng.celloct.dto.ManagerDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ManagerServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    ManagerService managerService;
    @Autowired
    EntityManager em;

    @Test
    void addManager() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        shopRepository.save(shop);

        em.flush();
        em.clear();

        //when
        System.out.println("========================================");
        ManagerDto.AddRequest addRequestDto = ManagerDto.AddRequest.builder()
                .shopId(shop.getId())
                .memberId(member.getId())
                .build();
        Manager savedManager = managerService.addManager(addRequestDto);
        Member findMember = memberRepository.findById(member.getId()).get();
        Shop findShop = shopRepository.findById(shop.getId()).get();
        System.out.println("========================================");

        //then
        assertThat(findMember.getManagers()).isNotEmpty();
        assertThat(findShop.getManagers()).isNotEmpty();
        assertThat(savedManager).isNotNull();
    }

    @Test
    void deleteManager() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        shopRepository.save(shop);

        Manager manager = Manager.builder()
                .shop(shop)
                .member(member)
                .name("manager1")
                .build();
        managerRepository.save(manager);

        em.flush();
        em.clear();

        //when
        ManagerDto.DeleteRequest deleteRequest = ManagerDto.DeleteRequest.builder()
                .managerId(manager.getId())
                .shopId(shop.getId())
                .build();
        managerService.deleteManager(deleteRequest);
        em.flush();
        em.clear();
        shop = shopRepository.findById(shop.getId()).get();

        //then
        assertThat(shop.getManagers()).isEmpty();

    }

    @Test
    void updateManagerTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        shopRepository.save(shop);

        Manager manager = Manager.builder()
                .shop(shop)
                .member(member)
                .name("manager1")
                .build();
        managerRepository.save(manager);

        em.flush();
        em.clear();
        //when
        ManagerDto.UpdateRequest updateRequest = ManagerDto.UpdateRequest.builder()
                .id(manager.getId())
                .name("changeManagerName")
                .build();
        managerService.updateManager(updateRequest);

        Optional<Manager> managerOptional = managerRepository.findById(manager.getId());
        //then
        assertThat(managerOptional).isNotEmpty();
        assertThat(managerOptional.get().getName()).isNotEqualTo(manager.getName());
    }

    @Test
    void addByStaffTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("test@gmail.com")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        shopRepository.save(shop);

        Staff staff = Staff.builder()
                .name("staff")
                .member(member)
                .shop(shop)
                .build();
        staffRepository.save(staff);

        em.flush();
        em.clear();
        //when
        ManagerDto.AddByStaffRequest addByStaffRequest = ManagerDto.AddByStaffRequest.builder()
                .staffId(staff.getId())
                .shopId(shop.getId())
                .build();
        Manager savedManager = managerService.addByStaff(addByStaffRequest);

        //then
        assertThat(savedManager.getName()).isEqualTo(staff.getName());
    }
}