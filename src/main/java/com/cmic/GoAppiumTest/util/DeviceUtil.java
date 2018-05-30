package com.cmic.GoAppiumTest.util;

import org.openqa.selenium.Capabilities;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;

public class DeviceUtil {

	public static Capabilities capabilities = DriverManger.getDriver().getCapabilities();

	@Tips(description = "本项目中DeviceVersion暂且用版本代码号代替")
	public static String getDeviceVersion() {
		return capabilities.getCapability("platformVersion").toString();
	}

	public static String getDevicePlatform() {
		return "Android";
	}

	public static String getDeviceName() {
		return capabilities.getCapability("deviceName").toString();
	}

	/**
	 * 简单比较两个APK的版本高低，取巧待完善
	 * 
	 * @param currentSdk
	 *            当前
	 * @param targetSdk
	 *            目标
	 * @return
	 */
	public static boolean moreThanTargetSdkVersion(String targetSdk) {
		String currentSdk = getDeviceVersion();// 7.1.2
		LogUtil.e(currentSdk);
		try {
			String temp = currentSdk.substring(0, currentSdk.indexOf("."));
			String temp1 = targetSdk.substring(0, targetSdk.indexOf("."));
			if (Integer.parseInt(temp) >= Integer.parseInt(temp1)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Tips(description = "使用apiCode代替platformVersion")
	public static boolean moreThanTargetSdkVersion(int apiCode) {
		if (AdbManager.getTargetSdk(AdbManager.udid) >= apiCode) {
			return true;
		} else {
			return false;
		}
	}

	public static void shakeDevice() {
		// DriverManger.getDriver().shakeDevice();
	}

	public static void openNotification() {
		App.NOTIFICATION_STATUS = true;
		DriverManger.getDriver().openNotifications();
	}

	/**
	 * @ 风险 不能确定是否下拉Notification，先取巧
	 */
	public static void closeNotification() {
		if (App.NOTIFICATION_STATUS)
			PageRouteUtil.pressBack();// 后退则会关闭Notification
		App.NOTIFICATION_STATUS = false;
	}

	public static String getDeviceTime() {
		return DriverManger.getDriver().getDeviceTime();
	}

	public static void getOrientation() {
		DriverManger.getDriver().getOrientation();
	}

	public static void lockDevice() {
		DriverManger.getDriver().lockDevice();
	}

	public static boolean getLockStatus() {
		return DriverManger.getDriver().isLocked();
	}

	public static void unLockDevice() {
		DriverManger.getDriver().unlockDevice();
	}

}
