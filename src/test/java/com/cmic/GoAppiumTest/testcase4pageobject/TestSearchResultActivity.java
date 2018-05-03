package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage.SearchResultAction;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import io.appium.java_client.android.Connection;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

@Listeners(ExtentReportListener.class)
public class TestSearchResultActivity extends BaseTest {

	private SearchResultPage mSearchResultPage;

	@Tips(description = "假设已经入SearchActivity的热词界面")
	@Override
	public void setUpBeforeClass() {
		mSearchResultPage = new SearchResultPage();
		((SearchResultAction) mSearchResultPage.action).go2SelfPageWithKey("移动");
	}

	@Override
	public void tearDownAfterClass() {

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// 先确认是否进入该页面
		LogUtil.w("进行{}用例集的初始化检验，失败则跳过该用例集的所有测试", mTag);
		assertEquals(getCurrentPageName(), "SearchActivity");
		assertEquals(mSearchResultPage.getPageLoadFinishOrNot(), true);
		mSearchResultPage.snapScreen("进入必备搜索结果界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "滑动切换页面", riskPoint = "UI变动")
	public void checkSild2OtherTab() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		assertEquals(mSearchResultPage.allTabSelected(), true);
		mSearchResultPage.action.go2SwipeFullScreen(Direction.LEFT, 80);
		mSearchResultPage.action.go2SwipeFullScreen(Direction.LEFT, 80);
		assertEquals(mSearchResultPage.allTabSelected(), false);
		mSearchResultPage.action.go2SwipeFullScreen(Direction.RIGHT, 80);
		assertEquals(mSearchResultPage.allTabSelected(), false);
		mSearchResultPage.action.go2SwipeFullScreen(Direction.RIGHT, 80);
		assertEquals(mSearchResultPage.allTabSelected(), true);
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "测试点击切换", riskPoint = "页面变动|网络变动")
	public void checkClick2OtherTab() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		assertEquals(mSearchResultPage.allTabSelected(), true);
		mSearchResultPage.click2SoftTab();// 点击切换到SoftWartTab
		assertEquals(mSearchResultPage.getSearchResultCount() > 0, true);
		assertEquals(mSearchResultPage.allTabSelected(), false);

		mSearchResultPage.click2GameTab();
		assertEquals(mSearchResultPage.getSearchResultCount() > 0, true);
		assertEquals(mSearchResultPage.allTabSelected(), false);

		mSearchResultPage.click2AllTab();
		assertEquals(mSearchResultPage.getSearchResultCount() > 0, true);
		assertEquals(mSearchResultPage.allTabSelected(), true);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkRandomClick2Detail() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		if (mSearchResultPage.getSearchResultCount() == 0) {
			System.err.println("列表为空");
			return;
		}
		mSearchResultPage.randomClick();
		assertEquals(getCurrentPageName(), "DetailActivity");
		mSearchResultPage.action.go2Backforward();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查随机下载", riskPoint = "下载状态实际难以预测，当前findView不够稳定")
	public void checkRandomClick2Download() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		Connection temp = mSearchResultPage.action.go2GetNetWorkStatus();
		LogUtil.w("当前网络状态为{}", temp.name());
		boolean isDataStatu = (temp == Connection.DATA);// 移动数据网络状态
		mSearchResultPage.randomGo2DownloadStart();
		if (!isDataStatu) {// 不是移动网络状态//WIFI
			mSearchResultPage.randomGo2DownloadPause();// 马上点击停止，防止进入安装界面
			WaitUtil.forceWait(0.5);
		} else {// 移动网络状态//DATA//CANTUSE
			LogUtil.w("由于处于移动网络，进入弹窗提示页面");
			mSearchResultPage.click2GoOnDownload();
			mSearchResultPage.forceWait(1);
			// 再点击暂停
			mSearchResultPage.randomGo2DownloadPause();// 马上点击停止，防止进入安装界面
		}
		assertEquals(mSearchResultPage.getRandomTargetText().contains("继续"), true);
	}

	// TODO 及其不稳定，暂不考虑放开
	@Test(dependsOnMethods = { "initCheck" })
	public void checkDownloadButtonOpenStatus() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSearchResultPage.randomGo2Open();
		assertEquals(mSearchResultPage.getPackageName() != App.PACKAGE_NAME, true);
		mSearchResultPage.action.go2AppReset();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkSearch2Baseline() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSearchResultPage.action.go2Swipe2BottomWithTimeOut(5);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查没有搜索内容时的情况", riskPoint = "搜索关键词可能存在搜索结果")
	public void checkErrorInput4EmptyPage() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		((SearchResultAction) mSearchResultPage.action).go2SelfPageWithKey("assssss");
		// 查找
		mSearchResultPage.click2GameTab();
		assertEquals(mSearchResultPage.getErrorTipVisiable(), true);
	}
}
