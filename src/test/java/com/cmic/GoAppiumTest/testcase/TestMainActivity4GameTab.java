package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.AndroidElement;

/**
 * 让各个测试用例间更加解耦
 * 
 * @author cmic
 *
 */
public class TestMainActivity4GameTab extends BaseTest {

	@Override
	public void setUpBeforeClass() {
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
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

	// TODO 当前需要跳过该部分的测试 @Test(dependsOnMethods = { "refreshSoftBatch" })
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试点击精品应用", riskPoint = "页面变动", triggerTime = "需要切换到游戏的Tab")
	public void checkGreatGame() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		String gameUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\")"
				+ ".childSelector(new UiSelector().textContains(\"游戏\"))";
		AndroidElement e = mDriver.findElementByAndroidUIAutomator(gameUiSelector);
		if (!e.isSelected()) {
			System.err.println("测试点击精品应用出现页面异常");
			return;
		}
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		String greatGameUiSelector = "new UiSelector().className(\"android.widget.TextView\").textContains(\"品牌游戏\").resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\")";
		AndroidElement greatGameTv = mDriver.findElementByAndroidUIAutomator(greatGameUiSelector);
		if (AppUtil.isInstall(App.MM_PACKAGE_NAME)) {
			greatGameTv.click();
			WaitUtil.forceWait(2);
			AppUtil.killApp(App.MM_PACKAGE_NAME);// 杀死MM
			// TODO 截图
		} else {
			greatGameTv.click();
			// TODO 新增不稳定
			PageRouteUtil.pressBack();
			if (ContextUtil.getCurrentActivity().equals(".activity.FavorActivity")) {//
				AppUtil.handleInfoSwitch2Native();
				WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
				mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
				System.out.println(ContextUtil.getCurrentActivity());
			} else {
				System.out.println(ContextUtil.getCurrentActivity());
			}
			// RiskPoint 不一定进来就是BS页面,当前是这个情况，用Back返回
			// TODO 截图
		}
	}

	@Test(dependsOnMethods = { "checkGreatGame" })
	public void check2GameBottom() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		ScrollUtil.scrollToBase();
	}

	@Test(dependsOnMethods = { "check2GameBottom" })
	public void moreGame() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		String moreBtnUiSelector = "new UiSelector().className(\"android.widget.TextView\").textContains(\"更多游戏\").resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\")";
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
	@Test(dependsOnMethods = { "moreGame" })
	@Tips(riskPoint = "非全Xpath,UI变动的风险，依赖于scrollBottom", description = "点击换一批")
	public void refreshGameBatch() throws InterruptedException {// 点击换一批(\"\")
		if (ContextUtil.getCurrentActivity().equals(".activity.MainActivity")) {
			ScrollUtil.scrollToBase();
		} else {
			System.err.println("当前页面不是目标页面");
		}
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		String gameUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\").textContains(\"换一批\")";
		AndroidElement topGameTab = mDriver.findElementByAndroidUIAutomator(gameUiSelector);
		topGameTab.click();
		WaitUtil.forceWait(1);
	}
}
