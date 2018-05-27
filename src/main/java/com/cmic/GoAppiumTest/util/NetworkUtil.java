package com.cmic.GoAppiumTest.util;

import com.cmic.GoAppiumTest.base.DriverManger;
import io.appium.java_client.android.Connection;

public class NetworkUtil {
	public static Connection getNetworkState() {
		// 返回类似NetworkState
		Connection cn = DriverManger.getDriver().getConnection();
		return cn;
	}

	/***
	 * 检查网络
	 * 
	 * @return 是否正常
	 */
	public static boolean checkNet() {
		String text = DriverManger.getDriver().getConnection().toString();
		if (text.contains("Data: true"))
			return true;
		else
			return false;
	}

	public enum NetworkState {
		Airplane, Nonetwork, MobileNetwork, Wifi, UnSpecified // 不确定
	}
}
