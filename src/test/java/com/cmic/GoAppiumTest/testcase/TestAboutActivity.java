package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;
@Listeners(ExtentReportListener.class)
public class TestAboutActivity extends BaseTest{

	@Tips(description = "继承自BaseActivity,用于增强@BeforeClass",triggerTime="假设已经入Setting&&未跳转到其他页面")
	@Override
	public void setUpBeforeClass() {
		WaitUtil.implicitlyWait(2);
		AndroidElement aboutLly = mDriver.findElement(By.id("com.cmic.mmnes:id/ll_about"));
		aboutLly.click();
	}

	@Tips(description = "继承自BaseActivity,用于增强@AfterClass")
	@Override
	public void tearDownAfterClass() {
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

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.AboutActivity");
		ScreenUtil.screenShot("进入必备应用关于界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "检查关于应用-更新功能", riskPoint = "0404只能检查是最新版本")
	public void checkAboutUpdate() throws InterruptedException {//
		WaitUtil.implicitlyWait(2);
		LogUtil.printCurrentMethodName();
		AndroidElement aboutUpdateLly = mDriver.findElement(By.id("com.cmic.mmnes:id/about_check_update_layout"));
		aboutUpdateLly.click();
		// 抓取Toast
		// 如果抓不到就判断是否有软件更新
		String targetToast = "已经是最新版本";
		Assert.assertEquals(ElementUtil.isTargetToast(targetToast), true);
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkUserProtocol() {
		WaitUtil.implicitlyWait(2);
		LogUtil.printCurrentMethodName();
		AndroidElement aboutProtocolLly = mDriver.findElement(By.id("com.cmic.mmnes:id/about_user_protocol_layout"));
		aboutProtocolLly.click();
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.ProtocalActivity");
		ScreenUtil.screenShot("用户协议显示");
	}

	@Test(dependsOnMethods = { "checkUserProtocol" })
	public void rollAndClose() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		ScrollUtil.scrollToBase();
		WaitUtil.forceWait(2);
		mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.AboutActivity");
	}
}
