package com.zfd.message.template;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zfd.message.Template;

/**
 * 设置模板参数 HasH保存
 * 
 * @author zfd
 * @createtime 2018年4月16日
 * @email zhufudong001@gmail.com
 */
public class HashTemplate implements Template {
	/**
	 * 对应的键值对
	 */
	private ConcurrentHashMap<String, String> params;
	private String tempCode;

	public HashTemplate(String tempCode) {
		this.params = new ConcurrentHashMap<>();
		this.tempCode = tempCode;
	}

	/**
	 * 增加多个参数
	 * 
	 * @param params
	 * @return
	 */
	public boolean addParams(Map<String, String> params) {
		try {
			this.params.putAll(params);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addParam(String key, String val) {
		try {
			this.params.put(key, val);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getTemplateParam() {
		StringBuffer json = new StringBuffer("{");
		this.params.keySet().forEach(key -> {
			json.append("\"" + key + "\":\"" + this.params.get(key) + "\",");
		});
		if (json.length() > 1)
			json.deleteCharAt(json.length() - 1);
		json.append("}");
		return json.toString();
	}

	@Override
	public String getTemplateCode() {
		return this.tempCode;
	}

}
