package com.heekng.celloct.service

import com.heekng.celloct.dto.WorkDto
import com.heekng.celloct.entity.Work
import com.heekng.celloct.entity.WorkTime
import com.heekng.celloct.repository.StaffRepository
import com.heekng.celloct.repository.WorkRepository
import com.heekng.celloct.util.fail
import com.heekng.celloct.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class WorkService(
    private val workRepository: WorkRepository,
    private val staffRepository: StaffRepository,
) {

    @Transactional
    fun addWork(addRequest: WorkDto.AddRequest): Long {
        val staff = staffRepository.findByMemberIdAndShopId(addRequest.memberId!!, addRequest.shopId!!) ?: fail()
        validateDuplicateWork(addRequest.workDate, staff.id!!)
        val work = Work(
            workTime = WorkTime(
                workDate = addRequest.workDate,
                startDate = addRequest.startDate,
                endDate = addRequest.endDate,
            ),
            note = addRequest.note,
            staff = staff,
        )
        workRepository.save(work)
        return work.id!!
    }

    @Transactional
    fun changeWorkTime(changeWorkTimeRequest: WorkDto.ChangeWorkTimeRequest) {
        val work = workRepository.findByIdOrThrow(changeWorkTimeRequest.workId)
        validateTime(changeWorkTimeRequest.changeStartDate, changeWorkTimeRequest.changeEndDate)
        work.workTime.changeWorkTime(changeWorkTimeRequest.changeStartDate, changeWorkTimeRequest.changeEndDate)
    }

    fun findWorkByFindWorkRequest(findWorkRequest: WorkDto.FindWorkRequest): List<Work> {
        val staff = staffRepository.findByShopIdAndId(findWorkRequest.shopId, findWorkRequest.staffId) ?: fail()
        val startDate = LocalDate.of(findWorkRequest.year, findWorkRequest.month, 1)
        val endDate = startDate.plusMonths(1).minusDays(1)
        return workRepository.findByWorkTimeWorkDateBetweenAndStaffId(startDate, endDate, staff.id!!)
    }

    fun validateExist(checkExistRequest: WorkDto.CheckExistRequest): Boolean {
        val staff =
            staffRepository.findByMemberIdAndShopId(checkExistRequest.memberId, checkExistRequest.shopId) ?: fail()
        try {
            validateDuplicateWork(checkExistRequest.workDate, staff.id!!)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    @Transactional
    fun updateWork(updateRequest: WorkDto.UpdateRequest) {
        val work = workRepository.findByIdAndStaffId(updateRequest.workId, updateRequest.staffId) ?: fail()
        work.updateWork(updateRequest.startDate, updateRequest.endDate, updateRequest.note)
    }

    @Transactional
    fun deleteWork(deleteRequest: WorkDto.DeleteRequest) {
        val work = workRepository.findByIdAndStaffId(deleteRequest.workId, deleteRequest.staffId) ?: fail()
        workRepository.delete(work)
    }

    private fun validateDuplicateWork(workDate: LocalDate, staffId: Long) {
        val findWorks = workRepository.findByWorkTimeWorkDateAndStaffId(workDate, staffId)
        if (findWorks.isNotEmpty()) fail()
    }

    private fun validateTime(changeStartDate: LocalDateTime, changeEndDate: LocalDateTime) {
        if (changeStartDate.isAfter(changeEndDate)) {
            throw IllegalStateException("근무 시작일은 근무 종료일 이전이여야 합니다.")
        }
    }
}