package com.zfd.message.template;

import com.zfd.message.Template;

/**
 * 通过字符串
 * 
 * @author zfd
 * @createtime 2018年4月16日
 * @email zhufudong001@gmail.com
 */
public class StringTemplate implements Template {
	private String tempCode;
	private String start = "{";
	private StringBuffer content;
	private String end = "}";

	public StringTemplate(String tempCode) {
		content = new StringBuffer();
		this.tempCode = tempCode;
	}

	@Override
	public String getTemplateParam() {
		return start + content.toString() + end;
	}

	@Override
	public String getTemplateCode() {
		return tempCode;
	}

	/**
	 * \"key\":\"val\",\"key\":\"val\",\"key\":\"val\"
	 * @param content
	 * @return
	 */
	public boolean addParams(String content) {
		this.content.append(content);
		return true;
	}
	@Override
	public boolean addParam(String key, String val) {
		if (!content.toString().isEmpty()) {
			content.append(",");
		}
		content.append("\"" + key + "\":\"" + val + "\"");
		return true;
	}
}
