package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Authority;
import com.heekng.celloct.entity.AuthorityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityId> {
}
