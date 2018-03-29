package com.cmic.GoAppiumTest.base;

public class AdbManager {
	// 执行adb命令
	@SuppressWarnings("unused")
	public  static void excuteAdbShell(String s) {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(s);
		} catch (Exception e) {
			System.out.println("执行命令:" + s + "出错");
		}
	}
}
