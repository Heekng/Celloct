package com.heekng.celloct.service

import com.heekng.celloct.dto.WorkUpdateRequestDto
import com.heekng.celloct.entity.WorkTime
import com.heekng.celloct.entity.WorkUpdateRequest
import com.heekng.celloct.repository.WorkRepository
import com.heekng.celloct.repository.WorkUpdateRequestRepository
import com.heekng.celloct.util.fail
import com.heekng.celloct.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class WorkUpdateRequestService(
    private val workUpdateRequestRepository: WorkUpdateRequestRepository,
    private val workRepository: WorkRepository,
) {

    fun addWorkUpdateRequest(addRequest: WorkUpdateRequestDto.AddRequest): Long {
        val work = workRepository.findByIdAndStaffId(addRequest.workId, addRequest.staffId) ?: fail()
        validateWorkUpdateRequest(addRequest.workId)
        val workUpdateRequest = WorkUpdateRequest(
            workTime = WorkTime(
                workDate = addRequest.workDate,
                startDate = addRequest.startDate,
                endDate = addRequest.endDate,
            ),
            work = work,
            note = addRequest.note,
        )
        workUpdateRequestRepository.save(workUpdateRequest)
        work.addWorkUpdateRequest(workUpdateRequest)
        return workUpdateRequest.id!!
    }

    //근무시간 변경 요청 취소
    fun deleteWorkUpdateRequest(workUpdateRequestId: Long) {
        val workUpdateRequest = workUpdateRequestRepository.findByIdOrThrow(workUpdateRequestId)
        workUpdateRequest.work.deleteWorkUpdateRequest()
        workUpdateRequestRepository.delete(workUpdateRequest)
    }

    //근무시간 변경 요청 수정
    fun updateWorkUpdateRequest(updateRequest: WorkUpdateRequestDto.UpdateRequest) {
        val workUpdateRequest = workUpdateRequestRepository.findByIdOrThrow(updateRequest.workUpdateRequestId)
        validateTime(updateRequest.updateStartDate, updateRequest.updateEndDate)
        workUpdateRequest.workTime.changeWorkTime(updateRequest.updateStartDate, updateRequest.updateEndDate)
    }

    private fun validateWorkUpdateRequest(workId: Long) {
        val findWorkUpdateRequests = workUpdateRequestRepository.findByWorkId(workId)
        if (findWorkUpdateRequests.isNotEmpty()) {
            throw IllegalStateException("이미 근무시간 변경 요청이 존재합니다.")
        }
    }

    private fun validateTime(changeStartDate: LocalDateTime, changeEndDate: LocalDateTime) {
        if (changeStartDate.isAfter(changeEndDate)) {
            throw IllegalStateException("근무 시작일은 근무 종료일보다 이전이여야 합니다.")
        }
    }
}