package com.heekng.celloct.controller;

import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/create")
    public String createStore(Model model) {
        log.info("createShop");
        model.addAttribute("shop", new ShopDto.createRequest());
        return "/store/createStore";
    }

    @ResponseBody
    @GetMapping("/name/exist")
    public boolean existName(String name) {
        return shopService.existName(name);
    }

    @GetMapping("/join")
    public String joinShop() {
        log.info("joinShop");
        return "joinShop";
    }
}
