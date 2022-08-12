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
    private final WorkRepository workRepository;

    @GetMapping("/workTimes")
    public String workTimes(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            return "redirect:/";
        }

        List<StaffDto.StaffResponse> staffs = staffRepository.findByShopId(shopId).stream()
                .map(StaffDto.StaffResponse::new)
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
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            throw new IllegalStateException("해당 매장의 매니저가 아닙니다.");
        }
        WorkDto.FindWorkRequest findWorkRequest = WorkDto.FindWorkRequest.builder()
                .staffId(staffId)
                .shopId(shopId)
                .year(year)
                .month(month)
                .build();
        return workService.findWorkByFindWorkRequest(findWorkRequest).stream()
                .map(WorkDto.WorkResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/workTimes/{staffId}")
    @ResponseBody
    public WorkDto.WorkDetailResponse workDetail(
            @PathVariable("shopId") Long shopId,
            @PathVariable("staffId") Long staffId,
            @RequestParam("workId") Long workId
    ) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            throw new IllegalStateException("해당 매장의 매니저가 아닙니다.");
        }
        Staff staff = staffRepository.findByShopIdAndId(shopId, staffId);
        if (staff == null) {
            throw new IllegalStateException("존재하지 않는 직원입니다.");
        }

        Work work = workRepository.findById(workId).orElseThrow(() -> new IllegalStateException("존재하지 않는 근무입니다"));
        return new WorkDto.WorkDetailResponse(work);
    }

    @PostMapping("/workTimes/{staffId}/update")
    @ResponseBody
    public Boolean workUpdate(
            @PathVariable("shopId") Long shopId,
            @PathVariable("staffId") Long staffId,
            @RequestBody WorkDto.UpdateRequest updateRequest
    ) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            throw new IllegalStateException("해당 매장의 매니저가 아닙니다.");
        }
        Staff staff = staffRepository.findByShopIdAndId(shopId, staffId);
        if (staff == null) {
            throw new IllegalStateException("존재하지 않는 직원입니다.");
        }
        updateRequest.setStaffId(staffId);

        workService.updateWork(updateRequest);
        return true;
    }

    @PostMapping("/workTimes/{staffId}/delete")
    @ResponseBody
    public Boolean workUpdate(
            @PathVariable("shopId") Long shopId,
            @PathVariable("staffId") Long staffId,
            @RequestBody WorkDto.DeleteRequest deleteRequest
    ) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Manager manager = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (manager == null) {
            throw new IllegalStateException("해당 매장의 매니저가 아닙니다.");
        }
        Staff staff = staffRepository.findByShopIdAndId(shopId, staffId);
        if (staff == null) {
            throw new IllegalStateException("존재하지 않는 직원입니다.");
        }
        deleteRequest.setStaffId(staffId);
        workService.deleteWork(deleteRequest);
        return true;
    }
}
