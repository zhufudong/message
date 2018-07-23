package com.zfd.message.exception;

/**
 * 输入异常 如缺少签名
 * 
 * @author zfd
 * @createtime 2018年4月16日
 * @email zhufudong001@gmail.com
 */
public class InputErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputErrorException() {
		super("your input is error");
	}

	public InputErrorException(String message) {
		super(message);
	}

}
