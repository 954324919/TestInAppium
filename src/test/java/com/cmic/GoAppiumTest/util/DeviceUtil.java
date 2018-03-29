package com.cmic.GoAppiumTest.util;

import org.openqa.selenium.Capabilities;

import com.cmic.GoAppiumTest.base.DriverManger;

public class DeviceUtil {

	public static Capabilities capabilities = DriverManger.getDriver().getCapabilities();

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
	 * @param currentSdk 当前
	 * @param targetSdk  目标
	 * @return
	 */
	public static boolean moreThanTargetSdkVersion(String targetSdk) {
		String currentSdk = getDeviceVersion();//7.1.2
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
}
