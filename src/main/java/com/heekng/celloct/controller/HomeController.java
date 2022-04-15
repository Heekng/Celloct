package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String home(Model model) {
        log.info("test");
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");

        if (sessionMember != null) {
            model.addAttribute("memberName", sessionMember.getName());
        }

        return "index";
    }
}
