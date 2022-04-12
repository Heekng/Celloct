package com.heekng.celloct.service;

import com.heekng.celloct.entity.Work;
import com.heekng.celloct.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;

    @Transactional
    public Long addWork(Work work) {
        validateDuplicateWork(work);
        workRepository.save(work);
        return work.getId();
    }

    @Transactional
    public void changeWorkTime(Long workId, LocalDateTime changeStartDate, LocalDateTime changeEndDate) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new IllegalStateException("존재하지 않는 근무입니다."));
        validateTime(changeStartDate, changeEndDate);
        work.changeWorkTime(changeStartDate, changeEndDate);
    }

    @Transactional
    public void deleteWork(Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new IllegalStateException("존재하지 않는 근무입니다."));
        workRepository.delete(work);
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

    private void validateDuplicateWork(Work work) {
        List<Work> findWorks = workRepository.findByWorkDateAndStaffId(work.getWorkDate(), work.getStaff().getId());
        if (!findWorks.isEmpty()) {
            throw new IllegalStateException("이미 근무한 날짜입니다.");
        }
    }
}
