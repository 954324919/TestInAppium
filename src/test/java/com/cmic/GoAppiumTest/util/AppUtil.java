package com.cmic.GoAppiumTest.util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;

import io.appium.java_client.android.AndroidElement;

public class AppUtil {
	// 先closeApp然后在launchAPP,此类方法都一定程度破坏了App的完整性
	public static void resetApp() {
		DriverManger.getDriver().resetApp();
	}

	@Tips(description = "直接点击launcher屏幕启动应用，而非采用强制启动launcherActivity的方法", //
			riskPoint = "Launcher页面没有切换到被测应用的页面")
	public static void softResetApp() {
		DriverManger.getDriver().closeApp();
		try {
			AndroidElement remoteAppWidget = DriverManger.getDriver()
					.findElement(By.xpath("//android.widget.TextView[@text='必备应用' and @content-desc='必备应用']"));
			remoteAppWidget.click();
		} catch (NoSuchElementException e) {
			Assert.assertEquals(true, false);
		}
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
	 * 
	 * @param packageName
	 */
	public static void clearAppData(String packageName) {
		System.err.println("清除应用缓存并杀死应用进程");
		AdbManager.excuteAdbShell("adb shell pm clear " + packageName);
		WaitUtil.implicitlyWait(2);
	}

	public static boolean isInstall(String packageName) {
        return DriverManger.getDriver().isAppInstalled(packageName);
	}

	public static void isInstall1(String packageName) {
        
	}
}
