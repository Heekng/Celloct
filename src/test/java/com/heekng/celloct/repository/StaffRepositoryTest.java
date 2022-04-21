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

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StaffRepositoryTest {

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
        //when
        Staff findStaff = staffRepository.findById(staff.getId()).get();

        //then
        assertThat(staff.getMember()).isEqualTo(findStaff.getMember());
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
        //when
        staffRepository.delete(staff);
        Optional<Staff> staffOptional = staffRepository.findById(staff.getId());
        //then
        assertThat(staffOptional).isEmpty();
    }

}