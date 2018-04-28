package com.cmic.GoAppiumTest.helper;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
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
		// AppUtil.resetApp();
		WaitUtil.implicitlyWait(1);
	}

	/**
	 * 重定向到RequestPermissionActivity
	 */
	public static void redirect2RequestPermissionActivity() {
		redirect2SplashActivity();
		WaitUtil.implicitlyWait(5);// 等待1S
		// 点击同意并使用
		AndroidElement element = driver.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
		element.click();
	}

	/**
	 * 重定向到RequestiteActivity
	 */
	public static void redirect2RequestiteActivity() {
		redirect2RequestPermissionActivity();
		WaitUtil.implicitlyWait(5);// 等待1S
		AndroidElement buttonAllow = driver
				.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"));
		for (int i = 0; i < 4; i++) {
			buttonAllow.click();
			WaitUtil.implicitlyWait(1);// 等待1S
		}
	}

	@Tips(description = "到达流量管家页面")
	public static void redirect2TrafficManagerActivity() {
		if (ContextUtil.getCurrentActivity().equals(".activity.MainActivity")) {// MainAct
			WaitUtil.implicitlyWait(2);// 等待1S
			AndroidElement managerRly = driver.findElement(By.id("com.cmic.mmnes:id/jump_ll"));
			managerRly.click();
		} else {
			PageRedirect.redirect2MainActivity();
			WaitUtil.implicitlyWait(2);// 等待1S
			AndroidElement managerRly = driver.findElement(By.id("com.cmic.mmnes:id/jump_ll"));
			managerRly.click();
		}
	}

	public static void redirect2DetailActivity() {
		PageRedirect.redirect2MainActivity();
		Random randomIndex = new Random();// 主页显示16个Item
		int index = 1 + randomIndex.nextInt(14);
		// 定位点击
		WaitUtil.implicitlyWait(10);
		List<AndroidElement> eList = driver.findElements(By.id("com.cmic.mmnes:id/index_item_rl"));
		AndroidElement e = eList.get(index);
		WaitUtil.implicitlyWait(2);
		e.click();
	}

	@Tips(description = "到达关于页面=")
	public static void redirect2AboutActivity() {
		if (ContextUtil.getCurrentPageActivtiy().equals("AboutActivity")) {
			return;
		} else if (ContextUtil.getCurrentPageActivtiy().equals("SettingActivity")) {
			// 不操作
		} else {
			redirect2SettingActivity();
		}
		WaitUtil.implicitlyWait(2);// 等待1S
		AndroidElement aboutLly = driver.findElement(By.id("com.cmic.mmnes:id/ll_about"));
		aboutLly.click();

	}

	/**
	 * 重定向到MainActivity
	 */
	public static void redirect2MainActivity() {
		redirect2RequestiteActivity();
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		AndroidElement mainButton = driver.findElement(By.id("com.cmic.mmnes:id/tv_main"));
		mainButton.click();
		// 等待1S
	}

	/**
	 * 重定向到DownloadManagerActivity
	 * 
	 * @throws InterruptedException
	 */
	public static void redirect2DownloadManagerActivity() {
		redirect2MainActivity();
		WaitUtil.implicitlyWait(5);// 等待1S
		AndroidElement managerRly = driver.findElement(By.id("com.cmic.mmnes:id/managerview"));
		managerRly.click();
		WaitUtil.forceWait(2);
	}

	/**
	 * 定位到权限管理的Ralation页面
	 */
	public static void redirect2PermissionRalation() {
		redirect2RequestPermissionActivity();
		WaitUtil.implicitlyWait(5);
		AndroidElement buttonAllow = driver
				.findElement(By.id("com.android.packageinstaller:id/permission_deny_button"));
		for (int i = 0; i < 4; i++) {
			buttonAllow.click();
			WaitUtil.forceWait(1);
		}
		WaitUtil.forceWait(2);
	}

	/**
	 * 重定向到SettingActivity
	 * 
	 * @throws InterruptedException
	 */
	public static void redirect2SettingActivity() {
		redirect2DownloadManagerActivity();
		WaitUtil.implicitlyWait(5);// 等待1S
		AndroidElement settingRly = driver.findElement(By.id("com.cmic.mmnes:id/setting_iv"));
		settingRly.click();
		WaitUtil.forceWait(2);
	}

	public static void rediret2ShareActivity() {
		if (!ContextUtil.getCurrentPageActivtiy().equals("SettingActivity")) {
			redirect2SettingActivity();
		}
		WaitUtil.implicitlyWait(2);
		AndroidElement shareLly = driver.findElement(By.id("com.cmic.mmnes:id/rl_share"));
		shareLly.click();
		WaitUtil.forceWait(3);

	}

	public static void redirect2SearchActivity() {
		redirect2MainActivity();
		WaitUtil.implicitlyWait(5);// 等待1S
		AndroidElement searchLayout = driver.findElement(By.id("com.cmic.mmnes:id/search_layout"));
		searchLayout.click();
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
