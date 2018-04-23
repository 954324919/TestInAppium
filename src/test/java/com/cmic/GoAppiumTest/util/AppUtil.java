package com.cmic.GoAppiumTest.util;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

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
			WaitUtil.implicitlyWait(5);
			AndroidElement remoteAppWidget = DriverManger.getDriver()
					.findElement(By.xpath("//android.widget.TextView[@text='必备应用' and @content-desc='必备应用']"));
			remoteAppWidget.click();
		} catch (NoSuchElementException e) {
			Assert.assertEquals(true, false);
		}
	}

	@Tips(description = "后台运行")
	public static void runInBackground4AWhile() {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		driver.pressKeyCode(AndroidKeyCode.HOME);
		try {
			WaitUtil.implicitlyWait(2);
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

	public static void unInstall(String packageName) {
		AdbManager.excuteAdbShell("adb uninstall " + packageName);
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

	public static void killApp(String packageName) {
		AdbManager.excuteAdbShell("adb shell am force-stop " + packageName);
	}
	
	public static boolean isInstallWithoutDriver(String packageName) {
		String result = AdbManager.excuteAdbShellGetResultGrep("adb shell pm list packages", "com.cmic.mmnes");
		return !(result.isEmpty());
	}

	public static void handleInfoSwitch2Native() {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			// 用于返回被测app是NATIVE_APP还是WEBVIEW，如果两者都有就是混合型App
			if (!contextName.contains("WEBVIEW")) {
				// 让appium切换到webview模式以便查找web元素
				driver.context(contextName);
				System.out.println("切换到：" + contextName);
				break;
			}
		}
	}

	public static void handleInfoSwitch2Webview() {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		Set<String> contextNames = driver.getContextHandles();
		System.out.println(contextNames.size());
		for (String contextName : contextNames) {
			// 用于返回被测app是NATIVE_APP还是WEBVIEW，如果两者都有就是混合型App
			if (contextName.contains("WEBVIEW")) {
				// 让appium切换到webview模式以便查找web元素
				driver.context(contextName);
				System.out.println("切换到：" + contextName);
				break;
			}
		}
	}

	public static HandlerStatus getHandleStatus() {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		Set<String> contextNames = driver.getContextHandles();
		System.out.println(contextNames.size());
		for (String contextName : contextNames) {
			// 用于返回被测app是NATIVE_APP还是WEBVIEW，如果两者都有就是混合型App
			if (contextName.contains("WEBVIEW")) {
				return HandlerStatus.HYBRID;
			}
		}
		return HandlerStatus.NATIVE;
	}

	public enum HandlerStatus {
		HYBRID, // 混合应用，包含Webview
		NATIVE// 原生应用，不包含Webview
	}

	public static int getStatuBar() {// 状态栏高度
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		AndroidElement actionBarLly = driver.findElementById("com.cmic.mmnes:id/bg_ll");
		return actionBarLly.getRect().getHeight();
	}

	public static int getActionBar() {// ActionBar高度||Search栏高度
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		AndroidElement actionBarLly = driver.findElementById("com.cmic.mmnes:id/search_title");
		return actionBarLly.getRect().getHeight();
	}
}
