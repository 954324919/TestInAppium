package com.cmic.GoAppiumTest.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.cmic.GoAppiumTest.helper.Tips;

public class PropertiesUtil {

	private static Map<String, Properties> propertiesMap = new HashMap<>();

	@Tips(description = "加载配置文件", riskPoint = "造成内存泄漏")
	public static Properties load(String filePath) {
		if (FileUtil.fileIsExist(filePath)) {
			if (propertiesMap.containsKey(filePath)) {
				return propertiesMap.get(filePath);
			}
			try {
				FileInputStream in = new FileInputStream(filePath);
				InputStreamReader iReader = new InputStreamReader(in, "UTF-8");
				Properties p = new Properties();
				p.load(iReader);
				propertiesMap.put(filePath, p);
				return p;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			throw new RuntimeException("不存在目标的配置文件");
		}
	}

	@Tips(description = "加载配置文件",riskPoint="")
	public static Properties load(InputStream in) {
		try {
			InputStreamReader iReader = new InputStreamReader(in, "UTF-8");
			Properties p = new Properties();
			p.load(iReader);
			return p;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getProperty(String fileName, String value) {
		return load(fileName).getProperty(value);
	}
}
