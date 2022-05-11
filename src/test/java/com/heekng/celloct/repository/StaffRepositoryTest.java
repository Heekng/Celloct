package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StaffRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ShopRepository shopRepository;

    @Test
    @DisplayName("staff 저장 테스트")
    void save() throws Exception {
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

        em.flush();
        em.clear();

        //when
        Staff findStaff = staffRepository.findById(staff.getId()).get();

        //then
        assertThat(staff.getId()).isEqualTo(findStaff.getId());
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

        em.flush();
        em.clear();

        //when
        Staff findStaff = staffRepository.findById(staff.getId()).get();
        staffRepository.delete(findStaff);
        em.flush();
        em.clear();

        Optional<Staff> staffOptional = staffRepository.findById(staff.getId());
        //then
        assertThat(staffOptional).isEmpty();
    }

    @Test
    void findByMemberIdAndShopIdTest() throws Exception {
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

        em.flush();
        em.clear();

        //when
        Optional<Staff> findStaff = staffRepository.findByMemberIdAndShopId(staffMember.getId(), shop.getId());

        //then
        assertThat(findStaff.get().getId()).isEqualTo(staff.getId());
    }

    @Test
    void findByShopIdTest() throws Exception {
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

        em.flush();
        em.clear();
        //when
        List<Staff> staffs = staffRepository.findByShopId(shop.getId());

        //then
        assertThat(staffs.get(0).getId()).isEqualTo(staff.getId());
    }

    @Test
    void findByMemberIdTest() throws Exception {
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

        em.flush();
        em.clear();
        //when
        List<Staff> staffs = staffRepository.findByMemberId(staffMember.getId());

        //then
        assertThat(staffs.get(0).getId()).isEqualTo(staff.getId());
    }
    
    @Test
    void findWithMemberByIdTest() throws Exception {
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

        em.flush();
        em.clear();
        //when
        Staff findStaff = staffRepository.findWithMemberById(staff.getId());
        em.flush();
        em.clear();

        //then
        assertThat(findStaff.getMember().getId()).isEqualTo(staffMember.getId());
    }

    @Test
    void findByShopIdAndIdTest() throws Exception {
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

        em.flush();
        em.clear();
        //when
        Staff findStaff = staffRepository.findByShopIdAndId(shop.getId(), staff.getId()).get();
        //then
        assertThat(findStaff.getId()).isEqualTo(staff.getId());
    }

    @Test
    void findWithShopByMemberIdTest() throws Exception {
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

        em.flush();
        em.clear();
        //when
        List<Staff> staffs = staffRepository.findWithShopByMemberId(staffMember.getId());

        //then
        assertThat(staffs.get(0).getId()).isEqualTo(staff.getId());
    }

}