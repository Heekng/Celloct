package com.heekng.celloct.controller

import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.dto.JoinRequestDto
import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.repository.JoinRequestRepository
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.service.JoinRequestService
import com.heekng.celloct.util.findByIdOrThrow
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/manage")
class ManageJoinRequestController(
    private val httpSession: HttpSession,
    private val managerRepository: ManagerRepository,
    private val joinRequestRepository: JoinRequestRepository,
    private val joinRequestService: JoinRequestService,
    private val shopRepository: ShopRepository,
) {

    @ModelAttribute
    fun preRequest(
        @PathVariable("shopId") shopId: Long,
    ): String? {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        managerRepository.findByMemberIdAndShopId(sessionMember!!.id, shopId)
            ?: return "redirect:/"
        return null
    }

    @GetMapping("/{shopId}/joinRequest")
    fun joinRequest(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val joinRequestResponses = joinRequestRepository.findWithMemberByShopId(shopId)
            .map { joinRequest -> JoinRequestDto.ManagerShopJoinRequestResponse(joinRequest) }
        model.addAttribute("joinRequests", joinRequestResponses)
        val shop = shopRepository.findByIdOrThrow(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))

        return "manager/shopJoinRequest"
    }

    @PostMapping("/{shopId}/joinRequest/approval")
    @ResponseBody
    fun approval(
        @RequestBody approvalRequest: JoinRequestDto.ApprovalRefusalRequest,
        @PathVariable("shopId") shopId: Long,
    ): Boolean {
        joinRequestService.approval(approvalRequest)
        return true
    }

    @PostMapping("/{shopId}/joinRequest/refusal")
    @ResponseBody
    fun refusal(
        @RequestBody approvalRequest: JoinRequestDto.ApprovalRefusalRequest,
        @PathVariable("shopId") shopId: Long,
    ): Boolean {
        joinRequestService.refusal(approvalRequest)
        return true
    }



}