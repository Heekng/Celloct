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
import java.util.Objects;
import java.util.Optional;

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
    public void deleteManager(ManagerDto.DeleteRequest deleteRequest) {
        Manager manager = managerRepository.findByShopIdAndId(deleteRequest.getShopId(), deleteRequest.getManagerId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 관리자입니다."));
        if (Objects.equals(manager.getMember().getId(), deleteRequest.getMemberId())) {
            throw new IllegalStateException("본인을 삭제할 수 없습니다.");
        }
        managerRepository.delete(manager);
    }

    @Transactional
    public void updateManager(ManagerDto.updateRequest updateRequest) {
        Manager manager = managerRepository.findById(updateRequest.getId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 관리자입니다."));
        manager.updateInfo(updateRequest.getName());
    }

    public List<Manager> findByMemberId(Long memberId) {
        return managerRepository.findByMemberId(memberId);
    }

    private void validateExistManager(Long shopId, Long memberId) {
        Optional<Manager> managers = managerRepository.findByMemberIdAndShopId(memberId, shopId);
        if (managers.isPresent()) {
            throw new IllegalStateException("이미 존재하는 관리자입니다.");
        }
    }
}
