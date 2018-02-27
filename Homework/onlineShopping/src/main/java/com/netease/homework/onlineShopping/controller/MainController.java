package com.netease.homework.onlineShopping.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netease.homework.onlineShopping.domain.Product;
import com.netease.homework.onlineShopping.domain.ProductView;
import com.netease.homework.onlineShopping.domain.Seller;
import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.Item;
import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.AccountItemRepository;
import com.netease.homework.onlineShopping.repository.ProductRepository;
import com.netease.homework.onlineShopping.repository.SellerRepository;
import com.netease.homework.onlineShopping.service.ApiService;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private SellerRepository sellerRepository;
	
    @Autowired
	private BuyerRepository buyerRepository;
    
    @Autowired
	private ProductRepository productRepository;
    
    @Autowired
    private ApiService apiService;
    
	@RequestMapping(value = "/")
    public ModelAndView index(ModelAndView modelAndView, HttpSession session, @RequestParam(required=false) Integer type)
    {
		//添加user参数
        Long userId=(Long)session.getAttribute("userId");
        User user=apiService.getUser(userId);
        modelAndView.addObject("user", user);
        
        //添加productViewList参数
        List<ProductView> productViewList=new ArrayList<>();
        
        if(user!=null)//已经登录的
        {
        	if(user.getUsertype()==0)//卖家
        	{
            	Seller seller=(Seller) user;
        		if(!(type!=null && type==1))//展示所有卖家发布的产品，已出售的带有“已出售”标签
        		{
                	for(Product product:productRepository.findAll())
                	{
                		productViewList.add(new ProductView(product,null,apiService.isSell(product)));
                	}
        		}
        		else//展示卖家自己发布的产品，已出售的带有“已出售”标签
        		{
                	for(Product product:seller.getProducts())
                	{
                		productViewList.add(new ProductView(product,null,apiService.isSell(product)));
                	}
        		}
        	}
        	else//买家
        	{
        		Buyer buyer=(Buyer) user;
        		
//        		//让前台处理“已购买”标签以及已购买物品的显示
//            	for(Product product:productRepository.findAll())
//            	{
//            		productViewList.add(new ProductView(product,apiService.isBuy(buyer, product),null));
//            	}
        		
        		if(!(type!=null && type==1))//展示所有产品信息，已购买项目带有“已购买”标签
        		{
                	for(Product product:productRepository.findAll())
                	{
                		productViewList.add(new ProductView(product,apiService.isBuy(buyer, product),null));
                	}
        		}
        		else//展示买家还没有购买的内容页面
        		{
                	for(Product product:productRepository.findAll())
                	{
                		if(!apiService.isBuy(buyer, product))
                			productViewList.add(new ProductView(product,false,null));
                	}
        		}
        	}
        }
        else//未登录
        {
        	//展示所有卖家发布的产品信息，没有带“已购买”、“已出售”标签
        	for(Product product:productRepository.findAll())
        	{
        		productViewList.add(new ProductView(product,null,null));
//        		productViewList.add(new ProductView(product,true,true));
        	}
        }
        
        modelAndView.addObject("productViewList", productViewList);
        
        //添加listType参数
        modelAndView.addObject("listType", type==null?0:1);//0表示所有内容，1表示买家未购买的内容，或卖家自己发布的内容
        
        
        modelAndView.setViewName("index");
        return modelAndView;
    }
	
	@RequestMapping(value = "/show")
    public ModelAndView show(ModelAndView modelAndView, @RequestParam Long id, HttpSession session)
    {
		Long userId = (Long)session.getAttribute("userId");
        User user=apiService.getUser(userId);
        modelAndView.addObject("user", user);
        
		ProductView productView=new ProductView(productRepository.findById(id),null,null);
		
		if(user!=null)//已经登录的
        {
        	if(user.getUsertype()==0)//卖家
        	{
            	productView=new ProductView(productRepository.findById(id));
        	}
        	else//买家
        	{
        		Buyer buyer=(Buyer) user;
        		Product product= productRepository.findById(id);
        		productView=new ProductView(product,apiService.isBuy(buyer, product),null);
        	}
        }
        else //未登录
        {
        	productView=new ProductView(productRepository.findById(id));
        }
		
		
		modelAndView.addObject("productView", productView);
        modelAndView.setViewName("show");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView modelAndView)
    {
        modelAndView.setViewName("login");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session)
    {
		session.removeAttribute("userId");
        modelAndView.setViewName("login");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/public")
    public ModelAndView publicItem(ModelAndView modelAndView, HttpSession session)
    {
		Long userId = (Long)session.getAttribute("userId");
        User user=apiService.getUser(userId);
        modelAndView.addObject("user", user);
        
        
        modelAndView.setViewName("public");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/publicSubmit")
    public ModelAndView publicSubmit(ModelAndView modelAndView,Product product, HttpSession session)
    {
		Long id = (Long)session.getAttribute("userId");
		Seller seller=sellerRepository.findById(id);
		if(seller!=null)
		{
			product.setSeller(seller);
			productRepository.save(product);
		}
		else
		{
			product=null;
			modelAndView.addObject("product", product);
			modelAndView.addObject("message", "请先登录");
		}
		
        modelAndView.setViewName("publicSubmit");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/edit")
    public ModelAndView edit(ModelAndView modelAndView, @RequestParam Long id, HttpSession session)
    {
		Long userId = (Long)session.getAttribute("userId");
        User user=apiService.getUser(userId);
        modelAndView.addObject("user", user);
        
		Product product=productRepository.findById(id);
		modelAndView.addObject("product", product);
		
        modelAndView.setViewName("edit");
        
        return modelAndView;
    }

	@RequestMapping(value = "/editSubmit")
    public ModelAndView editSubmit(ModelAndView modelAndView, @RequestParam Long id, Product product, HttpSession session)
    {
		Long userId = (Long)session.getAttribute("userId");
		Seller seller=sellerRepository.findById(userId);
		if(seller!=null)
		{
			product.setSeller(seller);
			product.setId(id);
			productRepository.save(product);
		}
		else
		{
			product=null;
			modelAndView.addObject("product", product);
			modelAndView.addObject("message", "请先登录");
		}
		
        modelAndView.setViewName("editSubmit");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/account")
    public ModelAndView account(ModelAndView modelAndView)
    {
        modelAndView.setViewName("account");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/cart")
    public ModelAndView cart(ModelAndView modelAndView)
    {
        modelAndView.setViewName("cart");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/test")
    public ModelAndView test(ModelAndView modelAndView)
    {
    	
        modelAndView.setViewName("test");
        
        List<String> userList=new ArrayList<String>();
        userList.add("admin");
        userList.add("user1");
        userList.add("user2");
        
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }
	

}
