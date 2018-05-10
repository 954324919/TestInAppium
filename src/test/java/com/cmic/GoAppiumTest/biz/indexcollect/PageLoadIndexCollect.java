package com.cmic.GoAppiumTest.biz.indexcollect;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTestNew;
import com.cmic.GoAppiumTest.bean.BarChartData;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.MainSoftTabPage;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.SplashPage;
import com.cmic.GoAppiumTest.page.TrafficManagerPage;
import com.cmic.GoAppiumTest.page.WebViewAdPage;
import com.cmic.GoAppiumTest.page.action.SplashAction;
import com.cmic.GoAppiumTest.page.middlepage.LauncherPage;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage.MainTempAction;
import com.cmic.GoAppiumTest.page.middlepage.NotificationPage;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.util.JFreeCharUtil;
import com.cmic.GoAppiumTest.util.LogUtil;

import io.appium.java_client.android.Connection;

public class PageLoadIndexCollect extends BaseTestNew {

	private MainTempPage mainPage;
	private MainTempAction mainAction;

	private Map<String, HashMap<String, ArrayList<Double>>> indexMap;
	private HashMap<String, ArrayList<Double>> innerMapMainPage;
	private HashMap<String, ArrayList<Double>> innerMapTrafficPage;
	private HashMap<String, ArrayList<Double>> innerMapSearchPage;

	@Override
	public void setUpBeforeClassOrigin() {
		LogUtil.w("{}用例集[{}]开始", "指标收集", mTag);
		// 初始化统计的数据
		indexMap = new HashMap<>();
		innerMapMainPage = new HashMap<>();
		innerMapTrafficPage = new HashMap<>();
		innerMapSearchPage = new HashMap<>();
		// PO初始化移动到网络环境设置中
	}

	@Override
	public void tearDownAfterClassOrigin() {
		// 已收集的装配和处理
		indexMap.put("主页页面加载", innerMapMainPage);
		indexMap.put("流量管家页面加载", innerMapTrafficPage);
		indexMap.put("搜索页面加载", innerMapSearchPage);
		List<BarChartData> chartDatas = dataAssemble(indexMap);
		// 画图
		new JFreeCharUtil.BarChartBuilder()//
				.setTitle("几种不同情况下应用平均加载速度")//
				.setXAxisName("X轴:测试情景").setYAxisName("Y轴:加载时间/秒")//
				.setImagePath("D:\\EclipseWorkspace\\GoAppium\\GoAppiumTest\\target\\chart\\页面加载速度指标.jpg")//
				.setDataSource(chartDatas)//
				.outputImage();
		// 释放
		indexMap = null;
		innerMapMainPage = null;
		innerMapTrafficPage = null;
		innerMapSearchPage = null;
		// Log提示
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

	@Tips(description = "开Wifi")
	@Test
	public void checkWifiOpen() {
		LogUtil.printCurrentMethodNameInLog4J();
		LogUtil.w("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2OpenTheWifi();
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, true);
		// PO 实例初始化
		mainPage = new MainTempPage();
		mainAction = (MainTempAction) mainPage.action;
	}

	@Tips(description = "启动到首页|迁移到PageLoadingCollect")
	@Test(dependsOnMethods = "checkWifiOpen", invocationCount = 2)
	public void loadMainFirstInit() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2AppReset();
		mainAction.go2SelfPage();
		MainTempPage mainTempPage = new MainTempPage();
		double diffTime = mainTempPage.action.go2GetTimeDiffElementShow(mainTempPage.rollKeyword);
		LogUtil.i("Wifi下|首页清除缓存加载时间为:{}", diffTime);
		addCollectedData(innerMapMainPage, "Wifi下|清除缓存加载", diffTime);
	}

	@Tips(description = "加载首页", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadMainFirstInit", invocationCount = 2)
	public void loadMainPage() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2killApp(App.PACKAGE_NAME);
		new LauncherPage().go2BootsTheApp();
		double diffTime = mainAction.go2GetTimeDiffElementShow(mainPage.rollKeyword);
		LogUtil.i("Wifi下|首页直接启动加载时间为{}", diffTime);
		addCollectedData(innerMapMainPage, "Wifi下|直接启动", diffTime);
	}

