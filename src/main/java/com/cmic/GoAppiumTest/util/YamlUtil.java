package com.cmic.GoAppiumTest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import org.yaml.snakeyaml.Yaml;

public class YamlUtil {

	public static <T> void bean2Yaml(String path, String fileName, T t) {
		try {
			File yamlFile = new File(path, fileName);
			System.err.println(yamlFile.getAbsolutePath());
			if (!yamlFile.exists()) {
				// 创建父级别目录
				yamlFile.getParentFile().mkdir();// 用mkdirs有风险
				yamlFile.createNewFile();
			}
			Yaml yaml = new Yaml();
			yaml.dump(t, new FileWriter(yamlFile));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static <T> T yaml2Bean(File file, Class<T> clazz) {
		try {
			if (!file.isFile()) {
				throw new RuntimeException("没有找到对应文件");
			}
			Yaml yaml = new Yaml();
			return yaml.loadAs(new FileInputStream(file), clazz);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T yaml2Bean(String yamlString, Class<T> clazz) {
		if (yamlString == null || yamlString.isEmpty()) {
			throw new RuntimeException("Yaml字符流不能为空");
		}
		Yaml yaml = new Yaml();
		return yaml.loadAs(yamlString, clazz);
	}
}
