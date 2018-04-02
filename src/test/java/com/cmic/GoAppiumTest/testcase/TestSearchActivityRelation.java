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
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestSearchActivityRelation {
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
	@Tips(description = "假设已经在MainAct", riskPoint = "耦合度暂不考虑，从MainTest完成进入")
	public void beforeClass() {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test
	public void initCheck() throws InterruptedException {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SearchActivity");
		ScreenUtil.screenShot("进入必备应用搜索界面");
	}
	

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "热搜联想")
	public void checkSearchRalation() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		WaitUtil.forceWait(2);
		AndroidElement et = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
		et.click();//点击出现搜索联想
		ScreenUtil.screenShot("联想搜索前...");
		et.sendKeys("移动");
		WaitUtil.forceWait(1500);
		ScreenUtil.screenShot("联想搜索后...");
	}
}
