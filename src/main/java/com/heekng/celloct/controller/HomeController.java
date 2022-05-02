package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HttpSession httpSession;
    private final ManagerService managerService;
    private final ManagerRepository managerRepository;

    @GetMapping("/")
    public String home(Model model) {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");

        if (sessionMember != null) {
            List<Manager> managers = managerRepository.findWithShopByMemberId(sessionMember.getId());
            List<ShopDto.HomeShopResponse> shops = managers.stream()
                    .map(Manager::getShop)
                    .map(ShopDto.HomeShopResponse::new)
                    .collect(Collectors.toList());
            model.addAttribute("manageShops", shops);
        } else {
            model.addAttribute("manageShops", null);
        }

        return "home";
    }

    @GetMapping("/index")
    public String index(Model model) {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");

        if (sessionMember != null) {
            model.addAttribute("memberName", sessionMember.getName());
        }

        return "index";
    }
}
