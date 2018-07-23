package com.zfd.message;

/**
 * 模板相关处理
 * 
 * @author zfd
 * @createtime 2018年4月16日
 * @email zhufudong001@gmail.com
 */
public interface Template {
	/**
	 * 得到模板需要的参数 JSON形式
	 * 
	 * @return
	 */
	public String getTemplateParam();

	/**
	 * 得到模板代码
	 * 
	 * @return
	 */
	public String getTemplateCode();

	/**
	 * 增加一个参数
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public boolean addParam(String key, String val);
}
