package com.cmic.GoAppiumTest.helper;

import org.openqa.selenium.By;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @描述 通过清除缓存重入应用
 * @其他 会清除所有写入缓存的数据
 * @author kiwi
 */
public class PageRedirect {

	public static AndroidDriver<AndroidElement> driver = DriverManger.getDriver();

	/**
	 * 重定向到SplashActivit
	 */
	public static void redirect2SplashActivity() {
		AppUtil.clearAppData(App.PACKAGE_NAME);// 清除缓存
		AppUtil.launchApp();
		WaitUtil.implicitlyWait(1);
	}

	/**
	 * 重定向到RequestPermissionActivity
	 */
	public static void redirect2RequestPermissionActivity() {
		redirect2SplashActivity();
		// 点击同意并使用
		AndroidElement element = driver.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
		element.click();
		WaitUtil.implicitlyWait(1);// 等待1S
	}

	/**
	 * 重定向到RequestiteActivity
	 */
	public static void redirect2RequestiteActivity() {
		redirect2RequestPermissionActivity();
		AndroidElement buttonAllow = driver
				.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"));
		for (int i = 0; i < 4; i++) {
			buttonAllow.click();
			WaitUtil.implicitlyWait(1);// 等待1S
		}
		WaitUtil.implicitlyWait(2);// 等待1S
	}

	/**
	 * 重定向到MainActivity
	 */
	public static void redirect2MainActivity() {
        redirect2RequestiteActivity();
        AndroidElement mainButton = driver.findElement(By.id("com.cmic.mmnes:id/tv_main"));
		mainButton.click();
		WaitUtil.implicitlyWait(2);// 等待1S
	}

	/**
	 * 重定向到DownloadManagerActivity
	 * @throws InterruptedException 
	 */
	public static void redirect2DownloadManagerActivity() throws InterruptedException {
		redirect2MainActivity();
		WaitUtil.implicitlyWait(2);// 等待1S
		AndroidElement managerRly = driver.findElement(By.id("com.cmic.mmnes:id/managerview"));
		managerRly.click();
		WaitUtil.forceWait(2);
	}

	/**
	 * 重定向到SettingActivity
	 * @throws InterruptedException 
	 */
	public static void redirect2SettingActivity() throws InterruptedException {
		redirect2DownloadManagerActivity();
		WaitUtil.implicitlyWait(2);// 等待1S
		AndroidElement settingRly = driver.findElement(By.id("com.cmic.mmnes:id/setting_iv"));
		settingRly.click();
		WaitUtil.forceWait(2);
	}
	
	
	@SuppressWarnings("unused")
	private enum TargetActivity {
		SPLASH_ACTIVITY, // 闪屏页面
		REQUEST_PERMISSION_ACTIVITY, // 权限授权页面
		REQUESTITE_ACTIVITY, // 应用推荐页面
		MAIN_ACTIVITY, // 主页
		DOWNLOAD_MANAGER_ACTIVITY // 下载管理页面
	}

}
