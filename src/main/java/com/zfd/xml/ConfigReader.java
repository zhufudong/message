package com.zfd.xml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zfd.message.exception.ConfigReadExcetion;

/**
 * 通过读取配置文件信息得到 对应的值
 * 
 * @author zhufudong
 * @date 2018年1月29日
 * @email zhufudong001@gmail.com
 */
public class ConfigReader {
	/**
	 * 配置文件路径
	 */
	private static final String DEFAULT_FILTER_PATH = "message-config.xml";
	private static DocumentBuilder builder;
	private static File file = new File(getClassPathFile(DEFAULT_FILTER_PATH));// 读取过滤文本
	private static Document doc;

	public static String getClassPathFile(String name) {
		ClassLoader classLoader = ConfigReader.class.getClassLoader();
		URL url = classLoader.getResource(name);
		return url.getFile();
	}

	static {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到参数
	 * 
	 * @param param
	 * @param canChoose
	 *            是否可选
	 * @return
	 * @throws ConfigReadExcetion
	 */
	public static String getValue(String param, boolean canChoose) throws ConfigReadExcetion {
		NodeList list = doc.getElementsByTagName(param);
		if (list.getLength() == 0) {
			if (canChoose) {
				return null;
			} else {
				throw new ConfigReadExcetion("not found param :" + param);
			}
		}
		Node node = list.item(0).getFirstChild();
		if (node == null) {
			throw new ConfigReadExcetion("not found param :" + param);
		}
		return node.getNodeValue();
	}

	/**
	 * 得到第一个属性值
	 * 
	 * @param param
	 * @param attribe
	 * @param canChoose
	 *            是否可选
	 * @return
	 * @throws ConfigReadExcetion
	 */
	public static String getFirstAttribe(String param, String attribe, boolean canChoose) throws ConfigReadExcetion {
		NodeList list = doc.getElementsByTagName(param);
		if (list.getLength() == 0) {
			if (canChoose) {
				return null;
			} else {
				throw new ConfigReadExcetion("not found param :" + param);
			}
		}
		NamedNodeMap node = list.item(0).getAttributes();
		if (node == null) {
			throw new ConfigReadExcetion("not found param :" + param);
		}
		return node.getNamedItem(attribe).getNodeValue();
	}

	/**
	 * 得到名字相同的参数属性值
	 * 
	 * @param param
	 * @param attribe
	 * @param canChoose
	 *            是否可选
	 * @return
	 * @throws ConfigReadExcetion
	 */
	public static List<String> getAttribes(String param, String attribe, boolean canChoose) throws ConfigReadExcetion {
		NodeList list = doc.getElementsByTagName(param);
		if (list.getLength() == 0) {
			if (canChoose) {
				return null;
			} else {
				throw new ConfigReadExcetion("not found param :" + param);
			}
		}
		List<String> result = new ArrayList<>();
		for (int i = 0; i < list.getLength(); i++) {
			NamedNodeMap node = list.item(i).getAttributes();
			if (node == null) {
				throw new ConfigReadExcetion("not found param :" + param);
			}
			result.add(node.getNamedItem(attribe).getNodeValue());
		}
		return result;
	}
}
