package com.heekng.celloct.service;

import com.heekng.celloct.entity.JoinRequest;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.JoinRequestRepository;
import com.heekng.celloct.repository.MemberRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;
    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;
    private final StaffRepository staffRepository;

    //가입 신청
    public Long joinRequest(Long memberId, Long shopId) {
        validateDuplicateStaff(memberId, shopId);
        Member findMember = memberRepository.findById(memberId).get();
        Shop findShop = shopRepository.findById(shopId).get();
        JoinRequest joinRequest = JoinRequest.builder()
                .shop(findShop)
                .member(findMember)
                .build();
        JoinRequest savedJoinRequest = joinRequestRepository.save(joinRequest);
        return savedJoinRequest.getId();
    }

    private void validateDuplicateStaff(Long memberId, Long shopId) {
        List<Staff> byMemberIdAndShopId = staffRepository.findByMemberIdAndShopId(memberId, shopId);
        if(!byMemberIdAndShopId.isEmpty()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    //가입신청 조회(회원)
    public List<JoinRequest> findByMemberId(Long memberId) {
        return joinRequestRepository.findByMemberId(memberId);
    }

    //가입신청 조회(매장)
    public List<JoinRequest> findByShopId(Long shopId) {
        return joinRequestRepository.findByShopId(shopId);
    }

    //가입신청 취소
    public void cancel(Long joinRequestId) {
        JoinRequest findJoinRequest = joinRequestRepository.findById(joinRequestId).get();
        joinRequestRepository.delete(findJoinRequest);
    }

    //가입신청 승인
    public Long Approval(Long joinRequestId) {
        Optional<JoinRequest> joinRequestOptional = joinRequestRepository.findById(joinRequestId);
        if (joinRequestOptional.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 가입신청입니다.");
        }
        JoinRequest findJoinRequest = joinRequestOptional.get();
        Member member = findJoinRequest.getMember();
        Shop shop = findJoinRequest.getShop();
        validateDuplicateStaff(member.getId(), shop.getId());
        LocalDateTime now = LocalDateTime.now();
        Staff staff = Staff.builder()
                .shop(shop)
                .member(member)
                .build();
        staffRepository.save(staff);
        return staff.getId();
    }
}
