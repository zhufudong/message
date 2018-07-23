package com.zfd;

import com.aliyuncs.exceptions.ClientException;
import com.zfd.message.Template;
import com.zfd.message.exception.ErrorException;
import com.zfd.message.exception.InputErrorException;
import com.zfd.message.send.AliyunSendMessage;
import com.zfd.message.template.HashTemplate;

public class Test {
	/**
	 * 使用方法1<br>
	 * 单单发送一个值
	 */
	public void use1() {
		AliyunSendMessage send = null;
		send = AliyunSendMessage.getInstance();
		System.out.println("发送...");
		try {
			send.sendCode("15716022396", "code", "123456");
		} catch (ClientException | InputErrorException | ErrorException e) {
			System.out.println("发送失败");
			e.printStackTrace();
		}
		System.out.println("发送成功");
	}

	/**
	 * 使用多数参数
	 */
	public void use2() {
		AliyunSendMessage send = null;
		send = AliyunSendMessage.getInstance();
		Template template = send.getTemplate();
		if(template==null) {
			
		}
		template.addParam("code", "123456");// 可添加多个参数
		System.out.println("发送...");
		try {
			send.sendContent("15716022396");
		} catch (ClientException | InputErrorException | ErrorException e) {
			System.out.println("发送失败");
			e.printStackTrace();
		}
		System.out.println("发送成功");
	}

	/**
	 * 自行设置参数
	 */
	public void use3() {
		AliyunSendMessage send = null;
		send = AliyunSendMessage.getInstance();
		send.setAccessKeyId("1234324");
		send.setAccessKeySecret("fasd4f56asf4");
		Template template = new HashTemplate("SMS_125454");
		template.addParam("code", "123456");// 可添加多个参数
		System.out.println("发送...");
		send.setTemplate(template);
		try {
			send.sendContent("15716022396");
		} catch (ClientException | InputErrorException | ErrorException e) {
			System.out.println("发送失败");
			e.printStackTrace();
		}
		System.out.println("发送成功");
	}

	public static void main(String[] args) {
		Test test =new Test();
		test.use1();
		test.use2();
		test.use3();
	}
}
