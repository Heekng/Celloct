package com.heekng.celloct.repository;

import com.heekng.celloct.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findByName(String name);
}
