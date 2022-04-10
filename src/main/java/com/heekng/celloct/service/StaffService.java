package com.heekng.celloct.service;

import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;

    public List<Staff> findByShopId(Long shopId) {
        return staffRepository.findByShopId(shopId);
    }

    public List<Staff> findByMemberId(Long memberId) {
        return staffRepository.findByMemberId(memberId);
    }

    @Transactional
    public Long addStaff(Long shopId, Long staffMemberId) {
        validateExistStaff(shopId, staffMemberId);
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new IllegalStateException("존재하지 않는 매장입니다."));
        Member member = memberRepository.findById(staffMemberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);
        return staff.getId();
    }

    @Transactional
    public void updateEmploymentDate(Long staffId, LocalDateTime changeEmploymentDate) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new IllegalStateException("존재하지 않는 직원입니다."));
        staff.changeEmploymentDate(changeEmploymentDate);
    }

    @Transactional
    public void deleteStaff(Long staffId) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new IllegalStateException("존재하지 않는 직원입니다."));
        staffRepository.delete(staff);
    }



    private void validateExistStaff(Long shopId, Long staffMemberId) {
        List<Staff> staffList = staffRepository.findByMemberIdAndShopId(staffMemberId, shopId);
        if (!staffList.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 직원입니다.");
        }
    }
}
