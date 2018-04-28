package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.MainGameTabPage;
import com.cmic.GoAppiumTest.page.MainSoftTabPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;

@Listeners(ExtentReportListener.class)
public class TestWebviewADActivity extends BaseTest {

	private MainGameTabPage gamePage;
	private MainSoftTabPage softPage;

	@Tips(description = "用于增强BeforeClass", triggerTime = "假设已经入Setting&&未跳转到其他页面")
	@Override
	public void setUpBeforeClass() {
		softPage = new MainSoftTabPage();// 先测试softPage的底部集团广告
		softPage.action.go2SelfPage();
	}

	@Tips(description = "用于增强AfterClass")
	@Override
	public void tearDownAfterClass() {

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// 建议移到和MainyAc一起测试 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		LogUtil.w("进行{}用例集的初始化检验，失败则跳过该用例集的所有测试", mTag);
		assertEquals(getCurrentPageName(), "MainActivity");
		softPage.snapScreen("进入必备应用主页广告界面");
	}

	// TODO 可建议分开，方便依赖
	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "检查SoftWare底部的集团广告是否存在", riskPoint = "开发大哥根本没有定义ID，怎么定位控件啊，0405取巧")
	public void checkMainSoftAdShow() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		int viewPageSizeBeforeScroll = softPage.softBannerSize();
		softPage.action.go2Swipe2Bottom();
		softPage = new MainSoftTabPage();// 重新加载
		int viewPageSizeAfterScroll = softPage.softBannerSize();
		// 滑动到底部，集团广告的ViewPage，主页的ViewPage
		assertEquals(viewPageSizeAfterScroll != viewPageSizeBeforeScroll, true);
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "检测主页软件Tab集团广告内容显示", riskPoint = "必须保障依赖，不然稳定性差")
	public void checkMainSoftAdContent() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		softPage.clickSoftAdBannner();// 点击集团广告Soft
		// 可能进入LoginActivity页面
		String curAct = getCurrentPageName();
		boolean isTargetAct1 = curAct.equals("LoginActivity") || curAct.equals("FavorActivity")
				|| curAct.equals("DetailActivity");
		assertEquals(isTargetAct1, true);
		if (curAct.equals("LoginActivity")) {// 登陆页要退两次
			softPage.action.go2Backforward();
		}
		softPage.action.go2Backforward();
		assertEquals(getCurrentPageName(), "MainActivity");
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "检查GameTab底部的集团广告是否存在", riskPoint = "开发大哥根本没有定义ID，怎么定位控件啊，0405取巧")
	public void checMainGameAdShow() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		gamePage = new MainGameTabPage();
		gamePage.action.go2SelfPage();
		int viewPageSizeBeforeScroll = gamePage.gameBannerSize();
		gamePage.action.go2Swipe2Bottom();
		gamePage = new MainGameTabPage();// 重新加载
		int viewPageSizeAfterScroll = gamePage.gameBannerSize();
		// 滑动到底部，集团广告的ViewPage，主页的ViewPage
		assertEquals(viewPageSizeAfterScroll != viewPageSizeBeforeScroll, true);
	}

	// TODO 出现新的Activity需要重新进行覆盖
	@Test(dependsOnMethods = { "checMainGameAdShow" })
	@Tips(description = "检测主页游戏Tab集团广告内容显示", riskPoint = "必须保障依赖，不然稳定性差||可能进入登陆页面")
	public void checkMainGameAdContent() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		String curAct1 = getCurrentPageName();
		boolean isTargetAct1 = curAct1.equals("LoginActivity") || curAct1.equals("FavorActivity");
		if (isTargetAct1) {// 用于失败重试
			while (getCurrentPageName().equals("MainActivity")) {
				gamePage.action.go2Backforward();
			}
		}
		gamePage.clickGameAdBannner();
		gamePage.snapScreen("主页游戏Tab集团广告显示");
		// 可能进入LoginActivity页面
		String curAct = getCurrentPageName();
		boolean isTargetAct = curAct.equals("LoginActivity") || curAct.equals("FavorActivity")
				|| curAct.equals("DetailActivity");
		assertEquals(isTargetAct, true);
		if (curAct.equals("LoginActivity")) {// 登陆页要退两次
			gamePage.action.go2Backforward();
		}
		gamePage.action.go2Backforward();
		assertEquals(getCurrentPageName(), "MainActivity");
	}
}
