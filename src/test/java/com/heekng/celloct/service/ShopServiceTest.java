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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ShopServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    ShopService shopService;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StaffRepository staffRepository;

    @Test
    void makeShopTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@gmail.com")
                .build();
        memberRepository.save(member);

        em.flush();
        em.clear();

        //when
        ShopDto.CreateRequest createRequest = ShopDto.CreateRequest.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .managerName("manager")
                .build();
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

    @Test
    void existNameTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@gmail.com")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .name("testShop")
                .info("information")
                .phone("01012341234")
                .build();
        shopRepository.save(shop);
        em.flush();
        em.clear();
        //when
        boolean result = shopService.existName("testShop");

        //then
        assertThat(result).isTrue();
    }

    @Test
    void updateShopTest() throws Exception {
        //given
        Member member = Member.builder()
                .name("member1")
                .email("member1@gmail.com")
                .build();
        memberRepository.save(member);

        Shop shop = Shop.builder()
                .name("testShop")
                .info("information")
                .phone("01012341234")
                .build();
        shopRepository.save(shop);
        em.flush();
        em.clear();
        //when
        ShopDto.UpdateRequest updateRequest = ShopDto.UpdateRequest.builder()
                .id(shop.getId())
                .phone("01043214321")
                .info("updateInfo")
                .build();
        shopService.updateShop(updateRequest);
        em.flush();
        em.clear();
        Shop findShop = shopRepository.findById(shop.getId()).get();
        //then
        assertThat(findShop.getPhone()).isEqualTo("01043214321");
        assertThat(findShop.getInfo()).isEqualTo("updateInfo");
    }
}