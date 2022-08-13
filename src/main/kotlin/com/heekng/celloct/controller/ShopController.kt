package com.heekng.celloct.controller

import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.service.ShopService
import com.heekng.celloct.util.findByIdOrThrow
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/shop")
class ShopController(
    private val shopService: ShopService,
    private val shopRepository: ShopRepository,
    private val httpSession: HttpSession,
) {

    @GetMapping("/create")
    fun createStore(
        model: Model,
    ): String {
        model.addAttribute("shop", ShopDto.CreateRequest())
        return "shop/createShop"
    }

    @PostMapping("/create")
    fun createStoreRequest(
        createRequest: ShopDto.CreateRequest,
        redirectAttributes: RedirectAttributes,
    ): String {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        val shopId = shopService.makeShop(createRequest, sessionMember!!.id)
        redirectAttributes.addAttribute("shopId", shopId)
        return "redirect:/manage/{shopId}"
    }

    @ResponseBody
    @GetMapping("/name/exist")
    fun existName(name: String): Boolean {
        return shopService.existName(name)
    }

    @GetMapping("/join")
    fun joinShop(): String {
        return "shop/joinShop"
    }

    @ResponseBody
    @GetMapping("/list")
    fun shopList(
        @RequestParam(name = "shopName") shopName: String,
    ): List<ShopDto.ListResponse> {
        return shopService.findListResponseListByNameContaining(shopName)
    }

    @GetMapping("/{shopId}")
    fun shopDetail(
        @PathVariable shopId: Long,
        model: Model,
    ): String {
        val shop = shopRepository.findByIdOrThrow(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))
        return "shop/shopDtail"
    }
}