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
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 当前0405从SearchActivity中进入
 * 
 * @author kiwi
 */
public class TestSearchResultActivity {
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
		PageRedirect.redirect2SearchActivity();
		AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
		searchEt.click();
		searchEt.clear();
		searchEt.sendKeys("和飞信");
		WaitUtil.implicitlyWait(2);
		mDriver.findElement(By.id("com.cmic.mmnes:id/search_icon_layout")).click();
		WaitUtil.forceWait(2);
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test(enabled = false)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SearchActivity");
		boolean isPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/search_count_tv"));
		assertEquals(isPresent, true);
		ScreenUtil.screenShot("进入必备搜索结果界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkSild2OtherTab() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkClick2OtherTab() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkRandomClick2Detail() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkRandomClick2Download() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkDownloadButtonUpdate() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkSearch2Baseline() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkErrorInput4EmptyPage() {

	}
}
