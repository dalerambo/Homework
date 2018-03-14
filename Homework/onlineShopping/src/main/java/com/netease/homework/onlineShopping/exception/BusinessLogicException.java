package com.netease.homework.onlineShopping.exception;

//业务逻辑异常
public class BusinessLogicException extends BaseException {

	public static BusinessLogicException PRODUCT_NOT_EXIST = new BusinessLogicException("商品不存在",1001);

	public static BusinessLogicException PRODUCT_SOLD_OUT = new BusinessLogicException("商品已出售",1002);

	public static BusinessLogicException PRODUCT_ALREADY_BOUGHT = new BusinessLogicException("商品已购买",1003);

	public static BusinessLogicException NUM_MUST_POSITIVE = new BusinessLogicException("数量必须大于0",1004);

	public static BusinessLogicException CARTITEM_NOT_EXIST = new BusinessLogicException("购物车项目不存在",1005);

	public static BusinessLogicException CARTITEM_NOT_BELONG_TO_USER = new BusinessLogicException("购物车项目不属于该用户",1006);

	public static BusinessLogicException REQUEST_PARAM_ERROR = new BusinessLogicException("购物车项目不属于该用户",1007);

	private BusinessLogicException(String message, Integer code)
	{
		super(message,code);
	}

}
