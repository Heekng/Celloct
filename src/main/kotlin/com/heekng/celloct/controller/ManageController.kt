package com.heekng.celloct.controller

import com.heekng.celloct.annotation.RoleCheck
import com.heekng.celloct.annotation.enum.UserType
import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.service.ShopService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/manage")
class ManageController(
    private val httpSession: HttpSession,
    private val managerRepository: ManagerRepository,
    private val shopService: ShopService,
) {
    @GetMapping("/{shopId}")
    @RoleCheck(UserType.MANAGER)
    fun shopHome(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))
        return "manager/shopHome"
    }

    @GetMapping("/{shopId}/update")
    @RoleCheck(UserType.MANAGER)
    fun updateShop(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))
        return "manager/shopHomeUpdate"
    }

    @PostMapping("/{shopId}/update")
    @RoleCheck(UserType.MANAGER)
    fun updateShopRequest(
        @PathVariable("shopId") shopId: Long,
        updateRequest: ShopDto.UpdateRequest,
    ): String {
        updateRequest.id = shopId
        shopService.updateShop(updateRequest)
        return "redirect:/manage/${shopId}"
    }

}