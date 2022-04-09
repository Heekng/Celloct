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

    /**
     * shop 생성
     * @param shop
     * @return
     */
    @Transactional
    public Long makeShop(Shop shop) {
        validateDuplicateShop(shop);
        shopRepository.save(shop);
        return shop.getId();
    }

    /**
     * shop name 중복확인
     * @param shopName
     * @return true(중복되지 않음), false(중복)
     */
    public boolean checkName(String shopName) {
        List<Shop> shops = shopRepository.findByName(shopName);
        return shops.isEmpty();
    }

    /**
     * shop 전화번호 변경
     * @param shopId
     * @param shopPhone
     */
    @Transactional
    public void updatePhone(Long shopId, String shopPhone) {
        Shop shop = shopRepository.findById(shopId).get();
        shop.updatePhone(shopPhone);
    }

    /**
     * shop 삭제
     * @param shopId
     */
    @Transactional
    public void delete(Long shopId) {
        Shop shop = shopRepository.findById(shopId).get();
        shopRepository.delete(shop);
    }

    @Transactional
    public Manager addManager(Long shopId, Long memberId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new IllegalStateException("존재하지 않는 매장입니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        validateExistManager(shopId, memberId);
        Manager manager = Manager.builder()
                .member(member)
                .shop(shop)
                .build();
        return managerRepository.save(manager);
    }

    @Transactional
    public void deleteManager(Long managerId) {
        managerRepository.deleteById(managerId);
    }

    public Shop findShop(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(() -> new IllegalStateException("존재하지 않는 매장입니다."));
    }

    private void validateExistManager(Long shopId, Long memberId) {
        List<Manager> managers = managerRepository.findByMemberIdAndShopId(memberId, shopId);
        if (!managers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 관리자입니다.");
        }
    }

    private void validateDuplicateShop(Shop shop) {
        List<Shop> shops = shopRepository.findByName(shop.getName());
        if (!shops.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 매장입니다.");
        }
    }
}
