package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestSettingActivity {
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
	@Tips(description = "假设已经入ManagerAct&&未跳转到其他页面")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		PageRedirect.redirect2SettingActivity();
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SettingActivity");
		ScreenUtil.screenShot("进入必备应用设置中心界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" },enabled=false)
	@Tips(description = "检查分享应用功能")
	public void shareTheApp() throws InterruptedException {
		goShare();
		LogUtil.printCurrentMethodName();
		// TODO 检查影响，0404暂不实现
		boolean shareLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/tv_cancel"));
		assertEquals(shareLlyIsPresent, true);
		if (shareLlyIsPresent) {
			PageRouteUtil.pressBack();
		}
		WaitUtil.forceWait(1);
	}
	
	public void goShare(){
		WaitUtil.implicitlyWait(2);
		AndroidElement shareLly = mDriver.findElement(By.id("com.cmic.mmnes:id/rl_share"));
		shareLly.click();
		try {
			WaitUtil.forceWait(4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(dependsOnMethods = { "shareTheApp" },enabled=false)
	public void shareByLink() throws InterruptedException {
		goShare();
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement shareByLinkLly = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_copy"));
		shareByLinkLly.click();

	}

	// 在0404，赞不检查其复制的字段
	@Test(dependsOnMethods = { "shareByLink" },enabled=false)
	@Tips(description = "测试更多分享|目前只有短信分享的方式", riskPoint = "")
	public void shareByMore() throws InterruptedException {
		goShare();
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement shareByMoreLly = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_share"));
		shareByMoreLly.click();
		// 抓取Toast
		WaitUtil.implicitlyWait(2);
		AndroidElement shareByMessageLly = mDriver.findElement(By.id("com.cmic.mmnes:id/item_layout"));
		shareByMessageLly.click();
		//
		WaitUtil.forceWait(2);
		String targetPackageName = ContextUtil.getPackageName();
		assertEquals(targetPackageName != App.PACKAGE_NAME, true);
		AppUtil.killApp(targetPackageName);
		PageRouteUtil.pressHome();
		WaitUtil.forceWait(2);
		AppUtil.softResetApp();
	}

	@Test(dependsOnMethods = { "initCheck" },enabled=false)
	@Tips(description = "检查自更新设置的影响")
	public void checkAutoUpdate() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement updateSetting = mDriver.findElement(By.id("com.cmic.mmnes:id/setting_zero_layout"));
		updateSetting.click();
		// TODO 检查影响，0404暂不实现
		WaitUtil.forceWait(1);
		updateSetting.click();
		// TODO 检查影响，0404暂不实现
		WaitUtil.forceWait(1);
	}

	@Test(dependsOnMethods = { "initCheck" },enabled=false)
	@Tips(description = "检查下载提示的影响")
	public void downloadTipShow() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement notifyLly = mDriver.findElement(By.id("com.cmic.mmnes:id/setting_download_notice_layout"));
		notifyLly.click();
		// TODO 检查影响，0404暂不实现
		WaitUtil.forceWait(1);
		boolean notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, true);
		if (notifyLlyIsPresent) {
			PageRouteUtil.pressBack();
		}
		WaitUtil.forceWait(1);
	}

	@Test(dependsOnMethods = { "downloadTipShow" },enabled=false)
	@Tips(description = "检查下载提示的影响")
	public void downloadTipCloseInOtherWay() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement notifyLly = mDriver.findElement(By.id("com.cmic.mmnes:id/setting_download_notice_layout"));
		// 重新点击显示NotifyDialog
		notifyLly.click();
		WaitUtil.forceWait(1);
		boolean notifyLlyIsPresent;
		notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, true);
		AndroidElement clearIcon = mDriver.findElement(By.id("com.cmic.mmnes:id/close_iv"));
		clearIcon.click();
		WaitUtil.forceWait(1);
		notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, false);
		// 重新点击显示NotifyDialog
		notifyLly.click();
		WaitUtil.forceWait(1);
		notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, true);
		AndroidElement cancelIcon = mDriver.findElement(By.id("com.cmic.mmnes:id/mm_dialog_cancel"));
		cancelIcon.click();
		WaitUtil.forceWait(1);
		notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, false);
	}

	@Test(dependsOnMethods = { "downloadTipShow" },enabled=false)
	@Tips(description = "检查下载提示的影响")
	public void checkSetTheRange() {//
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement notifyContentTv = mDriver
				.findElement(By.id("com.cmic.mmnes:id/setting_download_notice_content"));
		System.out.println(notifyContentTv.getText().replaceAll("[^0-9]", ""));
	}

	
	
}
