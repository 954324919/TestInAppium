package com.cmic.GoAppiumTest.testcase;
/**
 * 工信部弹窗+
 * @时机 工信部弹窗在第一次启动（同意之后不影响权限）
 */

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
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.sun.jna.platform.win32.ShellAPI.APPBARDATA;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @描述 默认初次进入既是SplashActivity，不是则跳过后续测试
 * @时机 1、初次进入首页 2、进入首页 而没有点击不再提示
 * @author kiwi
 */
public class TestSplashActivity {

	private String mTag;
	private AndroidDriver<AndroidElement> mDriver;

	@BeforeMethod
	public void tipBeforeTestCase() {
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
		System.out.println("测试用例集[" + mTag + "]开始");
		// 屏幕截图
		ScreenUtil.screenShot("进入SplashActivity");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test(enabled=false)
	public void initCheck() throws Exception {// 0
		// 确认为SplashActivity
		System.out.println("进行[" + mTag + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		String currentActivityName = ContextUtil.getCurrentActivity();
		// TODO 先试探错误的情况
		Assert.assertEquals(currentActivityName, ".activity.SplashActivity");
		//TODO 测试的一个良好介入点
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkNoNotify() throws InterruptedException {// 1
		System.out.println("checkNoNotify");
		// TODO 不再提示需要测试重入，从进入首页后返回检验
		WaitUtil.forceWait(1);
		AndroidElement element = mDriver.findElementById("com.cmic.mmnes:id/cb_repeat");
		if (element != null)
			element.click();
		WaitUtil.forceWait(1);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkDenyProcotol() {// 2
		System.out.println("checkDenyProcotol");
		// 点击拒绝协议
		AndroidElement element = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_refuse"));
		element.click();
		WaitUtil.implicitlyWait(3);// 等待1S
		// 屏幕截图
		ScreenUtil.screenShot("点击拒绝协议");
		WaitUtil.implicitlyWait(1);// 等待1S
		// 重入应用
		AppUtil.launchApp();
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkScrollProcotol() {// 3
		System.out.println("checkScrollProcotol");
		// 滑动协议至其底部
		By by = By.className("android.widget.ScrollView");
		ElementUtil.swipeControl(by, com.cmic.GoAppiumTest.helper.Heading.DOWN);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkComfirmProcotol() {// 4
		System.out.println("checkComfirmProcotol....");
		// 点击同意并使用
		AndroidElement element = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
		element.click();
		WaitUtil.implicitlyWait(1);// 等待1S
	}
}
