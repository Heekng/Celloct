package com.heekng.celloct.service;

import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.entity.Shop;
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

    /**
     * shop 생성
     * @param shop
     * @return
     */
    @Transactional
    public Long makeShop(ShopDto.createRequest createRequest) {
        Shop shop = createRequest.toEntity();
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
     */
    @Transactional
    public void updatePhone(ShopDto.updateRequest updateRequest) {
        Shop shop = shopRepository.findById(updateRequest.getId()).get();
        shop.update(updateRequest.getPhone(), updateRequest.getInfo());
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

    /**
     * 매장 찾기
     * @param shopId
     * @return
     */
    public Shop findShop(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(() -> new IllegalStateException("존재하지 않는 매장입니다."));
    }

    /**
     * 이름으로 매장 리스트 찾기
     * @param name
     * @return
     */
    public List<Shop> findListByName(String name) {
        return shopRepository.findByName(name);
    }

    private void validateDuplicateShop(Shop shop) {
        List<Shop> shops = shopRepository.findByName(shop.getName());
        if (!shops.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 매장입니다.");
        }
    }
}
