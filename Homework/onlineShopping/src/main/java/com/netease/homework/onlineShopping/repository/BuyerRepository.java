package com.netease.homework.onlineShopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netease.homework.onlineShopping.domain.Buyer;


public interface BuyerRepository extends JpaRepository<Buyer, Long> {
	Buyer findById(Long id);
	Buyer findByUsername(String username);
	Buyer findByUsernameAndPassword(String username,String password);
}
