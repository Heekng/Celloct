package com.heekng.celloct.service;

import com.heekng.celloct.dto.StaffDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;

    public List<Staff> findByMemberId(Long memberId) {
        return staffRepository.findByMemberId(memberId);
    }

    @Transactional
    public Long addStaff(StaffDto.AddRequest addRequestDto) {
        Shop shop = shopRepository.findById(addRequestDto.getShopId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 매장입니다."));
        Member member = memberRepository.findById(addRequestDto.getMemberId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        validateExistStaff(addRequestDto.getShopId(), addRequestDto.getMemberId());
        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);
        return staff.getId();
    }

    @Transactional
    public void updateEmploymentDate(StaffDto.UpdateEmploymentDateRequest updateEmploymentDateRequestDto) {
        Staff staff = staffRepository.findById(updateEmploymentDateRequestDto.getStaffId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 직원입니다."));
        staff.changeEmploymentDate(updateEmploymentDateRequestDto.getChangeEmploymentDate());
    }

    @Transactional
    public void deleteStaff(StaffDto.DeleteRequest deleteRequest) {
        Manager manager = managerRepository.findByMemberIdAndShopId(deleteRequest.getMemberId(), deleteRequest.getShopId()).orElseThrow(() -> new IllegalStateException("해당 매장의 관리자가 아닙니다."));
        Staff staff = staffRepository.findById(deleteRequest.getStaffId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 직원입니다."));
        staffRepository.delete(staff);
    }

    private void validateExistStaff(Long shopId, Long staffMemberId) {
        Optional<Staff> staffList = staffRepository.findByMemberIdAndShopId(staffMemberId, shopId);
        if (staffList.isPresent()) {
            throw new IllegalStateException("이미 존재하는 직원입니다.");
        }
    }

    @Transactional
    public void updateStaff(StaffDto.UpdateRequest updateRequest) {
        Staff findStaff = staffRepository.findByShopIdAndId(updateRequest.getShopId(), updateRequest.getStaffId()).orElseThrow(() -> new IllegalStateException("해당 매장에 등록된 직원이 아닙니다."));
        findStaff.updateInfo(updateRequest.getName());
    }
}
