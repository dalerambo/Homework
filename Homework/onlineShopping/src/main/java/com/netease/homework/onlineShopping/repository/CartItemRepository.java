package com.netease.homework.onlineShopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.CartItem;
import com.netease.homework.onlineShopping.domain.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	CartItem findById(Long id);
	CartItem findByProductAndCarter(Product product,Buyer carter);
}
