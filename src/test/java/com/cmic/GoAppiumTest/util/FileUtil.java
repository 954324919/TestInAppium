package com.cmic.GoAppiumTest.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import com.cmic.GoAppiumTest.base.DriverManger;

public class FileUtil {

	public static void pushFile(String path, String fileName) {
		// 上传文件
		File file = new File(path + "/" + fileName);
		String content = null;
		try {
			content = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = Base64.encodeBase64(content.getBytes());
		DriverManger.getDriver().pushFile("sdcard/test.txt", data);
	}

	public static void pullFile() {
		// 下载文件举例：
		byte[] resultDate = DriverManger.getDriver().pullFile("sdcard/test.txt");
		System.out.println(new String(Base64.decodeBase64(resultDate)));
	}

	public static void pullFilefolder() {
		// 下载文件夹：
		DriverManger.getDriver().pullFolder("tmp/");
		// 把android的tmp目录拷贝到临时文件夹
		System.out.println(DriverManger.getDriver().pullFolder("tmp/"));
	}
}
