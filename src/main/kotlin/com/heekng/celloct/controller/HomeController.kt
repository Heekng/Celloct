package com.heekng.celloct.controller

import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.entity.Manager
import com.heekng.celloct.entity.Staff
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.repository.StaffRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpSession

@Controller
class HomeController(
    private val httpSession: HttpSession,
    private val managerRepository: ManagerRepository,
    private val staffRepository: StaffRepository,
) {
    @GetMapping("/")
    fun home(model: Model): String {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?

        if (sessionMember != null) {
            val managerShops = managerRepository.findWithShopByMemberId(sessionMember.id)
                .map(Manager::shop)
                .map { shop -> ShopDto.HomeShopManagerResponse(shop) }
            model.addAttribute("manageShops", managerShops)

            val staffShops = staffRepository.findWithShopByMemberId(sessionMember.id)
                .map(Staff::shop)
                .map { shop -> ShopDto.HomeShopStaffResponse(shop) }
            model.addAttribute("staffShops", staffShops)
        } else {
            model.addAttribute("manageShops", null)
            model.addAttribute("staffShops", null)
        }

        return "home"
    }

    @GetMapping("/index")
    fun index(model: Model): String {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?

        if (sessionMember != null) {
            model.addAttribute("memberName", sessionMember.name)
        }

        return "index"
    }
}