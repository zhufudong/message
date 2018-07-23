package com.zfd.message.exception;
/**
 * 返回时出错
 * @author zfd
 * @createtime 2018年4月16日
 * @email zhufudong001@gmail.com
 */
public class ErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorException() {
		super();
	}

	public ErrorException(String message) {
		super(message);
	}

}
