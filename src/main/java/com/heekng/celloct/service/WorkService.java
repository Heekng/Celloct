package com.heekng.celloct.service;

import com.heekng.celloct.dto.WorkDto;
import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.entity.Work;
import com.heekng.celloct.repository.StaffRepository;
import com.heekng.celloct.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;
    private final StaffRepository staffRepository;

    @Transactional
    public Long addWork(WorkDto.AddRequest addRequest) {
        Staff staff = staffRepository.findByMemberIdAndShopId(addRequest.getMemberId(), addRequest.getShopId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 직원입니다."));
        validateDuplicateWork(addRequest.getWorkDate(), staff.getId());
        Work work = Work.builder()
                .staff(staff)
                .workDate(addRequest.getWorkDate())
                .startDate(addRequest.getStartDate())
                .endDate(addRequest.getEndDate())
                .note(addRequest.getNote())
                .build();
        workRepository.save(work);
        return work.getId();
    }

    @Transactional
    public void changeWorkTime(WorkDto.ChangeWorkTimeRequest changeWorkTimeRequest) {
        Work work = workRepository.findById(changeWorkTimeRequest.getWorkId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 근무입니다."));
        validateTime(changeWorkTimeRequest.getChangeStartDate(), changeWorkTimeRequest.getChangeEndDate());
        work.getWorkTime().changeWorkTime(changeWorkTimeRequest.getChangeStartDate(), changeWorkTimeRequest.getChangeEndDate());
    }

    @Transactional
    public void deleteWork(Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(() -> new IllegalStateException("존재하지 않는 근무입니다."));
        workRepository.delete(work);
    }

    public Boolean validateExist(WorkDto.CheckExistRequest checkExistRequest) {
        Staff staff = staffRepository.findByMemberIdAndShopId(checkExistRequest.getMemberId(), checkExistRequest.getShopId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 직원입니다."));
        try {
            validateDuplicateWork(checkExistRequest.getWorkDate(), staff.getId());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void findWorkByMemberIdAndYearMonth(WorkDto.FindWorkRequest findWorkRequest) {

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

    private void validateDuplicateWork(LocalDate workDate, Long staffId) {
        List<Work> findWorks = workRepository.findByWorkTimeWorkDateAndStaffId(workDate, staffId);
        if (!findWorks.isEmpty()) {
            throw new IllegalStateException("이미 근무한 날짜입니다.");
        }
    }
}
