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
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @TODO 后期可单独抽离并丰富双卡，不同模组的测试
 * @TODO 可和下载联合测试流量统计，但不够稳定
 */
public class TestTrafficManagerActivity {

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
	@Tips(description = "假设已经入MainAct&&未跳转到其他页面")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		PageRedirect.redirect2MainActivity();
		WaitUtil.implicitlyWait(2);// 等待1S
		AndroidElement managerRly = mDriver.findElement(By.id("com.cmic.mmnes:id/jump_ll"));
		managerRly.click();
		WaitUtil.forceWait(3);
		System.err.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.err.println("测试用例集[" + mTag + "]结束");
	}

	@Test
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.TrafficDetailActivity");
		ScreenUtil.screenShot("进入必备应用流量管家界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkTrafficInfo() throws InterruptedException {
		WaitUtil.forceWait(3);
		LogUtil.printCurrentMethodName();
		ScreenUtil.screenShot("查询页面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试滑动切换")
	public void checkSlipToOtherTab() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(2);
		ScreenUtil.screenShot("滑动-切换到按类型查看");
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		WaitUtil.forceWait(2);
		ScreenUtil.screenShot("滑动-切换到按套餐查看");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试点击切换")
	public void checkClick2OtherTab() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement tabStripType = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"按类型查看\")");
		tabStripType.click();
		ScreenUtil.screenShot("切换到按类型查看");
		WaitUtil.implicitlyWait(2);
		AndroidElement tabStripOrder = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"按套餐查看\")");
		tabStripOrder.click();
		ScreenUtil.screenShot("点击-切换到按套餐查看");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试下拉刷新", riskPoint = "截图不一定准确，需要调整")
	public void checkDropDown2Refesh() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		int width = mDriver.manage().window().getSize().width;
		int height = mDriver.manage().window().getSize().height;
		mDriver.swipe(width / 2, height / 2, width / 2, height / 2 + 30, 500);
		ScreenUtil.screenShot("小滑动未触发加载");
		WaitUtil.forceWait(2);
		mDriver.swipe(width / 2, height / 2, width / 2, height / 4 * 3, 500);
		ScreenUtil.screenShot("大滑动触发加载");
		WaitUtil.forceWait(2);
	}
}
