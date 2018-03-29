package com.cmic.GoAppiumTest.util;

import com.cmic.GoAppiumTest.base.DriverManger;

/**
 * 应用上下文
 * @author ikiwi
 *
 */
public class ContextUtil {
	//获取当前上下文
	public static String getContext() {
		return DriverManger.getDriver().getContext();
	}
	
	//获取当前应用
	public static String getCurrentActivity() {
		return DriverManger.getDriver().currentActivity();
	}
	
	// 访问目标Activity
	public static void goTargetActivity(String packageName,String activityName) {
		//测试内部应用com.cmic.mmnes/.activity.SearchActivity
		//测试外部应用com.example.android.contactmanager/.ContactManager
		DriverManger.getDriver().startActivity(packageName, activityName);
	}
}
