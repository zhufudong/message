package com.zfd.message.error;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 结果对应解释
 * @author zfd
 * @createtime 2018年4月16日
 * @email zhufudong001@gmail.com
 */
public enum ResultCode {
	OK("请求完成", "OK"),
	AMOUNT_NOT_ENOUGH("账户余额不足", "isv.AMOUNT_NOT_ENOUGH"),
	RAM_PERMISSION_DENY("RAM权限DENY","isp.RAM_PERMISSION_DENY"),
	OUT_OF_SERVICE("业务停机","isv.OUT_OF_SERVICE"),
	PRODUCT_UN_SUBSCRIPT("未开通云通信产品","isv.PRODUCT_UN_SUBSCRIPT"),
	PRODUCT_UNSUBSCRIBE("产品未开通","isv.PRODUCT_UNSUBSCRIBE"),
	ACCOUNT_NOT_EXISTS("账户不存在", "isv.ACCOUNT_NOT_EXISTS"),
	ACCOUNT_ABNORMAL("账户异常", "isv.ACCOUNT_ABNORMAL"),
	SMS_TEMPLATE_ILLEGAL("短信模板不合法", "isv.SMS_TEMPLATE_ILLEGAL"),
	SMS_SIGNATURE_ILLEGAL("短信签名不合法", "isv.SMS_SIGNATURE_ILLEGAL"),
	INVALID_PARAMETERS("参数异常", "isv.INVALID_PARAMETERS"),
	SYSTEM_ERROR("系统错误", "isp.SYSTEM_ERROR"),
	MOBILE_NUMBER_ILLEGAL("非法手机号", "isv.MOBILE_NUMBER_ILLEGAL"),
	MOBILE_COUNT_OVER_LIMIT("手机号码数量超过限制", "isv.MOBILE_COUNT_OVER_LIMIT"),
	TEMPLATE_MISSING_PARAMETERS("模板缺少变量", "isv.TEMPLATE_MISSING_PARAMETERS"),
	BUSINESS_LIMIT_CONTROL("业务限流","isv.BUSINESS_LIMIT_CONTROL"),
	INVALID_JSON_PARAM("JSON参数不合法，只接受字符串值","isv.INVALID_JSON_PARAM"),
	BLACK_KEY_CONTROL_LIMIT("黑名单管控","isv.BLACK_KEY_CONTROL_LIMIT"),
	PARAM_LENGTH_LIMIT("参数超出长度限制","isv.PARAM_LENGTH_LIMIT"),
	PARAM_NOT_SUPPORT_URL("不支持URL","isv.PARAM_NOT_SUPPORT_URL")
	;
	public static ConcurrentHashMap<String, ResultCode> allResult = new ConcurrentHashMap<>();
	public String tips;
	public String code;

	private ResultCode(String tips, String code) {
		this.tips = tips;
		this.code = code;
	}
	
	static {
		for(ResultCode result:ResultCode.values()) {
			ResultCode.allResult.put(result.code, result);
		}
	}
	
	public void print() {
		System.out.println("resultCode:" + code + "-->" + tips);
	}
}
