package com.netease.homework.onlineShopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netease.homework.onlineShopping.domain.AccountItem;
import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.Product;

public interface AccountItemRepository extends JpaRepository<AccountItem, Long> {
	AccountItem findById(Long id);
	List<AccountItem> findByProduct(Product product);
	List<AccountItem> findByProductAndBuyer(Product product,Buyer buyer);
}
