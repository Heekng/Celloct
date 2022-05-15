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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/staff/{shopId}")
public class StaffWorkTimeController {

    private final HttpSession httpSession;
    private final StaffRepository staffRepository;
    private final ShopService shopService;
    private final WorkRepository workRepository;
    private final WorkService workService;

    @GetMapping("/workTime/insert")
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

    @GetMapping("/workTime/validateExist")
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

    @PostMapping("/workTime/insert")
    public String doInsertWorkTime(@PathVariable("shopId") Long shopId, WorkDto.AddRequest addRequest, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Staff> staffOptional = staffRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (staffOptional.isEmpty()) {
            return "redirect:/";
        }

        addRequest.setMemberId(member.getId());
        addRequest.setShopId(shopId);
        Long workId = workService.addWork(addRequest);

        return "redirect:/";
    }

    @GetMapping("/workTimes")
    public String workTimes(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Staff> staffOptional = staffRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (staffOptional.isEmpty()) {
            return "redirect:/";
        }

        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "staff/workTime";
    }

    @GetMapping("/workTimes/{year}/{month}")
    @ResponseBody
    public List<WorkDto.WorkResponse> findWorks(
            @PathVariable("shopId") Long shopId,
            @PathVariable("year") Integer year,
            @PathVariable("month") Integer month
    ) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Staff> staffOptional = staffRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (staffOptional.isEmpty()) {
            throw new IllegalStateException("해당 매장의 직원이 아닙니다.");
        }

        Staff staff = staffRepository.findByMemberIdAndShopId(member.getId(), shopId).orElseThrow(() -> new IllegalStateException("존재하지 않는 직원입니다."));

        WorkDto.FindWorkRequest findWorkRequest = WorkDto.FindWorkRequest.builder()
                .staffId(staff.getId())
                .shopId(shopId)
                .year(year)
                .month(month)
                .build();
        return workService.findWorkByFindWorkRequest(findWorkRequest).stream()
                .map(WorkDto.WorkResponse::new)
                .collect(Collectors.toList());
    }
}
