package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.MainSoftTabPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

@Listeners(FailSnapshotListener.class)
public class TestMainActivity4SoftTab extends BaseTest {

	private MainSoftTabPage mMainSoftPage;

	@Override
	public void setUpBeforeClass() {
		mMainSoftPage = new MainSoftTabPage();
		mMainSoftPage.action.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {
	}

	@Test
	public void initCheck() {// 1
		// 先确认是否进入该页面
		LogUtil.w("进行{}用例集的初始化检验，失败则跳过该用例集的所有测试", mTag);
		assertEquals(getCurrentPageName(), "MainActivity");
		mMainSoftPage.snapScreen("进入必备应用主页界面");
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "测试点击跳转其他Tab(软件-游戏)", //
			riskPoint = "UI变动")
	public void checkClick2OtherTab() throws InterruptedException {//
		LogUtil.printCurrentMethodNameInLog4J();
		mMainSoftPage.clickGameTab();
		// TODO 必要时截图
		mMainSoftPage.snapScreen("进入必备应用主页界面测试点击跳转游戏Tab");
		mMainSoftPage.clickSoftTab();
		// TODO 必要时截图
		mMainSoftPage.snapScreen("进入必备应用主页界面测试点击跳转软件Tab");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试滑动跳转其他Tab(软件-游戏)", //
			riskPoint = "UI变动")
	public void testSlip2OtherTab() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		mMainSoftPage.action.go2SwipeFullScreen(Direction.LEFT, 80);
		assertEquals(mMainSoftPage.getGameTabSelectedStatus(), true);
		mMainSoftPage.action.go2SwipeFullScreen(Direction.RIGHT, 80);
		assertEquals(mMainSoftPage.getGameTabSelectedStatus(), false);
		// TODO 必要时截图
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试精选福利", riskPoint = "页面变动||MM的安装状态，目前只假定其安装和没安装两种情况")
	public void testWellSelect() throws InterruptedException {// 测试精选福利
		LogUtil.printCurrentMethodNameInLog4J();
		if (AppUtil.isInstall(App.MM_PACKAGE_NAME)) {
			mMainSoftPage.clickWellSelect();
			mMainSoftPage.action.go2killApp(App.MM_PACKAGE_NAME);// 杀死MM
			// TODO 截图
		} else {
			mMainSoftPage.clickWellSelect();
			// TODO 新增不稳定
			mMainSoftPage.action.go2Backforward();
			if (getCurrentPageName().equals("FavorActivity")) {//
				LogUtil.w("点击精选福利进入:{}", getCurrentPageName());
				AppUtil.handleInfoSwitch2Native();
				mMainSoftPage.action.go2Backforward();
			} else {
				LogUtil.w("点击精选福利进入:{}", getCurrentPageName());
			}
		}
	}

	@Test(dependsOnMethods = { "testWellSelect" })
	public void scroll2SoftWareBottom() {// 测试滑动到底部
		LogUtil.printCurrentMethodNameInLog4J();
		mMainSoftPage.action.go2Swipe2Bottom();
	}

	@Test(dependsOnMethods = { "scroll2SoftWareBottom" })
	public void moreSoftware() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		mMainSoftPage.clickMoreSoft();
		// TODO 必要时进行截图
		if (AppUtil.isInstall(App.MM_PACKAGE_NAME)) {// MM已经安装
			mMainSoftPage.forceWait(2);
			mMainSoftPage.action.go2killApp(App.MM_PACKAGE_NAME);// 杀死MM
		} else {// 没有安装，进入应用详情
			Assert.assertEquals(getCurrentPageName(), "DetailActivity");
			mMainSoftPage.action.go2Backforward();
			mMainSoftPage.forceWait(2);
		}
	}

	// TODO 从这里开始
	@Test(dependsOnMethods = { "moreSoftware" })
	@Tips(riskPoint = "非全Xpath,UI变动的风险，依赖于scrollBottom", description = "点击换一批")
	public void refreshSoftBatch() throws InterruptedException {// 点击换一批(\"\")
		LogUtil.printCurrentMethodNameInLog4J();
		if (getCurrentPageName().equals("MainActivity")) {
			ScrollUtil.scrollToBase();
		} else {
			System.err.println("当前页面不是目标页面");
		}
		mMainSoftPage.clickRefreshBatch();
	}
}
