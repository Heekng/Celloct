package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.dto.WorkDto;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.StaffRepository;
import com.heekng.celloct.repository.WorkRepository;
import com.heekng.celloct.service.ShopService;
import com.heekng.celloct.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final WorkRepository workRepository;
    private final WorkService workService;

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

    @GetMapping("/{shopId}/workTime/validateExist")
    @ResponseBody
    public Boolean checkExist(@PathVariable("shopId") Long shopId, WorkDto.CheckExistRequest checkExistRequest) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Staff> staffOptional = staffRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (staffOptional.isEmpty()) {
            return false;
        }

        checkExistRequest.setMemberId(member.getId());
        checkExistRequest.setShopId(shopId);
        return workService.validateExist(checkExistRequest);
    }

    @PostMapping("/{shopId}/workTime/insert")
    public void doInsertWorkTime(@PathVariable("shopId") Long shopId, Model model) {

    }
}
