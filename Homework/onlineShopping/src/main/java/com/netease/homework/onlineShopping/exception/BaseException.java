package com.netease.homework.onlineShopping.exception;

public class BaseException extends Exception  {

    private Integer code;

    public BaseException(String message, Integer code)
    {
        super(message);
        this.setCode(code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        return ((BaseException)obj).getCode()==this.getCode();
    }
}
