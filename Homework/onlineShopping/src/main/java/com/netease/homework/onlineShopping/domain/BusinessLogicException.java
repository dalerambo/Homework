package com.netease.homework.onlineShopping.domain;

public class BusinessLogicException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	
	public BusinessLogicException(String message, Integer code)
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
}
