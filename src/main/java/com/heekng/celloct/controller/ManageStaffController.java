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
import com.heekng.celloct.service.ManagerService;
import com.heekng.celloct.service.ShopService;
import com.heekng.celloct.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
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
    private final ManagerService managerService;

    @GetMapping("/{shopId}/staff")
    public String staffList(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return "redirect:/";
        }
        List<Manager> managers = managerRepository.findListByShopId(shopId);
        model.addAttribute("managers", managers.stream().map(ManagerDto.ManagerResponse::new).collect(Collectors.toList()));

        List<Staff> staffs = staffRepository.findByShopId(shopId);
        model.addAttribute("staffs", staffs.stream().map(StaffDto.StaffResponse::new).collect(Collectors.toList()));

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "manager/shopStaff";
    }

    @GetMapping("/{shopId}/manager/{managerId}")
    public String managerDetail(@PathVariable("shopId") Long shopId, @PathVariable("managerId") Long managerId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return "redirect:/";
        }

        Manager findManager = managerRepository.findWithMemberById(managerId);
        model.addAttribute("manager", new ManagerDto.WithMemberResponse(findManager));

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "manager/managerDetail";
    }

    @GetMapping("/{shopId}/manager/{managerId}/update")
    public String managerDetailUpdate(@PathVariable("shopId") Long shopId, @PathVariable("managerId") Long managerId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return "redirect:/";
        }

        Manager findManager = managerRepository.findWithMemberById(managerId);
        model.addAttribute("manager", new ManagerDto.WithMemberResponse(findManager));

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "manager/managerDetailUpdate";
    }

    @PostMapping("/{shopId}/manager/{managerId}/update")
    public String doManagerDetailUpdate(ManagerDto.UpdateRequest updateRequest, @PathVariable("shopId") Long shopId, @PathVariable("managerId") Long managerId, Model model, RedirectAttributes redirectAttributes) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return "redirect:/";
        }

        updateRequest.setId(managerId);
        managerService.updateManager(updateRequest);

        redirectAttributes.addAttribute("managerId", managerId);
        redirectAttributes.addAttribute("shopId", shopId);

        return "redirect:/manage/{shopId}/manager/{managerId}";
    }

    @PostMapping("/{shopId}/manager/{managerId}/delete")
    @ResponseBody
    public Boolean managerDelete(@PathVariable("shopId") Long shopId, @PathVariable("managerId") Long managerId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return false;
        }

        ManagerDto.DeleteRequest deleteRequest = ManagerDto.DeleteRequest.builder()
                .managerId(managerId)
                .shopId(shopId)
                .memberId(member.getId())
                .build();
        managerService.deleteManager(deleteRequest);

        return true;
    }

    @GetMapping("/{shopId}/staff/{staffId}")
    public String staffDetail(@PathVariable("shopId") Long shopId, @PathVariable("staffId") Long staffId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return "redirect:/";
        }

        Staff findStaff = staffRepository.findWithMemberById(staffId);
        model.addAttribute("staff", new StaffDto.WithMemberResponse(findStaff));

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "manager/staffDetail";
    }

    @PostMapping("/{shopId}/staff/{staffId}/delete")
    @ResponseBody
    public Boolean staffDelete(@PathVariable("shopId") Long shopId, @PathVariable("staffId") Long staffId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return false;
        }

        StaffDto.DeleteRequest deleteRequest = StaffDto.DeleteRequest.builder()
                .staffId(staffId)
                .shopId(shopId)
                .memberId(member.getId())
                .build();
        staffService.deleteStaff(deleteRequest);

        return true;
    }

    @GetMapping("/{shopId}/staff/{staffId}/update")
    public String staffDetailUpdate(@PathVariable("shopId") Long shopId, @PathVariable("staffId") Long staffId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return "redirect:/";
        }

        Staff findStaff = staffRepository.findWithMemberById(staffId);
        model.addAttribute("staff", new StaffDto.WithMemberResponse(findStaff));

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "manager/staffDetailUpdate";
    }

    @PostMapping("/{shopId}/staff/{staffId}/update")
    public String doStaffDetailUpdate(StaffDto.UpdateRequest updateRequest, @PathVariable("shopId") Long shopId, @PathVariable("staffId") Long staffId, Model model, RedirectAttributes redirectAttributes) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return "redirect:/";
        }

        updateRequest.setStaffId(staffId);
        updateRequest.setShopId(shopId);
        staffService.updateStaff(updateRequest);

        redirectAttributes.addAttribute("staffId", staffId);
        redirectAttributes.addAttribute("shopId", shopId);

        return "redirect:/manage/{shopId}/staff/{staffId}";
    }

    @PostMapping("/{shopId}/staff/{staffId}/addManager")
    @ResponseBody
    public ManagerDto.AddByStaffResponse addManager(StaffDto.UpdateRequest updateRequest, @PathVariable("shopId") Long shopId, @PathVariable("staffId") Long staffId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            throw new IllegalStateException("해당 매장의 매니저가 아닙니다.");
        }

        Staff staff = staffRepository.findByShopIdAndId(shopId, staffId);
        if (staff == null) {
            throw new IllegalStateException("해당 매장의 직원이 아닙니다.");
        }

        ManagerDto.AddByStaffRequest addByStaffRequest = ManagerDto.AddByStaffRequest.builder()
                .staffId(staffId)
                .shopId(shopId)
                .build();
        Manager savedManager = managerService.addByStaff(addByStaffRequest);
        return new ManagerDto.AddByStaffResponse(savedManager);
    }
}
