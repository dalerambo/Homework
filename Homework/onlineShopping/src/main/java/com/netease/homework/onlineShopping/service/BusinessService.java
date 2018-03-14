package com.netease.homework.onlineShopping.service;

import com.netease.homework.onlineShopping.domain.AccountItem;
import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.Product;
import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.exception.BusinessLogicException;
import com.netease.homework.onlineShopping.repository.AccountItemRepository;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class BusinessService {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private BuyerRepository buyerRepository;

	@Autowired
	private AccountItemRepository accountItemRepository;

	// 根据用户名密码查找用户
	public User getUser(String username, String password) {
		User user = sellerRepository.findByUsernameAndPassword(username, password);
		if (user == null)
			user = buyerRepository.findByUsernameAndPassword(username, password);
		return user;
	}

	// 根据id查找指定用户
	public User getUser(Long userId) {
		User user = null;
		if (userId != null) {
			user = sellerRepository.findById(userId);
			if (user == null)
				user = buyerRepository.findById(userId);
		}
		return user;
	}

	//从session中获得userId并获得user
	public User getUserFromSession(HttpSession session)
	{
		Long userId=(Long)session.getAttribute("userId");
		return getUser(userId);
	}

	// 货物是否已经被指定买家购买
	public boolean isBuy(Buyer buyer, Product product) {
		// return !accountItemRepository.findByProductAndBuyer(product,
		// buyer).isEmpty();

		return accountItemRepository.findByProductAndBuyer(product, buyer) != null;
	}

	// 货物是否已经被卖出
	public boolean isSell(Product product) {
		return !accountItemRepository.findByProduct(product).isEmpty();
	}

	// 获取已经出售的数量
	public Integer getSellNumber(Product product) {
		int num = 0;
		List<AccountItem> items = accountItemRepository.findByProduct(product);
		if (items != null) {
			for (AccountItem item : items) {
				num += item.getNumber();
			}
		}

		return num;
	}

	public void validateProduct(Product product) throws BusinessLogicException {
		if (product == null) {
			throw BusinessLogicException.PRODUCT_NOT_EXIST;
		}
	}

	public void validateIsBuy(Buyer buyer, Product product) throws BusinessLogicException {
		if (isBuy(buyer, product)) {
			throw BusinessLogicException.PRODUCT_ALREADY_BOUGHT;
		}
	}
	
	public void validateIsSell(Product product) throws BusinessLogicException {
		if (isSell(product)) {
			throw BusinessLogicException.PRODUCT_SOLD_OUT;
		}
	}

	public void validateNum(Integer num) throws BusinessLogicException {
		if (num <= 0) {
			throw BusinessLogicException.NUM_MUST_POSITIVE;
		}
	}
}
