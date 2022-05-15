package com.heekng.celloct.service;

import com.heekng.celloct.dto.WorkUpdateRequestDto;
import com.heekng.celloct.entity.Work;
import com.heekng.celloct.entity.WorkUpdateRequest;
import com.heekng.celloct.repository.WorkRepository;
import com.heekng.celloct.repository.WorkUpdateRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkUpdateRequestService {

    private final WorkUpdateRequestRepository workUpdateRequestRepository;
    private final WorkRepository workRepository;

    /**
     * 근무시간 변경 요청
     * @param addRequest
     * @return
     */
    public Long addWorkUpdateRequest(WorkUpdateRequestDto.AddRequest addRequest) {
        Work work = workRepository.findByIdAndStaffId(addRequest.getWorkId(), addRequest.getStaffId()).orElseThrow(() -> new IllegalStateException("해당 직원의 근무가 아닙니다."));
        validateWorkUpdateRequest(addRequest.getWorkId());
        WorkUpdateRequest workUpdateRequest = WorkUpdateRequest.builder()
                .workDate(addRequest.getWorkDate())
                .startDate(addRequest.getStartDate())
                .endDate(addRequest.getEndDate())
                .work(work)
                .note(addRequest.getNote())
                .build();
        workUpdateRequestRepository.save(workUpdateRequest);
        work.addWorkUpdateRequest(workUpdateRequest);
        return workUpdateRequest.getId();
    }

    /**
     * 근무시간 변경 요청 취소
     * @param workUpdateRequestId
     */
    public void deleteWorkUpdateRequest(Long workUpdateRequestId) {
        WorkUpdateRequest workUpdateRequest = workUpdateRequestRepository.findById(workUpdateRequestId).orElseThrow(() -> new IllegalStateException("존재하지 않는 근무시간 변경 요청입니다."));
        workUpdateRequest.getWork().deleteWorkUpdateRequest();
        workUpdateRequestRepository.delete(workUpdateRequest);
    }

    /**
     * 근무시간 변경 요청 수정
     * @param updateRequest
     */
    public void updateWorkUpdateRequest(WorkUpdateRequestDto.updateRequest updateRequest) {
        WorkUpdateRequest workUpdateRequest = workUpdateRequestRepository.findById(updateRequest.getWorkUpdateRequestId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 근무시간 변경 요청입니다."));
        validateTime(updateRequest.getUpdateStartDate(), updateRequest.getUpdateEndDate());
        workUpdateRequest.getWorkTime().changeWorkTime(updateRequest.getUpdateStartDate(), updateRequest.getUpdateEndDate());
    }

    private void validateWorkUpdateRequest(Long workId) {
        List<WorkUpdateRequest> findWorkUpdateRequests = workUpdateRequestRepository.findByWorkId(workId);
        if (!findWorkUpdateRequests.isEmpty()) {
            throw new IllegalStateException("이미 근무시간 변경 요청이 존재합니다.");
        }
    }

    /**
     * 근무 시작시간이 근무 종료시간보다 이전이어야 한다.
     * @param changeStartDate
     * @param changeEndDate
     */
    private void validateTime(LocalDateTime changeStartDate, LocalDateTime changeEndDate) {
        if (changeStartDate.isAfter(changeEndDate)) {
            throw new IllegalStateException("근무 시작일은 근무 종료일보다 이전이여야 합니다.");
        }
    }
}
