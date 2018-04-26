package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.action.SearchAction;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;

public class TestSearchActivity extends BaseTest {

	private int originItemCount;
	private int currentItemCount;
	private String rollHotKeyInMainAct;// 滚动热词
	private String searchBeforePerform;

	private SearchPage mSearchPage;

	@Tips(description = "假设已经在MainAct", riskPoint = "耦合度暂不考虑，从MainTest完成进入")
	@Override
	public void setUpBeforeClass() {
		mSearchPage = new SearchPage();
		mSearchPage.action.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() throws InterruptedException {// 1
		LogUtil.w("进行{}用例集的初始化检验，失败则跳过该用例集的所有测试", mTag);
		assertEquals(getCurrentPageName(), ".activity.SearchActivity");
		mSearchPage.snapScreen("进入必备应用搜索界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击加载更多")
	public void checkGetMore() {
		LogUtil.printCurrentMethodNameInLog4J();
		originItemCount = mSearchPage.getCountOfTargetHotWord();
		// 进行操作
		mSearchPage.clickLoadMoreSwitch();
		currentItemCount = mSearchPage.getCountOfTargetHotWord();// TODO 可能存在风险
		assertTrue(originItemCount < currentItemCount);
	}

	// TODO 可能存在风险，可以拆分依赖
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击收起更多")
	public void closeTheWordList() {
		LogUtil.printCurrentMethodNameInLog4J();
		// 进行操作
		mSearchPage.clickLoadMoreSwitch();
		currentItemCount = mSearchPage.getCountOfTargetHotWord();
		assertEquals(currentItemCount, originItemCount);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击随机的一个热词Item", //
			riskPoint = "耦合度过高，与下列clickTheClearSearchRly风险点太高")
	public void randomCheckOne() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		Random random = new Random();
		// TODO 模拟一个数字
		int tempCount = 0;
		int randomIndex = 0;
		if ((tempCount = mSearchPage.getCountOfTargetHotWord()) <= 3) {// 如果显示没有热词则抛出错误
			LogUtil.e("页面显示不全");
			throw new RuntimeException("randomCheckOne页面显示不全");
		}
		// 正常页面显示的情况
		mSearchPage.randomClickHotword();
		// TODO 风险不一定退出
		mSearchPage.action.go2Backforward();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击搜索栏目的clear图标||同意默认情况写下为不显示，受randomCheckOne影响可见")
	public void clickTheClearSearchRly() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSearchPage.clickCleanHistory();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击搜索ActionBar的后退")
	public void checkBack() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		if (!getCurrentPageName().equals("SearchActivity")) {
			LogUtil.e("checkBack当前不在目标页面无法测试");
			throw new RuntimeException("checkBack当前不在目标页面无法测试");
		}
		mSearchPage.action.go2Backforward();
		mSearchPage.forceWait(2);
		assertEquals(getCurrentPageName(), "MainActivity");
		// 获取瞬时滚动热词用于checkWordFromOutside测试
		MainTempPage mainTempPage = new MainTempPage();
		rollHotKeyInMainAct = mainTempPage.getRollKeyWordText();
		if (rollHotKeyInMainAct == null) {
			mSearchPage.snapScreenForce("checkBack获取不到滚动热词");
			mSearchPage.forceWait(1);
			rollHotKeyInMainAct = mainTempPage.getRollKeyWordText();
		}
		LogUtil.e("外界热词为{}", rollHotKeyInMainAct);
		// 再次点击返回SearchActivity
		mainTempPage.click2SearchActivity();
	}

	@Test(dependsOnMethods = { "checkBack" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "被传入的热词")
	public void checkWordFromOutside() {//
		LogUtil.printCurrentMethodNameInLog4J();
		if (!getCurrentPageName().equals("SearchActivity")) {
			LogUtil.e("checkWordFromOutside页面异常");
			throw new RuntimeException("checkWordFromOutside异常截图");
		}
		String rollText = mSearchPage.getCurrentSearchKeyWord();
		// TODO 必要时截图
		LogUtil.e("滚动热词为: {}" + rollText);
		assertEquals(rollText, rollHotKeyInMainAct);
	}
}
