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
}
