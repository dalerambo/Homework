package com.netease.homework.onlineShopping.exception;

public class PageAuthorizationException extends BaseException  {

    //需要登录
    public static PageAuthorizationException REQUIRE_LOGIN = new PageAuthorizationException("请登录",2001);

    //需要以卖家身份登录
    public static PageAuthorizationException REQUIRE_SELLER = new PageAuthorizationException("请以卖家身份登录",2002);

    //需要以买家身份登录
    public static PageAuthorizationException REQUIRE_BUYER = new PageAuthorizationException("请以买家身份登录",2003);

    private PageAuthorizationException(String message, Integer code) {
        super(message, code);
    }


}
