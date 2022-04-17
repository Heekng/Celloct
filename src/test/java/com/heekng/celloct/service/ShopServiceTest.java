package com.heekng.celloct.service;

import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.repository.ShopRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ShopServiceTest {

    @Autowired
    ShopService shopService;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    void make() throws Exception {
        //given
        ShopDto.createRequest createRequest = ShopDto.createRequest.builder()
                .name("shop1")
                .phone("010-1234-1234")
                .build();
        //when
        Long shopId = shopService.makeShop(createRequest);
        Shop shop = shopRepository.findById(shopId).get();

        //then
        assertThat(createRequest.getName()).isEqualTo(shop.getName());
        assertThat(createRequest.getPhone()).isEqualTo(shop.getPhone());
    }
}