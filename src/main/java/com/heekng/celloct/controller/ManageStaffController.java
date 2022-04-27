package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.ManagerDto;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.dto.StaffDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.repository.StaffRepository;
import com.heekng.celloct.service.ShopService;
import com.heekng.celloct.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manage")
public class ManageStaffController {

    private final HttpSession httpSession;
    private final StaffService staffService;
    private final ShopService shopService;
    private final StaffRepository staffRepository;
    private final ManagerRepository managerRepository;

    @GetMapping("/{shopId}/staff")
    public String staffList(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Manager> managerOptional = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (managerOptional.isEmpty()) {
            return "redirect:/";
        }
        List<Manager> managers = managerRepository.findListByShopId(shopId);
        model.addAttribute("managers", managers.stream().map(ManagerDto.managerResponse::new).collect(Collectors.toList()));

        List<Staff> staffs = staffRepository.findByShopId(shopId);
        model.addAttribute("staffs", staffs.stream().map(StaffDto.staffResponse::new).collect(Collectors.toList()));

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "manager/shopStaff";
    }
}
