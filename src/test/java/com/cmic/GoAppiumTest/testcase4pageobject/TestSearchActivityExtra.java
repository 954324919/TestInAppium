package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.eclipse.jetty.websocket.common.ConnectionState;
import org.openqa.selenium.By;
import org.openqa.selenium.net.NetworkUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.action.SearchAction;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.KeyboardUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.NetworkUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.Connection;

/**
 * 包含搜索页面的一些非常规测试，包括联想和搜索结果，这两者结合起来测试比较方便，故放在一起
 * 
 * @风险 0403发现AutoCompleteTextView无法定位，考虑使用坐标定位，及其不稳定
 * @author kiwi
 */
@Listeners(ExtentReportListener.class)
public class TestSearchActivityExtra extends BaseTest {

	private String searchHistoryItem;

	private SearchPage searchPage;
	private SearchAction searchAction;

	@Tips(description = "搜索联想功能测试", triggerTime = "假设已经在SearchAct", riskPoint = "耦合度暂不考虑，从MainTest完成进入")
	@Override
	public void setUpBeforeClass() {
		searchPage = new SearchPage();
		searchAction = (SearchAction) searchPage.action;
		searchAction.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() throws InterruptedException {// 1
		// 先确认是否进入该页面
		LogUtil.w("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
		assertEquals(getCurrentPageName(), "SearchActivity");
		searchPage.snapScreen("进入必备应用搜索界面-无搜索历史");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "热搜联想")
	public void checkSearchRalation() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		searchPage.snapScreen("联想搜索前...");
		searchPage.go2SearchByKeyWordInRalation("移动");
		searchPage.snapScreen("联想搜索后...");
	}

	@Test(dependsOnMethods = { "initCheck" }, timeOut = 15000)
	@Tips(description = "点击下载", //
			riskPoint = "由于自动补全控件无法定位，当前预期先使用坐标定位，待解决。不抛出异常，只做正向验证//" + "待补充Robotium白盒测试")
	public void checkClickDownload() {
		LogUtil.printCurrentMethodNameInLog4J();
		// TODO 当前不实现
		// 点击下载按钮
		// 0402当前只能使用这种效果较差的方法
	}

	@Test(dependsOnMethods = { "checkSearchRalation" })
	@Tips(description = "点击直达的联想条目", riskPoint = "联想结果不确定，不一定能点中")
	public void checkClick2DetailByDirectItem() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		searchPage.snapScreen("联想搜索后点击直达条目前...");
		// 1.进行点击
		searchPage.forceWait(1.5);
		searchPage.go2DetailInRalationDerict();
		searchPage.snapScreen("联想搜索后点击直达条目前...");
		// 预期进入详情页或搜索结果页面
		SearchResultPage tempSearchResultPage = new SearchResultPage();
		boolean isShow = tempSearchResultPage.isElementShown(tempSearchResultPage.searchResultCount);
		if (getCurrentPageName().equals("DetailActivity")) {
			go2Backforward();
		} else if (isShow) {
			AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
			searchHistoryItem = searchEt.getText();
			searchAction.go2GetText(searchPage.searchContainer);
			searchAction.go2Backforward();
		} else {// 没有点中\
			LogUtil.e("没有联想词，点击不到");
		}
	}

	@Test(dependsOnMethods = { "checkClickDownload" })
	@Tips(description = "点击简单的联想条目", riskPoint = "联想结果不确定，不一定能点中|注释了可能有意义的代码")
	public void checkClick2SearchResultByEasyItem() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		// 0.前置操作,显示搜索联想
		searchPage.go2SearchByKeyWordInRalation("移动");
		searchAction.go2HideKeyboard();
		searchPage.snapScreen("联想搜索后点击简单条目前...");
		// 1.进行点击
		// 1.进行点击
		searchPage.go2DetailInRalationEasyItem();
		searchPage.snapScreen("联想搜索后点击简单条目后...");
		// 预期
		SearchResultPage tempSearchResultPage = new SearchResultPage();
		boolean isShow = tempSearchResultPage.isElementShown(tempSearchResultPage.searchResultCount);
		if (getCurrentPageName().equals("DetailActivity")) {// 在详情页面
			PageRouteUtil.pressBack();
		} else if (isShow) {
			AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
			searchHistoryItem = searchEt.getText();
			searchAction.go2GetText(searchPage.searchContainer);
			searchAction.go2Backforward();
		} else {// 没有点中
			LogUtil.e("没有联想词，点击不到");
		}
	}

	@Test(dependsOnMethods = { "checkClick2SearchResultByEasyItem" })
	public void checkSearchHistory() {
		LogUtil.printCurrentMethodNameInLog4J();
		searchPage.snapScreen("搜索历史生成");
	}

	@Test(dependsOnMethods = { "checkClick2SearchResultByEasyItem" })
	public void checkEnterDetailFromHistory() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		if (searchHistoryItem == null) {
			searchPage.go2SearchByKeyWord("MM应用商场");
			searchHistoryItem = "MM应用商场";
			go2Backforward();// 无状态页面回退
		}
		((SearchPage) searchPage).clickTargetUiSelectorElement(searchHistoryItem);
		LogUtil.w("点击搜索历史");// TODO 截图
		searchAction.go2Backforward();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkNetworkStatus() {
		boolean temp = NetworkUtil.getNetworkState() == Connection.ALL;
		LogUtil.w("状态为:{}", temp);
		LogUtil.w(NetworkUtil.getNetworkState().name());
		LogUtil.e("请切换网络");
		WaitUtil.forceWait(8);
		LogUtil.w(NetworkUtil.getNetworkState().name());
		LogUtil.e("请切换网络");
		WaitUtil.forceWait(8);
		LogUtil.w(NetworkUtil.getNetworkState().name());
		LogUtil.e("请切换网络");
		WaitUtil.forceWait(8);
		LogUtil.w(NetworkUtil.getNetworkState().name());
	}
}
