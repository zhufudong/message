package com.zfd.message.exception;

/**
 * 配置文件读取异常
 * 
 * @author zfd
 * @createtime 2018年4月17日
 * @email zhufudong001@gmail.com
 */
public class ConfigReadExcetion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigReadExcetion() {
		super("config read error");
	}

	public ConfigReadExcetion(String message) {
		super(message);
	}

}
