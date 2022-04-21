package com.heekng.celloct.service;

import com.heekng.celloct.dto.MemberDto;
import com.heekng.celloct.entity.Member;
import com.heekng.celloct.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원탈퇴
    @Transactional
    public void withdraw(Long memberId) {
//        validateExist(email);
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        memberRepository.delete(findMember);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
    }

    private void validateExist(String email) {
        Optional<Member> members = memberRepository.findByEmail(email);
        if (members.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 이메일입니다.");
        }
    }
}
