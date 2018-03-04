package com.netease.homework.onlineShopping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.homework.onlineShopping.domain.AccountItem;
import com.netease.homework.onlineShopping.domain.BusinessLogicException;
import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.Product;
import com.netease.homework.onlineShopping.domain.Seller;
import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.repository.AccountItemRepository;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.SellerRepository;

@Service
public class ApiService {

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

	public User validateAndGetUser(Long userId) throws BusinessLogicException {
		if (userId == null) {
			throw new BusinessLogicException("请登录！", 417);
		} else {
			return getUser(userId);
		}
	}

	public void validateSellerPrivilege(User user) throws BusinessLogicException {
		if (user == null || user.getUsertype() != 0) {
			throw new BusinessLogicException("非卖家用户无权限！", 417);
		}
	}

	public Buyer validateBuyerPrivilege(User user) throws BusinessLogicException {
		if (user == null || user.getUsertype() != 1) {
			throw new BusinessLogicException("非买家用户无权限！", 417);
		} else {
			return (Buyer) user;
		}
	}

	public void validateProduct(Product product) throws BusinessLogicException {
		if (product == null) {
			throw new BusinessLogicException("商品不存在！", 417);
		}
	}

	public void validateIsBuy(Buyer buyer, Product product) throws BusinessLogicException {
		if (isBuy(buyer, product)) {
			throw new BusinessLogicException("商品已购买！", 417);
		}
	}
	
	public void validateIsSell(Product product) throws BusinessLogicException {
		if (isSell(product)) {
			throw new BusinessLogicException("商品出售！", 417);
		}
	}

	public void validateNum(Integer num) throws BusinessLogicException {
		if (num <= 0) {
			throw new BusinessLogicException("数量必须大于0！", 417);
		}
	}
}
