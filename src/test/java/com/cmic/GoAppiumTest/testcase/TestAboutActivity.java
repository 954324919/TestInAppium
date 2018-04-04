package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestAboutActivity {
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
	@Tips(description = "假设已经入Setting&&未跳转到其他页面")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		WaitUtil.implicitlyWait(2);
		AndroidElement aboutLly = mDriver.findElement(By.id("com.cmic.mmnes:id/ll_about"));
		aboutLly.click();
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
		//TODO 未来可采取这种形式
		//退回SettingActivity
		if (ContextUtil.getCurrentActivity().equals(".activity.AboutActivity")) {
			PageRouteUtil.pressBack();
		} else if (ContextUtil.getCurrentActivity().equals(".activity.ProtocalActivity")) {
			PageRouteUtil.pressBack();
			WaitUtil.forceWait(2);
			PageRouteUtil.pressBack();
			WaitUtil.forceWait(2);
		} else if (ContextUtil.getCurrentActivity().equals(".activity.SettingActivity")) {
			return;
		}
	}

	@Test(enabled = false)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.AboutActivity");
		ScreenUtil.screenShot("进入必备应用关于界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查关于应用-更新功能", riskPoint = "0404只能检查是最新版本")
	public void checkAboutUpdate() throws InterruptedException {//
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement aboutUpdateLly = mDriver.findElement(By.id("com.cmic.mmnes:id/about_check_update_layout"));
		aboutUpdateLly.click();
		// 抓取Toast
		// 如果抓不到就判断是否有软件更新
		String targetToast = "已经是最新版本";
		Assert.assertEquals(ElementUtil.isTargetToast(targetToast), true);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkUserProtocol() {
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement aboutProtocolLly = mDriver.findElement(By.id("com.cmic.mmnes:id/about_user_protocol_layout"));
		aboutProtocolLly.click();
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.ProtocalActivity");
		ScreenUtil.screenShot("用户协议显示");
	}

	@Test(dependsOnMethods = { "checkUserProtocol" }, enabled = false)
	public void rollAndClose() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		ScrollUtil.scrollToBase();
		WaitUtil.forceWait(2);
		mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.AboutActivity");
	}
}
