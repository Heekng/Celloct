package com.heekng.celloct.controller;

import com.heekng.celloct.config.oauth.dto.SessionMember;
import com.heekng.celloct.dto.JoinRequestDto;
import com.heekng.celloct.dto.ShopDto;
import com.heekng.celloct.entity.Manager;
import com.heekng.celloct.entity.Shop;
import com.heekng.celloct.repository.JoinRequestRepository;
import com.heekng.celloct.repository.ManagerRepository;
import com.heekng.celloct.repository.ShopRepository;
import com.heekng.celloct.service.JoinRequestService;
import com.heekng.celloct.service.ShopService;
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
@RequestMapping("/manage")
public class ManageJoinRequestController {

    private final HttpSession httpSession;
    private final ManagerRepository managerRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final JoinRequestService joinRequestService;
    private final ShopService shopService;

    @GetMapping("/{shopId}/joinRequest")
    public String joinRequest(@PathVariable("shopId") Long shopId, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Manager> managerOptional = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (managerOptional.isEmpty()) {
            return "redirect:/";
        }

        List<JoinRequestDto.ManagerShopJoinRequestResponse> joinRequestResponses = joinRequestRepository.findWithMemberByShopId(shopId)
                .stream().map(JoinRequestDto.ManagerShopJoinRequestResponse::new)
                .collect(Collectors.toList());
        model.addAttribute("joinRequests", joinRequestResponses);
        Shop shop = shopService.findShop(shopId);
        model.addAttribute("shop", new ShopDto.ShopDetailResponse(shop));

        return "manager/manageShopJoinRequest";
    }

    @PostMapping("/{shopId}/joinRequest/approval")
    @ResponseBody
    public Boolean approval(@RequestBody JoinRequestDto.ApprovalRefusalRequest approvalRequest, @PathVariable("shopId") Long shopId) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Manager> managerOptional = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (managerOptional.isEmpty()) {
            return false;
        }

        Long staffId = joinRequestService.approval(approvalRequest);

        return true;
    }

    @PostMapping("/{shopId}/joinRequest/refusal")
    @ResponseBody
    public Boolean refusal(@RequestBody JoinRequestDto.ApprovalRefusalRequest approvalRequest, @PathVariable("shopId") Long shopId) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Optional<Manager> managerOptional = managerRepository.findByMemberIdAndShopId(member.getId(), shopId);
        if (managerOptional.isEmpty()) {
            return false;
        }

        joinRequestService.refusal(approvalRequest);

        return true;
    }
}
