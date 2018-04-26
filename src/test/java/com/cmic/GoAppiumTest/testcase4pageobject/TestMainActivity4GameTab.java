package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.MainGameTabPage;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.AndroidElement;

public class TestMainActivity4GameTab extends BaseTest {

	private MainGameTabPage mMainGamePage;

	@Override
	public void setUpBeforeClass() {
		mMainGamePage = new MainGameTabPage();
		mMainGamePage.action.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Test
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		LogUtil.w("进行{}用例集的初始化检验，失败则跳过该用例集的所有测试", mTag);
		assertEquals(getCurrentPageName(), ".activity.MainActivity");
		mMainGamePage.snapScreen("进入必备应用主页界面");
	}

	// TODO 当前需要跳过该部分的测试 @Test(dependsOnMethods = { "refreshSoftBatch" })
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试点击精品应用", riskPoint = "页面变动", triggerTime = "需要切换到游戏的Tab")
	public void checkGreatGame() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		mMainGamePage.action.go2SwipeFullScreen(Direction.LEFT, 80);
		if (!mMainGamePage.getGameTabSelectedStatus()) {
			LogUtil.e("测试点击精品应用出现页面异常");
			return;
		}
		mMainGamePage.clickGreatGame();
		if (AppUtil.isInstall(App.MM_PACKAGE_NAME)) {
			mMainGamePage.action.go2killApp(App.MM_PACKAGE_NAME);// 杀死MM
			// TODO 截图
		} else {
			// TODO 新增不稳定
			mMainGamePage.action.go2Backforward();
			if (getCurrentPageName().equals("FavorActivity")) {//
				AppUtil.handleInfoSwitch2Native();
				mMainGamePage.action.go2Backforward();
				LogUtil.w(getCurrentPageName());
			} else {
				LogUtil.w(getCurrentPageName());
			}
		}
	}

	@Test(dependsOnMethods = { "checkGreatGame" })
	public void check2GameBottom() {
		LogUtil.printCurrentMethodNameInLog4J();
		mMainGamePage.action.go2Swipe2Bottom();
	}

	@Test(dependsOnMethods = { "check2GameBottom" })
	public void moreGame() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		mMainGamePage.clickMoreGame();
		// TODO 必要时进行截图
		if (AppUtil.isInstall(App.MM_PACKAGE_NAME)) {// MM已经安装
			mMainGamePage.action.go2killApp(App.MM_PACKAGE_NAME);// 杀死MM
		} else {// 没有安装，进入应用详情
			Assert.assertEquals(getCurrentPageName(), "DetailActivity");
			mMainGamePage.action.go2Backforward();
		}
	}

	// TODO 从这里开始
	@Test(dependsOnMethods = { "moreGame" })
	@Tips(riskPoint = "非全Xpath,UI变动的风险，依赖于scrollBottom", description = "点击换一批")
	public void refreshGameBatch() throws InterruptedException {// 点击换一批(\"\")
		LogUtil.printCurrentMethodNameInLog4J();
		if (getCurrentPageName().equals("MainActivity")) {
			mMainGamePage.action.go2Swipe2Bottom();
		} else {
			LogUtil.e("当前页面不是目标页面");
		}
		mMainGamePage.clickRefreshBatch();
	}
}
