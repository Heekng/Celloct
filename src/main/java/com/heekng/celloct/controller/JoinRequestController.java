package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.JoinRequestDto;
import com.heekng.celloct.service.JoinRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/joinRequest")
public class JoinRequestController {

    private final HttpSession httpSession;
    private final JoinRequestService joinRequestService;

    @PostMapping
    @ResponseBody
    public Boolean joinRequest(@RequestBody JoinRequestDto.JoinRequest joinRequest) {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        joinRequest.setMemberId(sessionMember.getId());

        try {
            Long joinRequestId = joinRequestService.joinRequest(joinRequest);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
