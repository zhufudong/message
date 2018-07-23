package com.zfd.message.send;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zfd.message.Config;
import com.zfd.message.Template;
import com.zfd.message.error.ResultCode;
import com.zfd.message.exception.ConfigReadExcetion;
import com.zfd.message.exception.ErrorException;
import com.zfd.message.exception.InputErrorException;

/**
 * 短信发送 阿里接口
 * 
 * @author zfd
 * @createtime 2018年4月16日
 * @email zhufudong001@gmail.com
 */
public class AliyunSendMessage {
	private static Logger log = Logger.getLogger(AliyunSendMessage.class);
	/**
	 * 默认超时时间
	 */
	private final String DEFAULT_TIME_OUT = "10000";
	/**
	 * 默认提交方式
	 */
	private final MethodType DEFAULT_METHOD = MethodType.POST;
	/************* 固定参数 ********/
	/**
	 * 短信API产品名称
	 */
	private final String PRODUCT = "Dysmsapi";
	/**
	 * 短信API产品域名
	 */
	private final String DOMAIN = "dysmsapi.aliyuncs.com";
	/************** 必选参数 ********/
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
	 * 模板参数
	 */
	private Template template;
	/************* 可选参数 **********/
	/**
	 * 提交数据方式
	 */
	private MethodType methodType;
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

	/***** 其他参数 */
	/**
	 * 单例实例
	 */
	private static AliyunSendMessage sendMessage;

	private static Lock lock = new ReentrantLock();
	private IClientProfile profile;
	private IAcsClient acsClient;
	/**
	 * 配置文件
	 */
	private Config config;

	private AliyunSendMessage() {
		config = new Config();
		readloadConfig();
		// 设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", timeOut == null ? DEFAULT_TIME_OUT : timeOut);
		System.setProperty("sun.net.client.defaultReadTimeout", timeOut == null ? DEFAULT_TIME_OUT : timeOut);
		profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		acsClient = new DefaultAcsClient(profile);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到模板 配置文件中第一个 <br>
	 * 默认使用第一个 有需要进行修改<br>
	 * 若配置文件没有配置 则需要自己设置
	 * 
	 * @return
	 */
	public Template getTemplate() {
		if(template==null) {
			
		}
		return this.template;
	}

	/**
	 * 得到实例 是否 需要读取配置文件
	 * 
	 * @return
	 * @throws ConfigReadExcetion
	 * @throws InputErrorException
	 */
	public static AliyunSendMessage getInstance() {
		if (sendMessage == null) {
			synchronized (lock) {
				if (sendMessage == null) {
					sendMessage = new AliyunSendMessage();
				}
			}
		}
		return sendMessage;
	}

	/**
	 * 检查是否缺少参数
	 * 
	 * @return
	 * @throws InputErrorException
	 */
	public boolean checkParam() throws InputErrorException {
		if (accessKeyId == null) {
			throw new InputErrorException("accessKeyId can't to be null，please set it");
		}
		if (accessKeySecret == null) {
			throw new InputErrorException("accessKeySecret can't to be null，please set it");
		}
		if (signName == null) {
			throw new InputErrorException("signName can't to be null，please set it");
		}
		if (template == null) {
			throw new InputErrorException("template can't to be null，please set it");
		}
		return true;
	}

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @param key
	 * @param val
	 * @return
	 * @throws ErrorException
	 * @throws InputErrorException
	 * @throws ClientException
	 * @throws Exception
	 */
	public boolean sendCode(String phone, String key, String val)
			throws ClientException, InputErrorException, ErrorException {
		if(this.template==null) {
			throw new InputErrorException("template can't to be null，please set it");
		}
		this.template.addParam(key, val);
		return sendContent(phone);
	}

	/**
	 * 发送短信
	 * 
	 * @param phone
	 * @return
	 * @throws ClientException
	 * @throws InputErrorException
	 * @throws ErrorException
	 * @throws Exception
	 */
	public boolean sendContent(String phone) throws ClientException, InputErrorException, ErrorException {
		checkParam();
		SendSmsRequest request = new SendSmsRequest();
		request.setMethod(methodType);
		request.setPhoneNumbers(phone);
		request.setSignName(this.signName);
		request.setTemplateCode(template.getTemplateCode());
		request.setTemplateParam(template.getTemplateParam());
		if (smsUpExtendCode != null) {
			request.setSmsUpExtendCode(smsUpExtendCode);
		}
		if (outId != null) {
			request.setOutId(outId);
		}
		// 请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && !sendSmsResponse.getCode().equals("OK")) {
			ResultCode result = ResultCode.allResult.get(sendSmsResponse.getCode());
			if (result == null) {
				log.error(sendSmsResponse.getCode());
				throw new ErrorException("not found error state");
			}
			throw new ErrorException("error code:" + result.code + ":tips-->" + result.tips);
		}
		return true;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	/**
	 * 设置秘钥
	 * 
	 * @param accessKeySecret
	 */
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
		profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		acsClient = new DefaultAcsClient(profile);
	}

	/**
	 * 设置秘钥id
	 * 
	 * @param accessKeyId
	 */
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
		profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		acsClient = new DefaultAcsClient(profile);
	}

	/**
	 * 设置模板
	 * 
	 * @param template
	 */
	public void setTemplate(Template template) {
		this.template = template;
	}

	/**
	 * 可选设置超时时间
	 * 
	 * @param time
	 */
	public void setTimtOut(long time) {
		this.timeOut = "" + time;
		// 设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", timeOut);
		System.setProperty("sun.net.client.defaultReadTimeout", timeOut);
	}

	/**
	 * 设置提交方式
	 * 
	 * @param type
	 */
	public void setMethodType(MethodType type) {
		this.methodType = type == null ? DEFAULT_METHOD : type;
	}

	/**
	 * 设置提交方式
	 * 
	 * @param type
	 * @throws InputErrorException
	 */
	public void setMethodType(String type) throws InputErrorException {
		if (type == null) {
			this.methodType = DEFAULT_METHOD;
		} else {
			type = type.toLowerCase();
			switch (type) {
			case "get":
				methodType = MethodType.GET;
				break;
			case "delete":
				methodType = MethodType.DELETE;
				break;
			case "head":
				methodType = MethodType.HEAD;
				break;
			case "options":
				methodType = MethodType.OPTIONS;
				break;
			case "post":
				methodType = MethodType.POST;
				break;
			case "put":
				methodType = MethodType.PUT;
				break;
			default:
				this.methodType = DEFAULT_METHOD;
				throw new InputErrorException("method Type is Error,not fount " + type);
			}
		}
	}

	/**
	 * 重新读取配置文件信息
	 * 
	 * @return
	 */
	public boolean readloadConfig() {
		try {
			config.init(); // 初始化配置参数
			signName = config.getSignName();
			accessKeyId = config.getAccessKeyId();
			accessKeySecret = config.getAccessKeySecret();
			setMethodType(config.getMethodType());
			smsUpExtendCode = config.getSmsUpExtendCode();
			timeOut = config.getTimeOut();
			template = config.getAllTemplate().get(0);// 默认使用第一个模板 后面有需要再修改
			outId = config.getOutId();
			log.info("read config success");
			return true;
		} catch (ConfigReadExcetion | InputErrorException e) {
			// 必填参数没有 则为代码设置
			log.error(e.getMessage());
		}
		return false;
	}
}
