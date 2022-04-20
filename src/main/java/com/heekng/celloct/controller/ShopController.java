package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final HttpSession httpSession;

    @GetMapping("/create")
    public String createStore(Model model) {
        log.info("createShop");
        model.addAttribute("shop", new ShopDto.createRequest());
        return "shop/createShop";
    }

    @PostMapping("/create")
    public String createStoreRequest(ShopDto.createRequest createRequest, RedirectAttributes redirectAttributes) {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        Long shopId = shopService.makeShop(createRequest, sessionMember.getId());
        redirectAttributes.addAttribute("shopId", shopId);
        return "redirect:/shop/{shopId}";
    }
    @ResponseBody
    @GetMapping("/name/exist")
    public boolean existName(String name) {
        return shopService.existName(name);
    }

    @GetMapping("/join")
    public String joinShop() {
        log.info("joinShop");
        return "shop/joinShop";
    }
}
