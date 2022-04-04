package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Work;
import com.heekng.celloct.entity.WorkId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, WorkId> {
}
