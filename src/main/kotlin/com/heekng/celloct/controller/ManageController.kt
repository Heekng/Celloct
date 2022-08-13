package com.heekng.celloct.controller

import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.service.ShopService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession
import kotlin.math.log

@Controller
@RequestMapping("/manage")
class ManageController(
    private val httpSession: HttpSession,
    private val managerRepository: ManagerRepository,
    private val shopService: ShopService,
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
    @GetMapping("/{shopId}")
    fun shopHome(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))
        return "manager/shopHome"
    }

    @GetMapping("/{shopId}/update")
    fun updateShop(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))
        return "manager/shopHomeUpdate"
    }

    @PostMapping("/{shopId}/update")
    fun updateShopRequest(
        @PathVariable("shopId") shopId: Long,
        updateRequest: ShopDto.UpdateRequest,
    ): String {
        updateRequest.id = shopId
        shopService.updateShop(updateRequest)
        return "redirect:/manage/${shopId}"
    }

}