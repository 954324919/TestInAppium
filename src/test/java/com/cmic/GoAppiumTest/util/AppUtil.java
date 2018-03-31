package com.cmic.GoAppiumTest.util;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;

public class AppUtil {
	// 先closeApp然后在launchAPP
	public static void resetApp() {
		DriverManger.getDriver().resetApp();
	}

	// 关闭应用，其实就是按home键把应用置于后台
	public static void closeApp() {
		DriverManger.getDriver().closeApp();
	}

	// 启动应用
	public static void launchApp() {
		DriverManger.getDriver().launchApp();
	}

	public static void killApp() {
		DriverManger.getDriver().close();
	}

	/**
	 * 清除应用缓存
	 * @param packageName
	 */
	public static void clearAppData(String packageName) {
		System.err.println("清除应用缓存并杀死应用进程");
		AdbManager.excuteAdbShell("adb shell pm clear " + packageName);
		WaitUtil.implicitlyWait(2);
	}
}
