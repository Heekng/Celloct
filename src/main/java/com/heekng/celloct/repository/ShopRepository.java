package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByName(String name);

    List<Shop> findListByNameContaining(String name);
}
