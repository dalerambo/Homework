package com.netease.homework.onlineShopping.exception;


import com.netease.homework.onlineShopping.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//全局异常处理
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PageAuthorizationException.class)
    public ModelAndView handlePageAuthorizationException(PageAuthorizationException e) {
        ModelAndView modelAndView=new ModelAndView("errorPage");
        modelAndView.addObject("info",e.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(ApiAuthorizationException.class)
    @ResponseBody
    public Result handleApiAuthorizationException(ApiAuthorizationException e) {
        return new Result(e.getMessage(),417);
}

    @ExceptionHandler(BusinessLogicException.class)
    @ResponseBody
    public Result handleBusinessLogicException(BusinessLogicException e) {
        return new Result(e.getMessage(),417);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e, HttpServletRequest request) {
        return new Result(e.getMessage(),400);
    }
}
