package com.netease.homework.onlineShopping.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.netease.homework.onlineShopping.util.ServiceInfoUtil;
import com.netease.homework.onlineShopping.domain.AccountItem;
import com.netease.homework.onlineShopping.domain.BusinessLogicException;
import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.CartItem;
import com.netease.homework.onlineShopping.domain.Product;
import com.netease.homework.onlineShopping.domain.Result;
import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.repository.AccountItemRepository;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.CartItemRepository;
import com.netease.homework.onlineShopping.repository.ProductRepository;
import com.netease.homework.onlineShopping.service.ApiService;

@Controller
@RequestMapping("/api")
public class ApiController {
    
	@Autowired
	private ApiService apiService;
	
    @Autowired
	private BuyerRepository buyerRepository;
	
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
    public Result login(ModelAndView modelAndView, HttpSession session, @RequestParam String userName, @RequestParam String password)
    {
		Result result;
		User user=apiService.getUser(userName, password);
		
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
	public Result delete(ModelAndView modelAndView, HttpSession session, @RequestParam Long id) throws BusinessLogicException
    {
		Result result;
		
		Long userId=(Long)session.getAttribute("userId");
		User user=apiService.validateAndGetUser(userId);
		apiService.validateSellerPrivilege(user);
		Product product=productRepository.findById(id);
		apiService.validateProduct(product);
		apiService.validateIsSell(product);

		productRepository.delete(id);
		result=new Result(null,200,true);
		
        return result;
    }
	
	//添加到购物车
	@RequestMapping(value = "/add")
	@ResponseBody
	public Result buy(ModelAndView modelAndView, HttpSession session, @RequestParam Long id, @RequestParam Integer num) throws BusinessLogicException
    {
		Result result;

		Long userId=(Long)session.getAttribute("userId");
		User user=apiService.validateAndGetUser(userId);
		Buyer buyer=apiService.validateBuyerPrivilege(user);
    	Product product=productRepository.findById(id);
    	apiService.validateProduct(product);
    	apiService.validateIsBuy(buyer, product);
    	apiService.validateNum(num);

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
	public Result updateItem(ModelAndView modelAndView, HttpSession session, @RequestParam Long id, @RequestParam Integer num) throws BusinessLogicException
    {
		Result result;

		Long userId=(Long)session.getAttribute("userId");
		User user=apiService.validateAndGetUser(userId);
		apiService.validateBuyerPrivilege(user);
		apiService.validateNum(num);

		CartItem  cartItem= cartItemRepository.findById(id);
		if(cartItem==null)
		{
			throw new BusinessLogicException("购物车项目不存在！",417);
		}
		else if(cartItem.getCarter().getId()!=userId)
		{
			throw new BusinessLogicException("购物车项目不属于该用户！",417);
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
	public Result deleteItem(ModelAndView modelAndView, HttpSession session, @RequestParam Long id) throws BusinessLogicException
    {
		Result result;

		Long userId=(Long)session.getAttribute("userId");
		
		User user=apiService.validateAndGetUser(userId);
		
		apiService.validateBuyerPrivilege(user);
		
    	CartItem  cartItem= cartItemRepository.findById(id);
		if(cartItem==null)
		{
			throw new BusinessLogicException("购物车项目不存在！",417);
		}
		else if(cartItem.getCarter().getId()!=userId)
		{
			throw new BusinessLogicException("购物车项目不属于该用户！",417);
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
	public Result buy(ModelAndView modelAndView, HttpSession session, @RequestBody List<Map<String,String>> data) throws BusinessLogicException
    {
		Result result;
		
		Long userId=(Long)session.getAttribute("userId");
		
		User user=apiService.validateAndGetUser(userId);
		
		Buyer buyer=apiService.validateBuyerPrivilege(user);
		
		//这里要采用数据库事务！！！！！！
		for(Map<String,String> item:data)
		{
			Long id=Long.valueOf(item.get("id"));
			Integer num=Integer.valueOf(item.get("num"));
			CartItem cartItem = cartItemRepository.findById(id);
			if(cartItem.getCarter().getId()!=userId)//如果item不是该买家的，则返回异常
			{
            	throw new BusinessLogicException("请求参数有误",417);
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
	
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Result upload(ModelAndView modelAndView, HttpSession session, @RequestParam(value="file")MultipartFile file) throws BusinessLogicException, IllegalStateException, IOException
    {
		Result result;
		
		Long userId=(Long)session.getAttribute("userId");
		
		User user=apiService.validateAndGetUser(userId);
		apiService.validateSellerPrivilege(user);
		
		
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

	@ExceptionHandler({BusinessLogicException.class,Exception.class})
	@ResponseBody
	public Result handleException(Exception e) {
		Result result;
		if(e instanceof BusinessLogicException)
		{
			BusinessLogicException err=(BusinessLogicException) e;
			result=new Result(err.getMessage(),err.getCode());
		}
		else
		{
			result=new Result(e.getMessage(),400);
		}
	    return result;
	}
	
//	@ExceptionHandler(BusinessLogicException.class)
//	@ResponseBody
//	public Result handleException(BusinessLogicException e) {
//
//	    return new Result(e.getMessage(),e.getCode());
//	}
	
}
