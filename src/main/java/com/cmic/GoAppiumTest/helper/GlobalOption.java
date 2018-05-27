package com.cmic.GoAppiumTest.helper;

import java.io.File;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.util.DeviceUtil;

public class GlobalOption {
	/**
	 * 创建userDir\appName_appVersion\为测试结果存储目录testDir
	 */
	private void initDir() {
		File root = new File(App.APP_NAME, App.APP_VERSION);
		if (!root.exists()) {
			root.mkdir();
		}
		File file = new File(root, DeviceUtil.getDeviceName());
		if (!file.exists()) {
			file.mkdir();
		}
		File testDir = new File(file, DeviceUtil.getDeviceTime());
		if (!testDir.exists()) {
			testDir.mkdir();
		}
	}

	/**
	 * 配置类，可自定义配置用户目录、app名、app版本
	 */
	public static class Config {

		File userDir;
		String appName;
		String appVersion;

		public Config userDir(String dir) {
			userDir = new File(dir);
			return this;
		}

		public Config appName(String name) {
			appName = name;
			return this;
		}

		public Config appVersion(String version) {
			appVersion = version;
			return this;
		}

		public GlobalOption build() {
			return new GlobalOption();
		}
	}
}
