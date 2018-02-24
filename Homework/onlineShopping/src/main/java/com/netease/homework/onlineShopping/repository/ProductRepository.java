package com.netease.homework.onlineShopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netease.homework.onlineShopping.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findById(Long id);
}
