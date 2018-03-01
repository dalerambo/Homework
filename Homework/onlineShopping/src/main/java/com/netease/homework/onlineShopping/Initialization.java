package com.netease.homework.onlineShopping;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.Product;
import com.netease.homework.onlineShopping.domain.Seller;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.ProductRepository;
import com.netease.homework.onlineShopping.repository.SellerRepository;

@Configuration
public class Initialization implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if(sellerRepository.count()==0)
			{
				Seller seller=new Seller();
				seller.setUsername("seller");
				seller.setPassword(getMD5("relles"));
				sellerRepository.saveAndFlush(seller);
			}
			if(buyerRepository.count()==0)
			{
				Buyer buyer=new Buyer();
				buyer.setUsername("buyer");
				buyer.setPassword(getMD5("reyub"));
				buyerRepository.saveAndFlush(buyer);
			}
			if(productRepository.count()==0)
			{
				Product product=new Product();
				product.setTitle("11");
				product.setSummary("11");
				product.setPrice(1.0);
				product.setDetail("11");
				product.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519626552671&di=1757241c633408b937f12340e23b4c73&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01e8a157f86d8ca84a0d304fcb9943.jpg%402o.jpg");
				product.setSeller(sellerRepository.findById((long) 1));
				productRepository.saveAndFlush(product);
				
				product=new Product();
				product.setTitle("22");
				product.setSummary("22");
				product.setPrice(2.0);
				product.setDetail("22");
				product.setImage("https://ss0.bdstatic.com/6ONWsjip0QIZ8tyhnq/it/u=3618554304,2887917621&fm=77&w_h=121_75&cs=2820658166,1330608299");
				product.setSeller(sellerRepository.findById((long) 1));
				productRepository.saveAndFlush(product);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		
		

	}
	
	
    /** 
     * 生成md5 
     *  
     * @param message 
     * @return 
     */  
    public static String getMD5(String message) {  
    String md5str = "";  
    try {  
        // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象  
        MessageDigest md = MessageDigest.getInstance("MD5");  
  
        // 2 将消息变成byte数组  
        byte[] input = message.getBytes();  
  
        // 3 计算后获得字节数组,这就是那128位了  
        byte[] buff = md.digest(input);  
  
        // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串  
        md5str = bytesToHex(buff);  
  
    } catch (Exception e) {  
        e.printStackTrace();  
    }  
    return md5str;  
    } 
    
    /** 
     * 二进制转十六进制 
     *  
     * @param bytes 
     * @return 
     */  
    public static String bytesToHex(byte[] bytes) {  
    StringBuffer md5str = new StringBuffer();  
    // 把数组每一字节换成16进制连成md5字符串  
    int digital;  
    for (int i = 0; i < bytes.length; i++) {  
        digital = bytes[i];  
  
        if (digital < 0) {  
        digital += 256;  
        }  
        if (digital < 16) {  
        md5str.append("0");  
        }  
        md5str.append(Integer.toHexString(digital));  
    }  
    return md5str.toString().toUpperCase();  
    }  

}
