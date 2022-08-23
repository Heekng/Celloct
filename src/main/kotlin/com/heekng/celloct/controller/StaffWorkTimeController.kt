package com.heekng.celloct.controller

import com.heekng.celloct.annotation.RoleCheck
import com.heekng.celloct.annotation.enums.UserType
import com.heekng.celloct.config.oauth.dto.SessionMember
import com.heekng.celloct.dto.ShopDto
import com.heekng.celloct.dto.WorkDto
import com.heekng.celloct.dto.WorkUpdateRequestDto
import com.heekng.celloct.repository.ShopRepository
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.repository.WorkRepository
import com.heekng.celloct.service.WorkService
import com.heekng.celloct.service.WorkUpdateRequestService
import com.heekng.celloct.util.findByIdOrThrow
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/staff/{shopId}")
class StaffWorkTimeController(
    private val httpSession: HttpSession,
    private val staffRepository: StaffRepository,
    private val shopRepository: ShopRepository,
    private val workRepository: WorkRepository,
    private val workService: WorkService,
    private val workUpdateRequestService: WorkUpdateRequestService,
) {

    @GetMapping("/workTime/insert")
    @RoleCheck(UserType.STAFF)
    fun insertWorkTime(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val shop = shopRepository.findByIdOrThrow(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))

        return "staff/workTimeInsert"
    }

    @GetMapping("/workTime/validateExist")
    @ResponseBody
    @RoleCheck(UserType.STAFF, true)
    fun checkExist(
        @PathVariable("shopId") shopId: Long,
        checkExistRequest: WorkDto.CheckExistRequest,
    ): Boolean {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?

        checkExistRequest.memberId = sessionMember!!.id
        checkExistRequest.shopId = shopId

        return workService.validateExist(checkExistRequest)
    }

    @PostMapping("/workTime/insert")
    @RoleCheck(UserType.STAFF)
    fun doInsertWorkTime(
        @PathVariable("shopId") shopId: Long,
        addRequest: WorkDto.AddRequest,
    ): String {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?

        addRequest.memberId = sessionMember!!.id
        addRequest.shopId = shopId
        workService.addWork(addRequest)
        return "redirect:/"
    }

    @GetMapping("/workTimes")
    @RoleCheck(UserType.STAFF)
    fun workTimes(
        @PathVariable("shopId") shopId: Long,
        model: Model,
    ): String {
        val shop = shopRepository.findByIdOrThrow(shopId)
        model.addAttribute("shop", ShopDto.ShopDetailResponse(shop))

        return "staff/workTime"
    }

    @GetMapping("/workTimes/search")
    @ResponseBody
    @RoleCheck(UserType.STAFF, true)
    fun findWorks(
        @PathVariable("shopId") shopId: Long,
        @RequestParam("year") year: Int,
        @RequestParam("month") month: Int,
    ): List<WorkDto.WorkResponse> {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        val staff = staffRepository.findOneQ(
            memberId = sessionMember!!.id,
            shopId = shopId,
        )

        val findWorkRequest = WorkDto.FindWorkRequest(
            staffId = staff!!.id!!,
            shopId = shopId,
            year = year,
            month = month,
        )
        return workService.findWorkByFindWorkRequest(findWorkRequest)
            .map { work -> WorkDto.WorkResponse(work) }
    }

    @GetMapping("/workTimes/{workId}")
    @ResponseBody
    @RoleCheck(UserType.STAFF, true)
    fun workDetail(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("workId") workId: Long,
    ): WorkDto.WorkDetailResponse {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        val staff = staffRepository.findOneQ(
            memberId = sessionMember!!.id,
            shopId = shopId,
        )

        val work = workRepository.findByIdAndStaffId(workId, staff!!.id!!)
            ?: throw IllegalStateException("존재하지 않는 근무입니다.")
        return WorkDto.WorkDetailResponse(work)
    }

    @Transactional
    @PostMapping("/workTimes/{workId}/update")
    @ResponseBody
    @RoleCheck(UserType.STAFF, true)
    fun updateRequest(
        @PathVariable("shopId") shopId: Long,
        @PathVariable("workId") workId: Long,
        @RequestBody addRequest: WorkUpdateRequestDto.AddRequest,
    ): Boolean {
        val sessionMember = httpSession.getAttribute("member") as SessionMember?
        val staff = staffRepository.findOneQ(
            memberId = sessionMember!!.id,
            shopId = shopId
        )

        addRequest.workId = workId
        addRequest.staffId = staff!!.id!!
        workUpdateRequestService.addWorkUpdateRequest(addRequest)
        return true
    }

}