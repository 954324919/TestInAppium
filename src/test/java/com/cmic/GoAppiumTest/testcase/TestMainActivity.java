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
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
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

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
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
	public void testSlip2OtherTab() throws InterruptedException {
		WaitUtil.forceWait(5);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(5);
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		WaitUtil.forceWait(5);
		// TODO 必要时截图
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试精选福利", riskPoint = "页面变动||MM的安装状态，目前只假定其安装和没安装两种情况")
	public void testWellSelect() throws InterruptedException {// 测试精选福利
		String wellSelectorUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\").textContains(\"精选福利\")";
		AndroidElement wellSelectItemTv = mDriver.findElementByAndroidUIAutomator(wellSelectorUiSelector);
		if (AppUtil.isInstall(App.MM_PACKAGE_NAME)) {
			System.out.println("Hi");
			wellSelectItemTv.click();
			WaitUtil.forceWait(1500);
			AppUtil.killApp(App.MM_PACKAGE_NAME);// 杀死MM
			// TODO 截图
		} else {
			System.out.println("He");
			wellSelectItemTv.click();
			AppUtil.handleInfoSwitch2Native();
			// RiskPoint 不一定进来就是BS页面,当前是这个情况，用Back返回
			// TODO 截图
			// WaitUtil.forceWait(1000); //Sleep有找不到控件的风险
			mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
		}
	}

	@Test(dependsOnMethods = { "testWellSelect" })
	@Tips(description = "测试点击精品应用", riskPoint = "页面变动", triggerTime = "需要切换到游戏的Tab")
	public void checkGreatGame() throws InterruptedException {
		WaitUtil.forceWait(5000);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 1000);
		String greatGameUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\").textContains(\"品牌游戏\")";
		AndroidElement greatGameTv = mDriver.findElementByAndroidUIAutomator(greatGameUiSelector);
		if (AppUtil.isInstall(App.MM_PACKAGE_NAME)) {
			System.out.println("Hi");
			greatGameTv.click();
			WaitUtil.forceWait(1500);
			AppUtil.killApp(App.MM_PACKAGE_NAME);// 杀死MM
			// TODO 截图
		} else {
			System.out.println("He");
			greatGameTv.click();
			WaitUtil.forceWait(3500);
			// RiskPoint 不一定进来就是BS页面,当前是这个情况，用Back返回
			ScreenUtil.screenShot("精选福利BS");
			PageRouteUtil.pressBack();
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void scroll2Bottom() {// 测试滑动到底部
		ScrollUtil.scrollToBase();
	}

	//TODO 从这里开始
	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(riskPoint = "非全Xpath,UI变动的风险")
	public void refreshBatch() {// 点击换一批
		ScrollUtil.scrollToElement("//android.widget.TextView[@text='更多软件']");
	}
}
