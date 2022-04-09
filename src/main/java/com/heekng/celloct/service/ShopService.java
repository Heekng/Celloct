package com.heekng.celloct.service;

import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;

    @Transactional
    public Long make(Shop shop, Long memberId) {
        validateDuplicateShop(shop);
        shopRepository.save(shop);
        Member member = memberRepository.findById(memberId).get();
        Manager manager = Manager.builder()
                .shop(shop)
                .member(member)
                .build();
        managerRepository.save(manager);
        return shop.getId();
    }

    public boolean checkName(String name) {
        List<Shop> shops = shopRepository.findByName(name);
        return shops.isEmpty();
    }

    @Transactional
    public void updatePhone(Long id, String phone) {
        Shop shop = shopRepository.findById(id).get();
        shop.updatePhone(phone);
    }

    @Transactional
    public void delete(Long id) {
        Shop shop = shopRepository.findById(id).get();
        shopRepository.delete(shop);
    }

    private void validateDuplicateShop(Shop shop) {
        List<Shop> shops = shopRepository.findByName(shop.getName());
        if (!shops.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 매장입니다.");
        }
    }
}
