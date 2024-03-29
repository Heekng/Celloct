package com.heekng.celloct.aop.aspect

import com.heekng.celloct.annotation.RoleCheck
import com.heekng.celloct.annotation.enums.UserType
import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.repository.StaffRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.Signature
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import javax.servlet.http.HttpSession

@Aspect
@Component
class CheckUserTypeAspect(
    private val httpSession: HttpSession,
    private val managerRepository: ManagerRepository,
    private val staffRepository: StaffRepository,
) {

    @Around("@annotation(roleCheck)")
    fun userTypeRequest(
        joinPoint: ProceedingJoinPoint,
        roleCheck: RoleCheck,
    ): Any? {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        var isRightMember: Boolean = sessionMember != null
        if (sessionMember != null) {
            val shopId: Long = joinPoint.args[0] as Long
            if (roleCheck.userType == UserType.STAFF) {
                isRightMember = staffRepository.findOneQ(
                    memberId = sessionMember.id,
                    shopId = shopId,
                ) != null
            } else {
                isRightMember = managerRepository.find(
                    memberId = sessionMember.id,
                    shopId = shopId,
                ).isNotEmpty()
            }
        }

        if (isRightMember) {
            return joinPoint.proceed()
        } else {
            if (roleCheck.isRest) {
                if (returnTypeIsBoolean(joinPoint.signature)) {
                    return false
                } else {
                    throw IllegalStateException("정상적인 요청이 아닙니다.")
                }
            } else {
                return "redirect:/"
            }
        }

    }

    private fun returnTypeIsBoolean(signature: Signature): Boolean {
        return (signature as MethodSignature).returnType.equals(Boolean::class)
    }
}