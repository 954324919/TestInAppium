package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @描述 推荐页面
 * @时机 确认授予所有权限，第一次进入应用
 * @其他 未完成无网络的状态确认
 * @author kiwi
 */
public class TestRequisiteActivity {
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
		WaitUtil.implicitlyWait(3);// 等待1S
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
		// AdbManager.excuteAdbShell("adb uninstall com.cmic.mmnes");
	}

	@Test
	public void initCheck() throws InterruptedException {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.RequisiteActivity");
		ScreenUtil.screenShot("进入装机必备界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkOneSelect() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkAllSelect() {

	}

	@Test(dependsOnMethods = { "initCheck" })
	public void futureHope() {
		PageRedirect.redirect2RequestiteActivity();
		;// 等待1S
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkBackForward() {// 一旦回退，只能回到首页
		WaitUtil.implicitlyWait(2);// 等待1S
		AppUtil.closeApp();
		WaitUtil.implicitlyWait(2);// 等待1S
		ContextUtil.goTargetActivity(App.PACKAGE_NAME, ".activity.RequisiteActivity");
		WaitUtil.implicitlyWait(2);// 等待1S
	}
}
