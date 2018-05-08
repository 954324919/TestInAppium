package com.cmic.GoAppiumTest.biz.indexcollect;

import static org.testng.Assert.assertEquals;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTestNew;
import com.cmic.GoAppiumTest.helper.AndroidDriverWait;
import com.cmic.GoAppiumTest.helper.ExpectedCondition4AndroidDriver;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.MainSoftTabPage;
import com.cmic.GoAppiumTest.page.RequisitePage;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.TrafficManagerPage;
import com.cmic.GoAppiumTest.page.WebViewAdPage;
import com.cmic.GoAppiumTest.page.middlepage.LauncherPage;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage.MainTempAction;
import com.cmic.GoAppiumTest.page.middlepage.NotificationPage;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.util.EssentialUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.Connection;

public class PageLoadIndexCollect extends BaseTestNew {

	private MainTempPage mainPage;
	private MainTempAction mainAction;

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

	@Tips(description = "开闭网络，仅测试用")
	public void initCheck() {
		LogUtil.w("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2OpenTheWifi();
		LogUtil.w("当前网络状态为{}", mDriver.getConnection());
		notificationPage.go2CloseTheWifi();
		LogUtil.w("当前网络状态为{}", mDriver.getConnection());
	}

	@Tips(description = "开Wifi")
	@Test
	public void checkWifiOpen() {
		LogUtil.printCurrentMethodNameInLog4J();
		LogUtil.w("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2OpenTheWifi();
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, true);
		//
		// 重启应用,清除缓存数据
		mainPage = new MainTempPage();
		mainAction = (MainTempAction) mainPage.action;
		mainAction.go2SelfPage();
	}

	@Tips(description = "加载首页", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "checkWifiOpen", invocationCount = 1)
	public void loadMainPage() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2killApp(App.PACKAGE_NAME);
		new LauncherPage().go2BootsTheApp();
		LogUtil.w("加载时间为{}", mainAction.go2GetTimeDiffElementShow(mainPage.rollKeyword));
	}

	@Tips(description = "加载流量管理", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadMainPage", invocationCount = 3)
	public void loadTrafficPage() {
		LogUtil.printCurrentMethodNameInLog4J();
		LogUtil.w("杀死应用");
		mainAction.go2killApp(App.PACKAGE_NAME);
		LogUtil.w("重新进入");
		WaitUtil.forceWait(2);
		new LauncherPage().go2BootsTheApp();
		//
		TrafficManagerPage trafficPage = new TrafficManagerPage();
		trafficPage.action.go2SelfPage();
		double diffTimer = trafficPage.action.go2GetTimeDiffElementShow(trafficPage.tabTrafficSuit);
		LogUtil.w("加载时间为{}", diffTimer);
	}

	@Tips(description = "加载首页H5|由于当前并不稳定，不进行该用例", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadTrafficPage", enabled = false)
	public void loadMainAdPage() {
		LogUtil.printCurrentMethodNameInLog4J();
		MainSoftTabPage softTabPage = new MainSoftTabPage();
		softTabPage.action.go2SoftReset();
		softTabPage.action.go2SelfPage();
		softTabPage.clickWellSelect();

		WebViewAdPage webViewAdPage = new WebViewAdPage();
		LogUtil.w(webViewAdPage.getPageResource());
	}

	@Tips(description = "加载搜索结果", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadTrafficPage", invocationCount = 3)
	public void loadSearchPage() {
		LogUtil.printCurrentMethodNameInLog4J();
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2SoftReset();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("和飞信");
		SearchResultPage searchResultPage = new SearchResultPage();
		double diffTimer = searchResultPage.action.go2GetTimeDiffElementShow(searchResultPage.searchResultCount);
		LogUtil.w("加载时间为{}", diffTimer);
	}

	@Test(dependsOnMethods = "loadSearchPage")
	public void checkWifiClose() {
		LogUtil.printCurrentMethodNameInLog4J();
		LogUtil.w("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2CloseTheWifi();
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, false);
		notificationPage.action.go2AppReset();// 重启应用,清除缓存数据
	}

	@Tips(description = "移动数据网络加载首页", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "checkWifiClose", invocationCount = 3)
	public void loadMainPageWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		MainTempPage mainPage = new MainTempPage();
		mainAction.go2SoftReset();
		mainAction.go2SelfPage();
		double diffTimer = mainAction.go2GetTimeDiffElementShow(mainPage.rollKeyword);
		LogUtil.w("加载时间为{}", diffTimer);
	}

	@Tips(description = "移动数据网络加载流量管理", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadMainPageWithoutWifi", invocationCount = 3)
	public void loadTrafficPageWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		TrafficManagerPage trafficPage = new TrafficManagerPage();
		trafficPage.action.go2SoftReset();
		trafficPage.action.go2SelfPage();
		double diffTimer = trafficPage.action.go2GetTimeDiffElementShow(trafficPage.tabTrafficSuit);
		LogUtil.w("加载时间为{}", diffTimer);
	}

	@Tips(description = "移动数据网络加载首页H5", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadTrafficPageWithoutWifi", enabled = false)
	public void loadMainAdPageWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		MainSoftTabPage softTabPage = new MainSoftTabPage();
		softTabPage.action.go2SoftReset();
		softTabPage.action.go2SelfPage();
		softTabPage.clickWellSelect();

		WebViewAdPage webViewAdPage = new WebViewAdPage();
		LogUtil.w(webViewAdPage.getPageResource());
		// webViewAdPage.action.go2GetTimeDiffElementShow(webViewAdPage.xxx);
	}

	@Tips(description = "移动数据网络加载搜索结果", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadTrafficPageWithoutWifi", invocationCount = 3)
	public void loadSearchPageWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2SoftReset();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("和飞信");
		SearchResultPage searchResultPage = new SearchResultPage();
		double diffTimer = searchResultPage.action.go2GetTimeDiffElementShow(searchResultPage.searchResultCount);
		LogUtil.w("加载时间为{}", diffTimer);
	}
}
