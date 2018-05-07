package com.cmic.GoAppiumTest.biz.indexcollect;

import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTestNew;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.RequisitePage;
import com.cmic.GoAppiumTest.page.TrafficManagerPage;
import com.cmic.GoAppiumTest.page.WebViewAdPage;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage;
import com.cmic.GoAppiumTest.page.middlepage.NotificationPage;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.util.EssentialUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.gargoylesoftware.htmlunit.javascript.host.fetch.Request;

public class PageLoadIndexCollect extends BaseTestNew {

	@Override
	public void setUpBeforeClassOrigin() {
		LogUtil.w("{}用例集[{}]开始", "指标收集", mTag);
	}

	@Override
	public void tearDownAfterClassOrigin() {
		LogUtil.w("{}用例集[{}]结束", "指标收集", mTag);
	}

	@Override
	public void setUpBeforeMTestCaseOrigin() {
		LogUtil.w("{}用例{}开始", "指标收集", ++App.CASE_COUNT);
	}

	@Override
	public void tearDownAfterMTestCaseOrigin() {
		LogUtil.w("{}用例{}结束", "指标收集", App.CASE_COUNT);
	}

	@Test
	public void initCheck() {
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2OpenTheWifi();
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
		notificationPage.go2CloseTheWifi();
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
	}

	@Test
	public void checkWifiOpen() {
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2OpenTheWifi();
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
	}

	@Tips(description = "加载首页", riskPoint = "受网速|缓存影响较大")
	@Test
	public void loadMainPage() {
		// RequisitePage requisitePage = new RequisitePage();
		// requisitePage.action.go2SelfPage();
		MainTempPage mainPage = new MainTempPage();
		mainPage.action.go2SelfPage();
		mainPage.action.go2GetTimeDiffElementShow(mainPage.rollKeyword);
	}

	@Tips(description = "加载流量管理", riskPoint = "受网速|缓存影响较大")
	public void loadTrafficPage() {
		TrafficManagerPage trafficPage = new TrafficManagerPage();
		trafficPage.action.go2SelfPage();
		trafficPage.action.go2GetTimeDiffElementShow(trafficPage.tabTrafficSuit);
	}

	@Tips(description = "加载首页H5", riskPoint = "受网速|缓存影响较大")
	public void loadMainAdPage() {
		WebViewAdPage webViewAdPage = new WebViewAdPage();
		webViewAdPage.action.go2SelfPage();
		webViewAdPage.action.go2GetTimeDiffElementShow(webViewAdPage.xxx);
	}

	@Tips(description = "加载搜索结果", riskPoint = "受网速|缓存影响较大")
	public void loadSearchPage() {
		SearchResultPage searchResultPage = new SearchResultPage();
		searchResultPage.action.go2SelfPage();
		searchResultPage.action.go2GetTimeDiffElementShow(searchResultPage.searchResultCount);
	}

	@Test
	public void checkWifiClose() {
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2CloseTheWifi();
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
	}

	@Tips(description = "移动数据网络加载首页", riskPoint = "受网速|缓存影响较大")
	public void loadMainPageWithoutWifi() {
		MainTempPage mainPage = new MainTempPage();
		mainPage.action.go2SelfPage();
		mainPage.action.go2GetTimeDiffElementShow(mainPage.rollKeyword);
	}

	@Tips(description = "移动数据网络加载流量管理", riskPoint = "受网速|缓存影响较大")
	public void loadTrafficPageWithoutWifi() {
		TrafficManagerPage trafficPage = new TrafficManagerPage();
		trafficPage.action.go2SelfPage();
		trafficPage.action.go2GetTimeDiffElementShow(trafficPage.tabTrafficSuit);
	}

	@Tips(description = "移动数据网络加载首页H5", riskPoint = "受网速|缓存影响较大")
	public void loadMainAdPageWithoutWifi() {
		WebViewAdPage webViewAdPage = new WebViewAdPage();
		webViewAdPage.action.go2SelfPage();
		webViewAdPage.action.go2GetTimeDiffElementShow(webViewAdPage.xxx);
	}

	@Tips(description = "移动数据网络加载搜索结果", riskPoint = "受网速|缓存影响较大")
	public void loadSearchPageWithoutWifi() {
		SearchResultPage searchResultPage = new SearchResultPage();
		searchResultPage.action.go2SelfPage();
		searchResultPage.action.go2GetTimeDiffElementShow(searchResultPage.searchResultCount);
	}
}
