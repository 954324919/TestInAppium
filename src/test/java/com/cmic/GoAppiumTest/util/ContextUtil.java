package com.cmic.GoAppiumTest.util;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;

/**
 * 应用上下文
 * 
 * @author ikiwi
 *
 */
public class ContextUtil {
	// 获取当前上下文
	public static String getContext() {
		return DriverManger.getDriver().getContext();
	}

	// 获取当前应用
	public static String getCurrentActivity() {
		return DriverManger.getDriver().currentActivity();
	}

	//获取应用包名
	@Tips(riskPoint="实际上是一种取巧的方式，不一定稳定")
	public static String getPackageName() {
		String execResult = AdbManager.excuteAdbShellGetResultGrep("adb shell dumpsys activity", "mFocusedActivity");
		String[] spiltResult = execResult.split(" ");
		String targetInfo = null;
		for (String tar : spiltResult) {
			if (tar.contains("/")) {
				targetInfo = tar;
				break;
			}
		}
		if (targetInfo != null) {
			String[] info = targetInfo.split("/");
			return info[0];
		}
		return "";
	}

	/**
	 * 当前只能访问LaunceActivity|可导出export
	 * 
	 * @param packageName
	 *            包名
	 * @param activityName
	 *            Activity名
	 */
	public static void goTargetActivity(String packageName, String activityName) {// 访问目标Activity
		// 测试内部应用com.cmic.mmnes/.activity.SearchActivity
		// 测试外部应用com.example.android.contactmanager/.ContactManager
		DriverManger.getDriver().startActivity(packageName, activityName);
	}

	// 是否安装
	public static boolean isTestAppInstalled() {
		return DriverManger.getDriver().isAppInstalled(App.PACKAGE_NAME);
	}

	// 安装应用
	public static void installApk(String path, String apkName) {
		AdbManager.excuteAdbShell("adb install " + path + "/" + apkName);
	}

	// 卸载应用
	public static void unInstallApk(String packageName) {
		AdbManager.excuteAdbShell("adb uninstall " + packageName);
	}
}
