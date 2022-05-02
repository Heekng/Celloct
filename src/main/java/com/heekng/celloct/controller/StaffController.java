package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.dto.StaffDto;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.StaffRepository;
import com.heekng.celloct.service.ShopService;
import com.heekng.celloct.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {

    private final HttpSession httpSession;
    private final ShopService shopService;
    private final StaffRepository staffRepository;
    private final StaffService staffService;


    @GetMapping("/{shopId}")
    public String shopHome(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Staff> staffOptional = staffRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (staffOptional.isEmpty()) {
            return "redirect:/";
        }

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        Staff findStaff = staffRepository.findByMemberIdAndShopId(member.getId(), shopId).orElseThrow(() -> new IllegalStateException("존재하지 않는 직원입니다."));
        model.addAttribute("staff", new StaffDto.StaffDetailResponse(findStaff));
        return "staff/shopHome";
    }

    @GetMapping("/{shopId}/update")
    public String updateStaff(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Staff> staffOptional = staffRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (staffOptional.isEmpty()) {
            return "redirect:/";
        }

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        Staff findStaff = staffRepository.findByMemberIdAndShopId(member.getId(), shopId).orElseThrow(() -> new IllegalStateException("존재하지 않는 직원입니다."));
        model.addAttribute("staff", new StaffDto.StaffDetailResponse(findStaff));
        return "staff/shopHomeUpdate";
    }

    @PostMapping("/{shopId}/update")
    public String doUpdateStaff(@PathVariable("shopId") Long shopId, StaffDto.UpdateRequest updateRequest, Model model, RedirectAttributes redirectAttributes) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Staff> staffOptional = staffRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (staffOptional.isEmpty()) {
            return "redirect:/";
        }

        updateRequest.setShopId(shopId);
        staffService.updateStaff(updateRequest);
        redirectAttributes.addAttribute("shopId", shopId);

        return "redirect:/staff/{shopId}";

    }

}