	@Test(dependsOnMethods = "loadMainPage", invocationCount = 2)
	public void loadTrafficPageResetApp() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2AppReset();
		TrafficManagerPage trafficPage = new TrafficManagerPage();
		trafficPage.action.go2SelfPage();
		try {
			double diffTime = trafficPage.action.go2GetTimeDiffElementShow(trafficPage.tabTrafficSuit);
			LogUtil.i("Wifi下|流量管家清除数据加载时间为{}", diffTime);
			addCollectedData(innerMapTrafficPage, "Wifi下|清除缓存加载", diffTime);
		} catch (Exception e) {
			LogUtil.e("加载超时");
		}
	}

	@Tips(description = "加载流量管理", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadTrafficPageResetApp", invocationCount = 2)
	public void loadTrafficPage() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2killApp(App.PACKAGE_NAME);
		new LauncherPage().go2BootsTheApp();
		mainPage.forceWait(2);// 等待
		TrafficManagerPage trafficPage = new TrafficManagerPage();
		trafficPage.action.go2SelfPage();
		try {
			double diffTime = trafficPage.action.go2GetTimeDiffElementShow(trafficPage.tabTrafficSuit);
			LogUtil.i("Wifi下|流量管家直接启动加载时间为{}", diffTime);
			addCollectedData(innerMapTrafficPage, "Wifi下|直接启动", diffTime);
		} catch (Exception e) {
			LogUtil.e("加载超时");
		}
	}

	@Tips(description = "加载搜索结果", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadTrafficPage", invocationCount = 2)
	public void loadSearchPageResetApp() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2killApp(App.PACKAGE_NAME);
		mainAction.go2AppReset();
		mainPage.forceWait(2);// 等待
		//
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("和飞信");
		SearchResultPage searchResultPage = new SearchResultPage();
		double diffTime = searchResultPage.action.go2GetTimeDiffElementShow(searchResultPage.searchResultCount);
		LogUtil.i("加载时间为{}", diffTime);
		addCollectedData(innerMapSearchPage, "Wifi下|清除缓存加载", diffTime);
	}

	@Tips(description = "加载搜索结果", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadSearchPageResetApp", invocationCount = 2)
	public void loadSearchPage() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2killApp(App.PACKAGE_NAME);
		new LauncherPage().go2BootsTheApp();
		mainPage.forceWait(2);// 等待
		//
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("和飞信");
		SearchResultPage searchResultPage = new SearchResultPage();
		double diffTime = searchResultPage.action.go2GetTimeDiffElementShow(searchResultPage.searchResultCount);
		LogUtil.i("加载时间为{}", diffTime);
		addCollectedData(innerMapSearchPage, "Wifi下|直接启动", diffTime);
	}

	@Test(dependsOnMethods = "loadSearchPage")
	public void checkWifiClose() {
		LogUtil.printCurrentMethodNameInLog4J();
		LogUtil.w("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2CloseTheWifi();
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, false);
		// notificationPage.action.go2AppReset();
	}

	@Tips(description = "启动到首页|迁移到PageLoadingCollect")
	@Test(dependsOnMethods = "checkWifiClose", invocationCount = 2)
	public void loadMainFirstInitWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2AppReset();// 重启应用,清除缓存数据
		mainAction.go2SelfPage();
		MainTempPage mainTempPage = new MainTempPage();
		double diffTime = mainTempPage.action.go2GetTimeDiffElementShow(mainTempPage.rollKeyword);
		LogUtil.i("Wifi下|首页清除缓存加载时间为:{}", diffTime);
		addCollectedData(innerMapMainPage, "非Wifi下|清除缓存加载", diffTime);
	}

	@Tips(description = "移动数据网络加载首页", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadMainFirstInitWithoutWifi", invocationCount = 2)
	public void loadMainPageWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2killApp(App.PACKAGE_NAME);
		new LauncherPage().go2BootsTheApp();
		double diffTime = mainAction.go2GetTimeDiffElementShow(mainPage.rollKeyword);
		LogUtil.i("Wifi下|首页直接启动加载时间为{}", diffTime);
		addCollectedData(innerMapMainPage, "非Wifi下|直接启动", diffTime);
	}

	@Test(dependsOnMethods = "loadMainPageWithoutWifi", invocationCount = 2)
	public void loadTrafficPageWithoutWifiAndResetApp() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2AppReset();
		TrafficManagerPage trafficPage = new TrafficManagerPage();
		trafficPage.action.go2SelfPage();
		try {
			double diffTime = trafficPage.action.go2GetTimeDiffElementShow(trafficPage.tabTrafficSuit);
			LogUtil.i("非Wifi下|流量管家清除数据加载时间为{}", diffTime);
			addCollectedData(innerMapTrafficPage, "非Wifi下|清除缓存加载", diffTime);
		} catch (Exception e) {
			LogUtil.e("加载超时");
		}
	}

	@Tips(description = "移动数据网络加载流量管理", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadTrafficPageWithoutWifiAndResetApp", invocationCount = 2)
	public void loadTrafficPageWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2killApp(App.PACKAGE_NAME);
		new LauncherPage().go2BootsTheApp();
		//
		TrafficManagerPage trafficPage = new TrafficManagerPage();
		trafficPage.action.go2SelfPage();
		try {
			double diffTime = trafficPage.action.go2GetTimeDiffElementShow(trafficPage.tabTrafficSuit);
			LogUtil.i("非Wifi下|流量管家直接启动加载时间为{}", diffTime);
			addCollectedData(innerMapTrafficPage, "非Wifi下|直接启动", diffTime);
		} catch (Exception e) {
			LogUtil.e("加载超时");
		}
	}

	@Tips(description = "加载搜索结果", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadTrafficPageWithoutWifi", invocationCount = 2)
	public void loadSearchPageWithoutWifiAndResetApp() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2AppReset();// 重启应用,清除缓存数据
		//
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("和飞信");
		SearchResultPage searchResultPage = new SearchResultPage();
		double diffTime = searchResultPage.action.go2GetTimeDiffElementShow(searchResultPage.searchResultCount);
		LogUtil.i("加载时间为{}", diffTime);
		addCollectedData(innerMapSearchPage, "非Wifi下|清除缓存加载", diffTime);
	}

	@Tips(description = "移动数据网络加载搜索结果", riskPoint = "受网速|缓存影响较大")
	@Test(dependsOnMethods = "loadSearchPageWithoutWifiAndResetApp", invocationCount = 2)
	public void loadSearchPageWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		mainAction.go2killApp(App.PACKAGE_NAME);
		new LauncherPage().go2BootsTheApp();
		//
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("和飞信");
		SearchResultPage searchResultPage = new SearchResultPage();
		double diffTime = searchResultPage.action.go2GetTimeDiffElementShow(searchResultPage.searchResultCount);
		LogUtil.i("加载时间为{}", diffTime);
		addCollectedData(innerMapSearchPage, "非Wifi下|直接启动", diffTime);
	}

	// =============================以下为暂存代码===============================

	@Tips(description = "移动数据网络加载首页H5", riskPoint = "受网速|缓存影响较大")
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

	@Tips(description = "加载首页H5|由于当前并不稳定，不进行该用例", riskPoint = "受网速|缓存影响较大")
	public void loadMainAdPage() {
		LogUtil.printCurrentMethodNameInLog4J();
		MainSoftTabPage softTabPage = new MainSoftTabPage();
		softTabPage.action.go2SoftReset();
		softTabPage.action.go2SelfPage();
		softTabPage.clickWellSelect();

		WebViewAdPage webViewAdPage = new WebViewAdPage();
		LogUtil.w(webViewAdPage.getPageResource());
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

	@Tips(description = "[非测试用例]将收集到的数据添加到目标Map容器中")
	public void addCollectedData(Map<String, ArrayList<Double>> targetInnerMap, String category, double value) {
		if (targetInnerMap.containsKey(category)) {
			targetInnerMap.get(category).add(value);
		} else {
			ArrayList<Double> tempList = new ArrayList<>();
			tempList.add(value);
			targetInnerMap.put(category, tempList);
		}
	}

	@Tips(description = "进行数据处理(格拉布斯异常值剔除并求平均值)和装配")
	private List<BarChartData> dataAssemble(Map<String, HashMap<String, ArrayList<Double>>> indexMap) {
		List<BarChartData> chartDatas = new ArrayList<>();
		// new ArrayList<>();
		// 数据处理
		Iterator<Map.Entry<String, HashMap<String, ArrayList<Double>>>> it = indexMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, HashMap<String, ArrayList<Double>>> entryOutside = it.next();
			// 内部Map
			HashMap<String, ArrayList<Double>> innerMap = entryOutside.getValue();
			Iterator<Map.Entry<String, ArrayList<Double>>> itInner = innerMap.entrySet().iterator();
			while (itInner.hasNext()) {
				Map.Entry<String, ArrayList<Double>> entryInner = itInner.next();
				double sum = 0;
				for (Double collectedIndex : entryInner.getValue()) {// 循环
					sum += collectedIndex;
				}
				BarChartData barChartData = new BarChartData(entryInner.getKey(), entryOutside.getKey(),
						sum / entryInner.getValue().size());
				chartDatas.add(barChartData);
			}
		}
		return chartDatas;
	}

}
