package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Staff;
import com.heekng.celloct.entity.StaffId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, StaffId> {
}
