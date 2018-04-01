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
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestMainActivity {
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
		WaitUtil.implicitlyWait(2);// 等待1S
		PageRedirect.redirect2MainActivity();
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
		// AdbManager.excuteAdbShell("adb uninstall com.cmic.mmnes");
	}

	@Test
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		ScreenUtil.screenShot("进入必备应用主页界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void testNotification() {
		DeviceUtil.openNotification();
		WaitUtil.implicitlyWait(2);
		DeviceUtil.closeNotification();
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试点击跳转其他Tab(软件-游戏)", //
			riskPoint = "UI变动")
	public void checkClick2OtherTab() {//
		// 全路径xpath获取游戏Tab
		String gameUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\")"
				+ ".childSelector(new UiSelector().textContains(\"游戏\"))";
		AndroidElement topGameTab = mDriver.findElementByAndroidUIAutomator(gameUiSelector);
		topGameTab.click();
		WaitUtil.implicitlyWait(2);
		// // 全路径xpath获取软件Tab
		String softwareUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\")"
				+ ".childSelector(new UiSelector().textContains(\"软件\"))";
		AndroidElement topSoftwareTab = mDriver.findElementByAndroidUIAutomator(softwareUiSelector);
		topSoftwareTab.click();
		WaitUtil.implicitlyWait(2);
		// TODO 必要时截图
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "测试滑动跳转其他Tab(软件-游戏)", //
			riskPoint = "UI变动")
	public void testSlip2OtherTab() {
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		WaitUtil.implicitlyWait(2);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "测试精选福利", riskPoint = "页面变动")
	public void testWellSelect() {// 测试精选福利
		AndroidElement wellSelectItemTv = mDriver.findElement(By.xpath(
				"//android.widget.FrameLayout[1]/android.support.v4.view.ViewPager[1]/android.support.v7.widget.RecyclerView[1]/android.widget.RelativeLayout[1]/android.widget.TextView[1]"));
		System.out.println(wellSelectItemTv.getText());
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "测试点击精品应用", riskPoint = "页面变动", triggerTime = "需要切换到游戏的Tab")
	public void checkGreatGame() {
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 50);
		WaitUtil.implicitlyWait(2);
		AndroidElement wellSelectItemTv = mDriver.findElement(By.xpath(
				"//android.widget.FrameLayout[1]/android.support.v4.view.ViewPager[1]/android.support.v7.widget.RecyclerView[1]/android.widget.RelativeLayout[1]/android.widget.TextView[1]"));
		System.out.println(wellSelectItemTv.getText());
		WaitUtil.implicitlyWait(2);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 50);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void scroll2Bottom() {// 测试滑动到底部
		ScrollUtil.scrollToBase();
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(riskPoint = "非全Xpath,UI变动的风险")
	public void refreshBatch() {// 点击换一批
		ScrollUtil.scrollToElement("//android.widget.TextView[@text='更多软件']");
	}
}
