package com.heekng.celloct.controller

import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.dto.JoinRequestDto
import com.heekng.celloct.service.JoinRequestService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/joinRequest")
class JoinRequestController(
    private val httpSession: HttpSession,
    private val joinRequestService: JoinRequestService,
) {

    @PostMapping
    @ResponseBody
    fun joinRequest(
        @RequestBody joinRequest: JoinRequestDto.JoinRequest,
    ): Boolean {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        joinRequest.memberId = sessionMember!!.id

        try {
            val joinRequestId = joinRequestService.joinRequest(joinRequest)
        } catch (e: Exception) {
            return false
        }
        return true
    }
}