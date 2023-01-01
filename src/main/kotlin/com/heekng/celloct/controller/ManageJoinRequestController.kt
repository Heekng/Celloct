package com.heekng.celloct.controller

import com.heekng.celloct.annotation.RoleCheck
import com.heekng.celloct.annotation.enums.UserType
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


    @GetMapping("/{shopId}/joinRequest")
    @RoleCheck(UserType.MANAGER)
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
    @RoleCheck(UserType.MANAGER, true)
    fun approval(
        @PathVariable("shopId") shopId: Long,
        @RequestBody approvalRequest: JoinRequestDto.ApprovalRefusalRequest,
    ): Boolean {
        joinRequestService.approval(approvalRequest)
        return true
    }

    @PostMapping("/{shopId}/joinRequest/refusal")
    @ResponseBody
    @RoleCheck(UserType.MANAGER, true)
    fun refusal(
        @PathVariable("shopId") shopId: Long,
        @RequestBody approvalRequest: JoinRequestDto.ApprovalRefusalRequest,
    ): Boolean {
        joinRequestService.refusal(approvalRequest)
        return true
    }



}