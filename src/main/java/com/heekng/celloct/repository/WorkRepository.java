package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByWorkDateAndStaffId(LocalDate workDate, Long staffId);
}
