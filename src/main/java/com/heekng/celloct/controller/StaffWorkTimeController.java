package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.StaffRepository;
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
@RequestMapping("/staff")
public class StaffWorkTimeController {

    private final HttpSession httpSession;
    private final StaffRepository staffRepository;
    private final ShopService shopService;

    @GetMapping("/{shopId}/workTime/insert")
    public String insertWorkTime(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Staff> staffOptional = staffRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (staffOptional.isEmpty()) {
            return "redirect:/";
        }

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "staff/workTimeInsert";
    }
}
