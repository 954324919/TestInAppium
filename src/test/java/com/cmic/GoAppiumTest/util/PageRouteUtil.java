package com.cmic.GoAppiumTest.util;

import com.cmic.GoAppiumTest.base.DriverManger;

import io.appium.java_client.android.AndroidKeyCode;

/**
 * 页面路由
 * 
 * @author ikiwi
 *
 */
public class PageRouteUtil {
	// 后退
	public static void pressBack() {
		DriverManger.getDriver().pressKeyCode(AndroidKeyCode.BACK);
	}
	
	// 访问目标
	public static void goTargetActivity() {
		DriverManger.getDriver().startActivity("com.example.android.contactmanager", "ContactManager");
	}

	// 后退
	public static void pressHome() {
		DriverManger.getDriver().pressKeyCode(AndroidKeyCode.HOME);
	}

	// 点击目录
	public static void pressMenu() {
		DriverManger.getDriver().pressKeyCode(AndroidKeyCode.KEYCODE_MENU);
	}

	// 下拉页面通知栏
	public static void drawNotification() {
		DriverManger.getDriver().openNotifications();
	}

	// 浏览器功能-前进
	public static void goWebkitForward() {
		DriverManger.getDriver().navigate().forward();
	}

	public static void goWebkitBack() {
		DriverManger.getDriver().navigate().back();
	}

	public static void goWebkitRefresh() {
		DriverManger.getDriver().navigate().refresh(); // 刷新
	}
}
