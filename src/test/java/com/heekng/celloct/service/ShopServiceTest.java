package com.heekng.celloct.service;

import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ShopServiceTest {

    @Autowired
    ShopService shopService;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StaffRepository staffRepository;

    @Test
    void make() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .build();
        memberRepository.save(member);

        ShopDto.CreateRequest createRequest = ShopDto.CreateRequest.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .managerName("")
                .build();
        //when
        Long shopId = shopService.makeShop(createRequest, member.getId());
        Shop shop = shopRepository.findById(shopId).get();

        //then
        assertThat(createRequest.getName()).isEqualTo(shop.getName());
        assertThat(createRequest.getPhone()).isEqualTo(shop.getPhone());
    }

    @Test
    void findListByNameContaining() {
        //given
        Member member1 = Member.builder()
                .name("member1")
                .email("member1@gmail.com")
                .build();
        Member member2 = Member.builder()
                .name("member2")
                .email("member2@gmail.com")
                .build();
        Member member3 = Member.builder()
                .name("member3")
                .email("member3@gmail.com")
                .build();
        Shop shop = Shop.builder()
                .name("shop1")
                .build();
        Staff staff1 = Staff.builder()
                .name("staff1")
                .member(member1)
                .shop(shop)
                .build();
        Staff staff2 = Staff.builder()
                .name("staff2")
                .member(member2)
                .shop(shop)
                .build();
        Staff staff3 = Staff.builder()
                .name("staff3")
                .member(member3)
                .shop(shop)
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        shopRepository.save(shop);
        staffRepository.save(staff1);
        staffRepository.save(staff2);
        staffRepository.save(staff3);

        //when
        List<ShopDto.ListResponse> listResponses = shopService.findListResponseListByNameContaining("op");
        //then
        assertThat(listResponses.get(0).getStaffCount()).isEqualTo(3);
    }
}