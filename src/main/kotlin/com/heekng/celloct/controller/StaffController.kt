package com.heekng.celloct.controller

import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.dto.StaffDto
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.service.ShopService
import com.heekng.celloct.service.StaffService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/staff")
class StaffController(
    private val httpSession: HttpSession,
    private val shopService: ShopService,
    private val staffRepository: StaffRepository,
    private val staffService: StaffService,
) {

    @ModelAttribute
    fun preRequest(
        @PathVariable("shopId") shopId: Long,
    ): String? {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        staffRepository.findByMemberIdAndShopId(sessionMember!!.id, shopId)
            ?: return "redirect:/"
        return null
    }

    @GetMapping("/{shopId}")
    fun shopHome(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))

        val findStaff = (staffRepository.findByMemberIdAndShopId(sessionMember!!.id, shopId)
            ?: throw IllegalStateException("존재하지 않는 직원입니다."))
        model.addAttribute("staff", StaffDto.StaffDetailResponse(findStaff))

        return "staff/shopHome"
    }

    @GetMapping("/{shopId}/update")
    fun updateStaff(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))

        val findStaff = (staffRepository.findByMemberIdAndShopId(sessionMember!!.id, shopId)
            ?: throw IllegalStateException("존재하지 않는 직원입니다."))
        model.addAttribute("staff", StaffDto.StaffDetailResponse(findStaff))

        return "staff/shopHomeUpdate"
    }

    @PostMapping("/{shopId}/update")
    fun doUpdateStaff(
        @PathVariable("shopId") shopId: Long,
        updateRequest: StaffDto.UpdateRequest,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): String {
        updateRequest.shopId = shopId
        staffService.updateStaff(updateRequest)
        redirectAttributes.addAttribute("shopId", shopId)

        return "redirect:/staff/{shopId}"
    }

}