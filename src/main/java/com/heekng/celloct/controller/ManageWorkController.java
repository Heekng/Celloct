package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.dto.StaffDto;
import com.heekng.celloct.dto.WorkDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.entity.Work;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.repository.StaffRepository;
import com.heekng.celloct.service.ShopService;
import com.heekng.celloct.service.StaffService;
import com.heekng.celloct.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manage/{shopId}")
public class ManageWorkController {

    private final HttpSession httpSession;
    private final ManagerRepository managerRepository;
    private final ShopService shopService;
    private final StaffRepository staffRepository;
    private final WorkService workService;

    @GetMapping("/workTimes")
    public String workTimes(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Manager> managerOptional = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (managerOptional.isEmpty()) {
            return "redirect:/";
        }

        List<StaffDto.staffResponse> staffs = staffRepository.findByShopId(shopId).stream()
                .map(StaffDto.staffResponse::new)
                .collect(Collectors.toList());
        model.addAttribute("staffs", staffs);
        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "manager/workTime";
    }

    @GetMapping("/workTimes/{staffId}/{year}/{month}")
    @ResponseBody
    public List<WorkDto.WorkResponse> findWork(
            @PathVariable("shopId") Long shopId,
            @PathVariable("staffId") Long staffId,
            @PathVariable("year") Integer year,
            @PathVariable("month") Integer month
    ) {
        log.info("in findWork");
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Manager> managerOptional = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (managerOptional.isEmpty()) {
            throw new IllegalStateException("해당 매장의 매니저가 아닙니다.");
        }
        log.info("111111");
        WorkDto.FindWorkRequest findWorkRequest = WorkDto.FindWorkRequest.builder()
                .staffId(staffId)
                .shopId(shopId)
                .year(year)
                .month(month)
                .build();
        log.info("22222");
        return workService.findWorkByFindWorkRequest(findWorkRequest).stream()
                .map(WorkDto.WorkResponse::new)
                .collect(Collectors.toList());
    }
}
