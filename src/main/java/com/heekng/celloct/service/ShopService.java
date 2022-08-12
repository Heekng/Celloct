package com.heekng.celloct.service;

import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;

    /**
     * shop 생성 및 매니저 등록
     */
    @Transactional
    public Long makeShop(ShopDto.CreateRequest createRequest, Long memberId) {
        Shop shop = createRequest.toEntity();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        String managerName = createRequest.getManagerName().equals("") ? member.getName() : createRequest.getManagerName();
        validateDuplicateShop(shop);
        Manager manager = Manager.Companion.fixture(shop, member, managerName);
        shop.addManager(manager);

        shopRepository.save(shop);

        return shop.getId();
    }

    /**
     * shop name 중복확인
     * @param shopName
     * @return true(중복되지 않음), false(중복)
     */
    public boolean existName(String shopName) {
        Shop shop = shopRepository.findByName(shopName);
        return shop != null;
    }

    /**
     * shop 정보 변경
     */
    @Transactional
    public void updateShop(ShopDto.UpdateRequest updateRequest) {
        Shop shop = shopRepository.findById(updateRequest.getId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 매장입니다."));
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

    public List<ShopDto.ListResponse> findListResponseListByNameContaining(String name) {
        return shopRepository.findListByNameContaining(name)
                .stream().map(ShopDto.ListResponse::new)
                .collect(Collectors.toList());
    }

    private void validateDuplicateShop(Shop shop) {
        Shop findShop = shopRepository.findByName(shop.getName());
        if (findShop != null) {
            throw new IllegalStateException("이미 존재하는 매장입니다.");
        }
    }
}
