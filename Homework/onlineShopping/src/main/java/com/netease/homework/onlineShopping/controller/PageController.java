package com.netease.homework.onlineShopping.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.netease.homework.onlineShopping.annotation.AuthorityEnum;
import com.netease.homework.onlineShopping.annotation.QueryTypeEnum;
import com.netease.homework.onlineShopping.annotation.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.netease.homework.onlineShopping.domain.Product;
import com.netease.homework.onlineShopping.result.ProductView;
import com.netease.homework.onlineShopping.domain.Seller;
import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.AccountItemRepository;
import com.netease.homework.onlineShopping.repository.ProductRepository;
import com.netease.homework.onlineShopping.repository.SellerRepository;
import com.netease.homework.onlineShopping.service.BusinessService;

@Controller
@RequestMapping("/")
public class PageController {

	@Autowired
	private SellerRepository sellerRepository;
	
    @Autowired
	private BuyerRepository buyerRepository;
    
    @Autowired
	private ProductRepository productRepository;
    
    @Autowired
	private AccountItemRepository accountItemRepository;
    
    @Autowired
    private BusinessService businessService;

	@RequestMapping(value = "/")
	public ModelAndView index(ModelAndView modelAndView, HttpSession session, @RequestParam(required=false) Integer type)
	{
		//添加user参数
		User user=businessService.getUserFromSession(session);
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
						ProductView productView=new ProductView(product,null,businessService.isSell(product));
						int num=businessService.getSellNumber(product);
						if(num!=0)
							productView.setNum(num);

						productViewList.add(productView);
					}
				}
				else//展示卖家自己发布的产品，已出售的带有“已出售”标签
				{
					for(Product product:seller.getProducts())
					{
						ProductView productView=new ProductView(product,null,businessService.isSell(product));
						int num=businessService.getSellNumber(product);
						if(num!=0)
							productView.setNum(num);

						productViewList.add(productView);
					}
				}
			}
			else//买家
			{
				Buyer buyer=(Buyer) user;

//        		//让前台处理“已购买”标签以及已购买物品的显示
//            	for(Product product:productRepository.findAll())
//            	{
//            		productViewList.add(new ProductView(product,businessService.isBuy(buyer, product),null));
//            	}

				if(!(type!=null && type==1))//展示所有产品信息，已购买项目带有“已购买”标签
				{
					for(Product product:productRepository.findAll())
					{
						ProductView productView=new ProductView(product,businessService.isBuy(buyer, product),null);
						productViewList.add(productView);
					}
				}
				else//展示买家还没有购买的内容页面
				{
					for(Product product:productRepository.findAll())
					{
						if(!businessService.isBuy(buyer, product))
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
    public ModelAndView show(ModelAndView modelAndView, HttpSession session, @RequestParam Long id)
    {
		User user=businessService.getUserFromSession(session);
        modelAndView.addObject("user", user);
        
		ProductView productView;
		
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
        		
        		if(businessService.isBuy(buyer, product))
        		{
        			productView=new ProductView(product,true,null);
        			productView.setBuyPrice(accountItemRepository.findByProductAndBuyer(product, buyer).getBuyPrice());
        		}
        		else
        		{
        			productView=new ProductView(product,false,null);
        		}

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
	@Authorization(authority = AuthorityEnum.Seller, queryType = QueryTypeEnum.Page)
    public ModelAndView publicItem(ModelAndView modelAndView, HttpSession session)
    {
		User user=businessService.getUserFromSession(session);
        modelAndView.addObject("user", user);

        modelAndView.setViewName("public");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/publicSubmit")
	@Authorization(authority = AuthorityEnum.Seller, queryType = QueryTypeEnum.Page)
    public ModelAndView publicSubmit(ModelAndView modelAndView, HttpSession session, Product product)
    {
		Seller seller=(Seller)businessService.getUserFromSession(session);
		modelAndView.addObject("user", seller);
		product.setSeller(seller);
		productRepository.save(product);
		
        modelAndView.setViewName("publicSubmit");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/edit")
	@Authorization(authority = AuthorityEnum.Seller, queryType = QueryTypeEnum.Page)
    public ModelAndView edit(ModelAndView modelAndView, HttpSession session, @RequestParam Long id)
    {
        User user=businessService.getUserFromSession(session);
        modelAndView.addObject("user", user);
        
		Product product=productRepository.findById(id);
		modelAndView.addObject("product", product);
		
        modelAndView.setViewName("edit");
        
        return modelAndView;
    }

	@RequestMapping(value = "/editSubmit")
	@Authorization(authority = AuthorityEnum.Seller, queryType = QueryTypeEnum.Page)
    public ModelAndView editSubmit(ModelAndView modelAndView, HttpSession session, @RequestParam Long id, Product product)
    {
		Seller seller=(Seller)businessService.getUserFromSession(session);
		modelAndView.addObject("user", seller);
		product.setSeller(seller);
		product.setId(id);
		productRepository.save(product);
		
        modelAndView.setViewName("editSubmit");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/account")
	@Authorization(authority = AuthorityEnum.Buyer, queryType = QueryTypeEnum.Page)
    public ModelAndView account(ModelAndView modelAndView, HttpSession session)
    {
        User user=businessService.getUserFromSession(session);
        modelAndView.addObject("user", user);

		Buyer buyer = (Buyer) user;
		modelAndView.addObject("itemList", buyer.getAccount());
        
        modelAndView.setViewName("account");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/cart")
	@Authorization(authority = AuthorityEnum.Buyer, queryType = QueryTypeEnum.Page)
    public ModelAndView cart(ModelAndView modelAndView, HttpSession session)
    {
        User user=businessService.getUserFromSession(session);
        modelAndView.addObject("user", user);

		Buyer buyer = (Buyer) user;
		modelAndView.addObject("itemList", buyer.getCart());
        
        modelAndView.setViewName("cart");
        
        return modelAndView;
    }

}
