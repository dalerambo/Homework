package com.netease.homework.onlineShopping.controller;

import com.netease.homework.onlineShopping.annotation.AuthorityEnum;
import com.netease.homework.onlineShopping.annotation.Authorization;
import com.netease.homework.onlineShopping.annotation.QueryTypeEnum;
import com.netease.homework.onlineShopping.domain.*;
import com.netease.homework.onlineShopping.exception.BusinessLogicException;
import com.netease.homework.onlineShopping.repository.AccountItemRepository;
import com.netease.homework.onlineShopping.repository.CartItemRepository;
import com.netease.homework.onlineShopping.repository.ProductRepository;
import com.netease.homework.onlineShopping.result.Result;
import com.netease.homework.onlineShopping.service.BusinessService;
import com.netease.homework.onlineShopping.util.ServiceInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ApiController {
    
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private AccountItemRepository accountItemRepository;
	
//	@Value("${uploadImagePath}")
//    private String uploadImagePath;
	
	@RequestMapping(value = "/login")
	@ResponseBody
    public Result login(HttpSession session, @RequestParam String userName, @RequestParam String password)
    {
		Result result;
		User user=businessService.getUser(userName, password);
		
		if(user!=null)
		{
			result=new Result(null,200,true);
			session.setAttribute("userId", user.getId());
		}
		else
		{
			result=new Result(null,200,false);
		}

        return result;
    }
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	@Authorization(authority = AuthorityEnum.Seller, queryType = QueryTypeEnum.Api)
	public Result delete(@RequestParam Long id) throws BusinessLogicException
    {
		Result result;

		Product product=productRepository.findById(id);
		businessService.validateProduct(product);
		businessService.validateIsSell(product);

		productRepository.delete(id);
		result=new Result(null,200,true);
		
        return result;
    }

	@RequestMapping(value = "/upload")
	@ResponseBody
	@Authorization(authority = AuthorityEnum.Seller, queryType = QueryTypeEnum.Api)
	public Result upload(HttpSession session, @RequestParam(value="file")MultipartFile file) throws IllegalStateException, IOException {
		Result result;

		Long userId=(Long)session.getAttribute("userId");


		String staticResoucePath=URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(),"UTF-8").substring(1)+"static/";
		String fileReletivePath= "images/"+userId+"/"+file.getOriginalFilename();
		String fileAbsPath =staticResoucePath + fileReletivePath;

//		String filePath = uploadImagePath+ "/" + file.getOriginalFilename();


		File desFile = new File(fileAbsPath);
		//如果父目录不存在则创建
		if(!desFile.getParentFile().exists())
		{
			desFile.getParentFile().mkdirs();
		}

		file.transferTo(desFile);

		result=new Result(null,200,"http://"+ServiceInfoUtil.getIp()+":"+ServiceInfoUtil.getPort()+"/"+fileReletivePath);

		return result;
	}


	//添加到购物车
	@RequestMapping(value = "/add")
	@ResponseBody
	@Authorization(authority = AuthorityEnum.Buyer, queryType = QueryTypeEnum.Api)
	public Result buy(HttpSession session, @RequestParam Long id, @RequestParam Integer num) throws BusinessLogicException
    {
		Result result;

		Buyer buyer=(Buyer) businessService.getUserFromSession(session);
    	Product product=productRepository.findById(id);
    	businessService.validateProduct(product);
    	businessService.validateIsBuy(buyer, product);
    	businessService.validateNum(num);

		CartItem  cartItem= cartItemRepository.findByProductAndCarter(product, buyer);
		if(cartItem==null)
		{
			cartItem=new CartItem(buyer,product,num);
		}
		else
		{
			cartItem.setNumber(cartItem.getNumber()+num);
		}
		cartItemRepository.save(cartItem);
		result=new Result(null,200,true);
    	
    	return result;
    	
    }
	
	@RequestMapping(value = "/updateItem")
	@ResponseBody
	@Authorization(authority = AuthorityEnum.Buyer, queryType = QueryTypeEnum.Api)
	public Result updateItem(HttpSession session, @RequestParam Long id, @RequestParam Integer num) throws BusinessLogicException
    {
		Result result;

		Long userId=(Long)session.getAttribute("userId");
		businessService.validateNum(num);

		CartItem  cartItem= cartItemRepository.findById(id);
		if(cartItem==null)
		{
			throw BusinessLogicException.CARTITEM_NOT_EXIST;
		}
		else if(!cartItem.getCarter().getId().equals(userId))
		{
			throw BusinessLogicException.CARTITEM_NOT_BELONG_TO_USER;
		}
		else
		{
			cartItem.setNumber(num);
		}
		cartItemRepository.save(cartItem);
		result=new Result(null,200,true);
    	
    	return result;
    	
    }
	
	@RequestMapping(value = "/deleteItem")
	@ResponseBody
	@Authorization(authority = AuthorityEnum.Buyer, queryType = QueryTypeEnum.Api)
	public Result deleteItem(HttpSession session, @RequestParam Long id) throws BusinessLogicException
    {
		Result result;

		Long userId=(Long)session.getAttribute("userId");
		
    	CartItem  cartItem= cartItemRepository.findById(id);
		if(cartItem==null)
		{
			throw BusinessLogicException.CARTITEM_NOT_EXIST;
		}
		else if(!cartItem.getCarter().getId().equals(userId))
		{
			throw BusinessLogicException.CARTITEM_NOT_BELONG_TO_USER;
		}
		else
		{
			cartItemRepository.delete(cartItem);
		}
		result=new Result(null,200,true);
    	
    	return result;
    	
    }
	
	
	@RequestMapping(value = "/buy")
	@ResponseBody
	@Authorization(authority = AuthorityEnum.Buyer, queryType = QueryTypeEnum.Api)
	@Transactional
	public Result buy(HttpSession session, @RequestBody List<Map<String,String>> data) throws BusinessLogicException
    {
		Result result;
		
		Long userId=(Long)session.getAttribute("userId");

		Buyer buyer=(Buyer)businessService.getUserFromSession(session);
		
		//这里要采用数据库事务！！！！！！
		for(Map<String,String> item:data)
		{
			Long id=Long.valueOf(item.get("id"));
			Integer num=Integer.valueOf(item.get("num"));
			CartItem cartItem = cartItemRepository.findById(id);
			if(!cartItem.getCarter().getId().equals(userId))//如果item不是该买家的，则返回异常
			{
            	throw BusinessLogicException.REQUEST_PARAM_ERROR;
			}
			AccountItem accountItem=new AccountItem();
			accountItem.setBuyer(buyer);
			accountItem.setBuyPrice(cartItem.getProduct().getPrice());
			accountItem.setNumber(num);
			accountItem.setProduct(cartItem.getProduct());
			accountItemRepository.save(accountItem);
			cartItemRepository.delete(cartItem);
		}
		
		
		result=new Result(null,200,true);
    	
    	return result;
    	
    }
}
