package com.heekng.celloct.service;

import com.heekng.celloct.entity.Member;
import com.heekng.celloct.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long Join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //비밀번호 변경
    @Transactional
    public void changePassword(String loginId, String nowPassword, String changePassword) {
        validateExist(loginId);
        validateByLoginIdAndPassword(loginId, nowPassword);
        Member findMember = memberRepository.findByLoginId(loginId).get(0);
        findMember.changePassword(changePassword);
    }

    //회원탈퇴
    @Transactional
    public void withdraw(String loginId, String nowPassword) {
        validateExist(loginId);
        validateByLoginIdAndPassword(loginId, nowPassword);
        Member findMember = memberRepository.findByLoginId(loginId).get(0);
        memberRepository.delete(findMember);
    }

    private void validateByLoginIdAndPassword(String loginId, String nowPassword) {
        List<Member> findMembers = memberRepository.findByLoginIdAndPassword(loginId, nowPassword);
        if (findMembers.isEmpty()) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validateExist(String loginId) {
        List<Member> members = memberRepository.findByLoginId(loginId);
        if (members.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 아이디입니다.");
        }
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByLoginId(member.getLoginId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }
}
