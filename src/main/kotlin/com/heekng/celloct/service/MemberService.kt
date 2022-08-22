package com.heekng.celloct.service

import com.heekng.celloct.entity.Member
import com.heekng.celloct.repository.MemberRepository
import com.heekng.celloct.util.fail
import com.heekng.celloct.util.findByIdOrThrow
import com.heekng.celloct.util.whenEmpty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun withdraw(memberId: Long) {
        val findMember = memberRepository.findByIdOrThrow(memberId)
        memberRepository.delete(findMember)
    }

    fun findById(memberId: Long): Member {
        return memberRepository.findByIdOrThrow(memberId)
    }

    private fun validateExist(email: String) {
        memberRepository.find(email = email).whenEmpty { fail() }
    }
}