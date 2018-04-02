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
import com.cmic.GoAppiumTest.util.KeyboardUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 包含搜索页面的一些非常规测试，包括联想和搜索结果，这两者结合起来测试比较方便，故放在一起
 * 
 * @风险 0403发现AutoCompleteTextView无法定位，考虑使用坐标定位，及其不稳定
 * @author kiwi
 */
public class TestSearchActivityExtra {
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

	@Test(dependsOnMethods = { "initCheck" }, timeOut = 15000)
	@Tips(description = "热搜联想")
	public void checkSearchRalation() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		WaitUtil.forceWait(2);
		AndroidElement et = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
		et.click();// 点击出现搜索联想
		ScreenUtil.screenShot("联想搜索前...");
		et.sendKeys("移动");
		WaitUtil.forceWait(1500);
		ScreenUtil.screenShot("联想搜索后...");
		KeyboardUtil.hideKeyBoard();
	}

	@Test(dependsOnMethods = { "initCheck" }, timeOut = 15000)
	@Tips(description = "点击下载", //
			riskPoint = "由于自动补全控件无法定位，当前预期先使用坐标定位，待解决。不抛出异常，只做正向验证")
	public void checkClickDownload() {
		LogUtil.printCurrentMethodName();
		AndroidElement element = mDriver.findElementByClassName("android.widget.ListView");
		System.out.println(element.getCoordinates().toString());
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkClick2DetailByRalationItem() {
		LogUtil.printCurrentMethodName();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkSearchHistory() {
		LogUtil.printCurrentMethodName();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkEnterDetailFromHistory() {
		LogUtil.printCurrentMethodName();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkClearHistory() {
		LogUtil.printCurrentMethodName();
	}
}
