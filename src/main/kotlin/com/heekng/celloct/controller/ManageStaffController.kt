package com.heekng.celloct.controller

import com.heekng.celloct.annotation.RoleCheck
import com.heekng.celloct.annotation.enums.UserType
import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.dto.ManagerDto
import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.dto.StaffDto
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.service.ManagerService
import com.heekng.celloct.service.ShopService
import com.heekng.celloct.service.StaffService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/manage")
class ManageStaffController(
    private val httpSession: HttpSession,
    private val staffService: StaffService,
    private val shopService: ShopService,
    private val staffRepository: StaffRepository,
    private val managerRepository: ManagerRepository,
    private val managerService: ManagerService,
) {


    @GetMapping("/{shopId}/staff")
    @RoleCheck(UserType.MANAGER)
    fun staffList(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val managers = managerRepository.findListByShopId(shopId)
            .map { manager -> ManagerDto.ManagerResponse(manager) }
        model.addAttribute("managers", managers)

        val staffs = staffRepository.findByShopId(shopId)
            .map { staff -> StaffDto.StaffResponse(staff) }
        model.addAttribute("staffs", staffs)

        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))

        return "manager/shopStaff"
    }

    @GetMapping("/{shopId}/manager/{managerId}")
    @RoleCheck(UserType.MANAGER)
    fun managerDetail(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("managerId") managerId: Long,
        model: Model,
    ): String {
        val findManager = managerRepository.findWithMemberById(managerId)
        model.addAttribute("manager", ManagerDto.WithMemberResponse(findManager))

        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))

        return "manager/managerDetail"
    }

    @GetMapping("/{shopId}/manager/{managerId}/update")
    @RoleCheck(UserType.MANAGER)
    fun managerDetailUpdate(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("managerId") managerId: Long,
        model: Model,
    ): String {
        val findManager = managerRepository.findWithMemberById(managerId)
        model.addAttribute("manager", ManagerDto.WithMemberResponse(findManager))

        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))

        return "manager/managerDetailUpdate"
    }

    @PostMapping("/{shopId}/manager/{managerId}/update")
    @RoleCheck(UserType.MANAGER)
    fun doManagerDetailUpdate(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("managerId") managerId: Long,
        updateRequest: ManagerDto.UpdateRequest,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): String {
        updateRequest.id = managerId
        managerService.updateManager(updateRequest)

        redirectAttributes.addAttribute("managerId", managerId)
        redirectAttributes.addAttribute("shopId", shopId)

        return "redirect:/manage/{shopId}/manager/{managerId}"
    }

    @PostMapping("/{shopId}/manager/{managerId}/delete")
    @ResponseBody
    @RoleCheck(UserType.MANAGER, true)
    fun managerDelete(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("managerId") managerId: Long,
        model: Model,
    ): Boolean {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?

        val deleteRequest = ManagerDto.DeleteRequest(
            managerId = managerId,
            shopId = shopId,
            memberId = sessionMember!!.id
        )
        managerService.deleteManager(deleteRequest)
        return true;
    }

    @GetMapping("/{shopId}/staff/{staffId}")
    @RoleCheck(UserType.MANAGER)
    fun staffDetail(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("staffId") staffId: Long,
        model: Model,
    ): String {
        val findStaff = staffRepository.findWithMemberById(staffId)
        model.addAttribute("staff", StaffDto.WithMemberResponse(findStaff))

        val findShop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(findShop))

        return "manager/staffDetail"
    }

    @PostMapping("/{shopId}/staff/{staffId}/delete")
    @ResponseBody
    @RoleCheck(UserType.MANAGER, true)
    fun staffDelete(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("staffId") staffId: Long,
    ): Boolean {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?

        val deleteRequest = StaffDto.DeleteRequest(
            staffId = staffId,
            shopId = shopId,
            memberId = sessionMember!!.id
        )
        staffService.deleteStaff(deleteRequest)
        return true;
    }

    @GetMapping("/{shopId}/staff/{staffId}/update")
    @RoleCheck(UserType.MANAGER)
    fun staffDetailUpdate(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("staffId") staffId: Long,
        model: Model,
    ): String {
        val findStaff = staffRepository.findWithMemberById(staffId)
        model.addAttribute("staff", StaffDto.WithMemberResponse(findStaff))

        val findShop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(findShop))

        return "manager/staffDetailUpdate"
    }

    @PostMapping("/{shopId}/staff/{staffId}/update")
    @RoleCheck(UserType.MANAGER)
    fun doStaffDetailUpdate(
        updateRequest: StaffDto.UpdateRequest,
        @PathVariable("shopId") shopId: Long,
        @PathVariable("staffId") staffId: Long,
        redirectAttributes: RedirectAttributes,
    ): String {
        updateRequest.staffId = staffId
        updateRequest.shopId = shopId
        staffService.updateStaff(updateRequest)

        redirectAttributes.addAttribute("staffId", staffId)
        redirectAttributes.addAttribute("shopId", shopId)

        return "redirect:/manage/{shopId}/staff/{staffId}"
    }

    @PostMapping("/{shopId}/staff/{staffId}/addManager")
    @ResponseBody
    @RoleCheck(UserType.MANAGER, true)
    fun addManager(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("staffId") staffId: Long,
    ): ManagerDto.AddByStaffResponse {

        val staff = staffRepository.findByShopIdAndId(shopId, staffId)
            ?: throw IllegalStateException("해당 매장의 직원이 아닙니다.")

        val addByStaffRequest = ManagerDto.AddByStaffRequest(shopId, staffId)
        val savedManager = managerService.addByStaff(addByStaffRequest)
        return ManagerDto.AddByStaffResponse(savedManager)
    }

}