package com.heekng.celloct.service;

import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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
    void addTest() throws Exception {
        //given

        //when

        //then

    }
}