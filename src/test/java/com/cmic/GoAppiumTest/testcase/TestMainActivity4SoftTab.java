package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

@Listeners(ExtentReportListener.class)
public class TestMainActivity4SoftTab extends BaseTest {

	@Override
	public void setUpBeforeClass() {
		PageRedirect.redirect2MainActivity();
	}

	@Override
	public void tearDownAfterClass() {
	}

	@Test
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		ScreenUtil.screenShot("进入必备应用主页界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "测试点击跳转其他Tab(软件-游戏)", //
			riskPoint = "UI变动")
	public void checkClick2OtherTab() throws InterruptedException {//
		// 全路径xpath获取游戏Tab
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		String gameUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\")"
				+ ".childSelector(new UiSelector().textContains(\"游戏\"))";
		AndroidElement topGameTab = mDriver.findElementByAndroidUIAutomator(gameUiSelector);
		topGameTab.click();
		WaitUtil.forceWait(3);
		// // 全路径xpath获取软件Tab
		String softwareUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\")"
				+ ".childSelector(new UiSelector().textContains(\"软件\"))";
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		AndroidElement topSoftwareTab = mDriver.findElementByAndroidUIAutomator(softwareUiSelector);
		topSoftwareTab.click();
		WaitUtil.implicitlyWait(2);
		// TODO 必要时截图
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试滑动跳转其他Tab(软件-游戏)", //
			riskPoint = "UI变动")
	public void testSlip2OtherTab() throws InterruptedException {
		String gameUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\")"
				+ ".childSelector(new UiSelector().textContains(\"游戏\"))";
		LogUtil.printCurrentMethodName();
		WaitUtil.forceWait(4);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(4);
		assertEquals(mDriver.findElementByAndroidUIAutomator(gameUiSelector).isSelected(), true);
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		WaitUtil.forceWait(4);
		assertEquals(mDriver.findElementByAndroidUIAutomator(gameUiSelector).isSelected(), false);
		// TODO 必要时截图
		// 最好增加验证
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试精选福利", riskPoint = "页面变动||MM的安装状态，目前只假定其安装和没安装两种情况")
	public void testWellSelect() throws InterruptedException {// 测试精选福利
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		String wellSelectorUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\").textContains(\"精选福利\")";
		AndroidElement wellSelectItemTv = mDriver.findElementByAndroidUIAutomator(wellSelectorUiSelector);
		if (AppUtil.isInstall(App.MM_PACKAGE_NAME)) {
			wellSelectItemTv.click();
			WaitUtil.forceWait(2);
			AppUtil.killApp(App.MM_PACKAGE_NAME);// 杀死MM
			// TODO 截图
		} else {
			wellSelectItemTv.click();

			// TODO 新增不稳定
			PageRouteUtil.pressBack();
			if (ContextUtil.getCurrentActivity().equals(".activity.FavorActivity")) {//
				AppUtil.handleInfoSwitch2Native();
				WaitUtil.implicitlyWait(2);
				mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
				System.out.println(ContextUtil.getCurrentActivity());
			} else {
				System.out.println(ContextUtil.getCurrentActivity());
			}
			// RiskPoint 不一定进来就是BS页面,当前是这个情况，用Back返回
			// TODO 截图
		}
	}

	@Test(dependsOnMethods = { "testWellSelect" })
	public void scroll2SoftWareBottom() {// 测试滑动到底部
		LogUtil.printCurrentMethodName();
		ScrollUtil.scrollToBase();
	}

	@Test(dependsOnMethods = { "scroll2SoftWareBottom" })
	public void moreSoftware() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		String moreBtnUiSelector = "new UiSelector().className(\"android.widget.TextView\").textContains(\"更多软件\").resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\")";
		AndroidElement moreBtn = mDriver.findElementByAndroidUIAutomator(moreBtnUiSelector);
		moreBtn.click();
		// TODO 必要时进行截图
		if (AppUtil.isInstall(App.MM_PACKAGE_NAME)) {// MM已经安装
			WaitUtil.forceWait(2);
			AppUtil.killApp(App.MM_PACKAGE_NAME);// 杀死MM
		} else {// 没有安装，进入应用详情
			Assert.assertEquals(ContextUtil.getCurrentActivity(), ".activity.DetailActivity");
			PageRouteUtil.pressBack();
			WaitUtil.forceWait(2);
		}
	}

	// TODO 从这里开始
	@Test(dependsOnMethods = { "moreSoftware" })
	@Tips(riskPoint = "非全Xpath,UI变动的风险，依赖于scrollBottom", description = "点击换一批")
	public void refreshSoftBatch() throws InterruptedException {// 点击换一批(\"\")
		if (ContextUtil.getCurrentActivity().equals(".activity.MainActivity")) {
			ScrollUtil.scrollToBase();
		} else {
			System.err.println("当前页面不是目标页面");
		}
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(5);
		String gameUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\").textContains(\"换一批\")";
		AndroidElement topGameTab = mDriver.findElementByAndroidUIAutomator(gameUiSelector);
		topGameTab.click();
		WaitUtil.forceWait(2);
	}
}
