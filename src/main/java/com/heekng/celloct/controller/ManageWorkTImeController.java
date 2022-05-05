package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manage")
public class ManageWorkTImeController {

    private final HttpSession httpSession;
    private final ManagerRepository managerRepository;
    private final ShopService shopService;

    @GetMapping("/{shopId}/workTimes")
    public String workTimes(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Manager> managerOptional = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (managerOptional.isEmpty()) {
            return "redirect:/";
        }

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "manager/workTime";
    }
}
