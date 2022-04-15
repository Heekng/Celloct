package com.heekng.celloct.service;

import com.heekng.celloct.dto.ManagerDto;
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
public class ManagerService {

    private final ShopRepository shopRepository;
    private final ManagerRepository managerRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Manager addManager(ManagerDto.addRequest addRequestDto) {
        Shop shop = shopRepository.findById(addRequestDto.getShopId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 매장입니다."));
        Member member = memberRepository.findById(addRequestDto.getMemberId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        validateExistManager(addRequestDto.getShopId(), addRequestDto.getMemberId());
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

    private void validateExistManager(Long shopId, Long memberId) {
        List<Manager> managers = managerRepository.findByMemberIdAndShopId(memberId, shopId);
        if (!managers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 관리자입니다.");
        }
    }
}
