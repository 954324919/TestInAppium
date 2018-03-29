package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @描述 权限管理弹窗(系统)+权限细则
 * @时机 安卓系统大于6.0（API23），且@TargetAPI>=23
 * @author ikiwi
 */
public class TestGrantPremissionActivity {
	private String mTag;
	private AndroidDriver<AndroidElement> mDriver;

	@BeforeMethod
	public void tipBeforeTestCase() {
		// 点击同意并使用
		System.out.println("测试用例[" + (++App.CASE_COUNT) + "]开始");
	}

	@AfterMethod
	public void tipAfterTestCase() {
		System.out.println("测试用例[" + (App.CASE_COUNT) + "]结束");
	}

	@BeforeClass
	public void beforeClass() {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		AndroidElement element = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
		element.click();
		WaitUtil.implicitlyWait(1);// 等待1S
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test
	public void initCheck() throws InterruptedException {// 1
		System.out.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(".permission.ui.GrantPermissionsActivity", ContextUtil.getCurrentActivity());
		assertEquals(true, DeviceUtil.moreThanTargetSdkVersion("6.0.0"));//
		WaitUtil.forceWait(3);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void chekNotNotify() throws InterruptedException {// 点击不再询问弹窗
		AndroidElement notifyCheckbox = mDriver
				.findElement(By.id("com.android.packageinstaller:id/do_not_ask_checkbox"));
		notifyCheckbox.click();
		WaitUtil.forceWait(1);
		// 回复状态
		notifyCheckbox.click();
		WaitUtil.forceWait(1);
		// 回复状态,影响之后的其他测试
		notifyCheckbox.click();
		WaitUtil.forceWait(1);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void denyAllPremission() throws InterruptedException {// 2
		// 必备1.5已知拥有4个高级权限
		AndroidElement buttonAllow = mDriver
				.findElement(By.id("com.android.packageinstaller:id/permission_deny_button"));
		for (int i = 0; i < 4; i++) {
			buttonAllow.click();
			WaitUtil.forceWait(1);
		}
	}

	@Test(dependsOnMethods = { "denyAllPremission" }, enabled = false)
	public void checkShowPermissionRalation() throws InterruptedException {
		// 弹窗页面
		// 截图
		WaitUtil.forceWait(1);
		ScreenUtil.screenShot("权限弹窗");
		Assert.assertEquals(ContextUtil.getCurrentActivity(), ".activity.SplashActivity");
		System.out.println("进入权限弹窗界面");
	}

	@Test(dependsOnMethods = { "denyAllPremission" }, enabled = false)
	public void checkGetPermissionDetail() throws InterruptedException {// 点击权限获取说明
		AndroidElement showDetailButton = mDriver.findElement(By.id("com.cmic.mmnes:id/show_pre_text"));
		showDetailButton.click();
		// 截图
		WaitUtil.forceWait(1);
		ScreenUtil.screenShot("权限获取说明点击,显示列表");
		showDetailButton.click();
		WaitUtil.forceWait(1);
		// 截图
		ScreenUtil.screenShot("权限获取说明二次点击，收起列表");
	}

	@Test(dependsOnMethods = { "denyAllPremission" }, enabled = false)
	public void checkPermissionRefuse() throws InterruptedException {// 点击含泪拒绝
		// 点击退出应用
		AndroidElement showDetailButton = mDriver.findElement(By.id("com.cmic.mmnes:id/mm_continue"));
		showDetailButton.click();
		// 重入应用，再次获取权限
		AppUtil.launchApp();
		// 返回之前的页面
		AndroidElement buttonAllow = mDriver
				.findElement(By.id("com.android.packageinstaller:id/permission_deny_button"));
		for (int i = 0; i < 4; i++) {
			buttonAllow.click();
			WaitUtil.forceWait(1);
		}
		Assert.assertEquals(ContextUtil.getCurrentActivity(), ".activity.SplashActivity");
	}

	/**
	 * 点击再次获取,会进入应用信息管理页面（N3）
	 * 
	 * @throws InterruptedException
	 */
	@Test(dependsOnMethods = { "denyAllPremission" }, enabled = false)
	public void checkPermissionRetry() throws InterruptedException {//
		AndroidElement showDetailButton = mDriver.findElement(By.id("com.cmic.mmnes:id/mm_getagain"));
		showDetailButton.click();
		WaitUtil.forceWait(2);
		// PageRouteUtil.pressBack();
		AppUtil.resetApp();
		WaitUtil.forceWait(2);
		Assert.assertEquals(ContextUtil.getCurrentActivity(), ".activity.SplashActivity");
		System.out.println("重新回到权限弹窗界面");
		// 点击工信部弹窗
		AndroidElement element = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
		element.click();
		WaitUtil.implicitlyWait(1);// 等待1S
		assertEquals(".permission.ui.GrantPermissionsActivity", ContextUtil.getCurrentActivity());
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void allowAllPremission() throws InterruptedException {// 必备1.5已知拥有4个高级权限
		AndroidElement buttonAllow = mDriver
				.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"));
		for (int i = 0; i < 4; i++) {
			buttonAllow.click();
			WaitUtil.forceWait(1);
		}
		// 进入首页，进行截图
		ScreenUtil.screenShot("进入推荐页");
		WaitUtil.forceWait(2);
	}
}
