package com.heekng.celloct.controller

import com.heekng.celloct.annotation.RoleCheck
import com.heekng.celloct.annotation.enums.UserType
import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.dto.StaffDto
import com.heekng.celloct.dto.WorkDto
import com.heekng.celloct.dto.WorkUpdateRequestDto
import com.heekng.celloct.repository.ManagerRepository
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.repository.WorkRepository
import com.heekng.celloct.service.ShopService
import com.heekng.celloct.service.WorkService
import com.heekng.celloct.service.WorkUpdateRequestService
import com.heekng.celloct.util.findByIdOrThrow
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/manage/{shopId}")
class ManageWorkController(
    private val httpSession: HttpSession,
    private val managerRepository: ManagerRepository,
    private val shopService: ShopService,
    private val staffRepository: StaffRepository,
    private val workService: WorkService,
    private val workRepository: WorkRepository,
    private val workUpdateRequestService: WorkUpdateRequestService,
) {


    @GetMapping("/workTimes")
    @RoleCheck(UserType.MANAGER)
    fun workTimes(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val staffs = staffRepository.find(shopId = shopId)
            .map { staff -> StaffDto.StaffResponse(staff) }
        model.addAttribute("staffs", staffs)

        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))

        return "manager/workTime"
    }

    @GetMapping("/workTimes/{staffId}/{year}/{month}")
    @ResponseBody
    @RoleCheck(UserType.MANAGER, true)
    fun findWork(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("staffId") staffId: Long,
        @PathVariable("year") year: Int,
        @PathVariable("month") month: Int,
    ): List<WorkDto.WorkResponse> {
        val findWorkRequest = WorkDto.FindWorkRequest(
            staffId = staffId,
            shopId = shopId,
            year = year,
            month = month
        )
        return workService.findWorkByFindWorkRequest(findWorkRequest)
            .map { work -> WorkDto.WorkResponse(work) }
    }

    @GetMapping("/workTimes/{staffId}")
    @ResponseBody
    @RoleCheck(UserType.MANAGER, true)
    fun workDetail(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("staffId") staffId: Long,
        @RequestParam("workId") workId: Long,
    ): WorkDto.WorkDetailResponse {
        staffRepository.findByShopIdAndId(shopId, staffId)
            ?: throw IllegalStateException("존재하지 않는 직원입니다.")

        val work = workRepository.findByIdOrThrow(workId)
        return WorkDto.WorkDetailResponse(work)
    }

    @PostMapping("/workTimes/{staffId}/update")
    @ResponseBody
    @RoleCheck(UserType.MANAGER, true)
    fun workUpdate(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("staffId") staffId: Long,
        @RequestBody updateRequest: WorkDto.UpdateRequest,
    ): Boolean {
        staffRepository.findByShopIdAndId(shopId, staffId)
            ?: throw IllegalStateException("존재하지 않는 직원입니다.")
        updateRequest.staffId = staffId

        workService.updateWork(updateRequest)
        return true;
    }

    @PostMapping("/workTimes/{staffId}/delete")
    @ResponseBody
    @RoleCheck(UserType.MANAGER, true)
    fun workUpdate(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("staffId") staffId: Long,
        @RequestBody deleteRequest: WorkDto.DeleteRequest,
    ): Boolean {
        staffRepository.findByShopIdAndId(shopId, staffId)
            ?: throw IllegalStateException("존재하지 않는 직원입니다.")

        deleteRequest.staffId = staffId
        workService.deleteWork(deleteRequest)
        return true;
    }

    @GetMapping("/updateWorkTime")
    @RoleCheck(UserType.MANAGER, false)
    fun updateWorkTime(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val shop = shopService.findShop(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))
        return "manager/workUpdate"
    }

    @GetMapping("/updateWorkTimes")
    @RoleCheck(UserType.MANAGER, isRest = true)
    @ResponseBody
    fun updateWorkTimes(
        @PathVariable("shopId") shopId: Long,
    ): List<WorkUpdateRequestDto.WorkUpdateRequestListResponse> {
        return workUpdateRequestService.findByShopId(shopId).map {
            WorkUpdateRequestDto.WorkUpdateRequestListResponse(it)
        }
    }
}