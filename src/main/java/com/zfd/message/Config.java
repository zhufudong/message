package com.zfd.message;

import java.util.ArrayList;
import java.util.List;

import com.zfd.message.exception.ConfigReadExcetion;
import com.zfd.message.template.HashTemplate;
import com.zfd.xml.ConfigReader;

/**
 * 配置文件信息
 * 
 * @author zfd
 * @createtime 2018年4月17日
 * @email zhufudong001@gmail.com
 */
public class Config {

	/**
	 * 配置得到的模板
	 */
	private List<Template> allTemplate = new ArrayList<>();

	public void init() throws ConfigReadExcetion {
		try {
			// 读取基础信息
			accessKeyId = ConfigReader.getValue("accessKeyId", false);
			accessKeySecret = ConfigReader.getValue("accessKeySecret", false);
			signName = ConfigReader.getValue("signName", false);
			List<String> listCode = ConfigReader.getAttribes("template", "code", false);
			timeOut = ConfigReader.getValue("timeout", true);
			methodType = ConfigReader.getValue("method", true);
			smsUpExtendCode = ConfigReader.getValue("SmsUpExtendCode", true);
			outId = ConfigReader.getValue("outId", true);
			listCode.forEach(code -> {
				Template template = new HashTemplate(code);
				allTemplate.add(template);
			});
		} catch (NullPointerException e) {
			throw new ConfigReadExcetion("not fount file message-config.xml");
		}

	}

	public String getOutId() {
		return outId;
	}

	public List<Template> getAllTemplate() {
		return allTemplate;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public String getSmsUpExtendCode() {
		return smsUpExtendCode;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public String getSignName() {
		return signName;
	}

	public String getMethodType() {
		return methodType;
	}

	public String getTimeOut() {
		return timeOut;
	}

	/**
	 * 秘钥id
	 */
	private String accessKeyId;
	/**
	 * 秘钥
	 */
	private String accessKeySecret;
	/**
	 * 签名
	 */
	private String signName;
	/**
	 * 提交数据方式
	 */
	private String methodType;
	/**
	 * 超时时间
	 */
	private String timeOut;
	/**
	 * 上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
	 */
	private String smsUpExtendCode;
	/** outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者 */
	private String outId;
}
