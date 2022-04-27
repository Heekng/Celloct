package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        model.addAttribute("shop", new ShopDto.CreateRequest());
        return "shop/createShop";
    }

    @PostMapping("/create")
    public String createStoreRequest(ShopDto.CreateRequest createRequest, RedirectAttributes redirectAttributes) {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        Long shopId = shopService.makeShop(createRequest, sessionMember.getId());
        redirectAttributes.addAttribute("shopId", shopId);
        return "redirect:/manage/{shopId}";
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

    @ResponseBody
    @GetMapping("/list")
    public List<ShopDto.ListResponse> shopList(@RequestParam(name = "shopName") String shopName) {
        return shopService.findListResponseListByNameContaining(shopName);
    }

    @GetMapping("/{shopId}")
    public String shopDetail(@PathVariable Long shopId, Model model) {
        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));
        return "shop/shopDetail";
    }
}
