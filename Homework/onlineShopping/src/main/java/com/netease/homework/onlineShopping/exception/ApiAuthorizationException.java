package com.netease.homework.onlineShopping.exception;

//页面权限的异常
public class ApiAuthorizationException extends BaseException  {

    //需要登录
    public static ApiAuthorizationException REQUIRE_LOGIN = new ApiAuthorizationException("请登录",3001);

    //需要以卖家身份登录
    public static ApiAuthorizationException REQUIRE_SELLER = new ApiAuthorizationException("请以卖家身份登录",3002);

    //需要以买家身份登录
    public static ApiAuthorizationException REQUIRE_BUYER = new ApiAuthorizationException("请以买家身份登录",3003);

    private ApiAuthorizationException(String message, Integer code) {
        super(message, code);
    }


}
