package com.cmic.GoAppiumTest.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;

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

	/**
	 * 文件是否存在
	 * 
	 * @param fileFullPath
	 * @return
	 */
	public static boolean fileIsExist(String fileFullPath) {
		File tempFile = new File(fileFullPath);
		return fileIsExist(tempFile);
	}

	public static boolean fileIsExist(File fileFullPath) {
		return fileFullPath.exists();
	}

	/**
	 * 文件夹路径是否存在
	 * 
	 * @param file
	 * @return
	 */
	@Tips(riskPoint = "必须先创建文件家，才能写文件")
	public static boolean filePathIsExist(File filedir) {
		if (filedir.exists() && filedir.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 文件夹路径是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean filePathIsExist(String filedir) {
		File file = new File(filedir);
		return filePathIsExist(file);
	}

	/**
	 * 创建文件夹
	 * 
	 * @param filePath
	 */
	public static void createFilePath(String filePath) {
		File file = new File(filePath);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdir();
			// 使用mkdirs保证创建上层缺失目录
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 * @param name
	 * @throws IOException
	 */
	@Tips(description = "", riskPoint = "必须创建上次文件，保证不会FileIOEx")
	public static void createFile(String path, String name) throws IOException {
		createFilePath(path);
		File tempFile = new File(path + File.separator + name);
		if (!fileIsExist(tempFile)) {
			tempFile.createNewFile();
		}
	}
}
