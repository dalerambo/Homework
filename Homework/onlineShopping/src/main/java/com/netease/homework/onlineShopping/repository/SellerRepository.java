package com.netease.homework.onlineShopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netease.homework.onlineShopping.domain.Seller;


public interface SellerRepository extends JpaRepository<Seller, Long> {
	Seller findById(Long id);
	Seller findByUsername(String username);
	Seller findByUsernameAndPassword(String username,String password);
}
