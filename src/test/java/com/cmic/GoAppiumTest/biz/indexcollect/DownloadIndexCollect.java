package com.cmic.GoAppiumTest.biz.indexcollect;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.bean.BarChartData;
import com.cmic.GoAppiumTest.biz.base.BaseTest4IndexCollect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.middlepage.InstalllerPage;
import com.cmic.GoAppiumTest.page.middlepage.NotificationPage;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.page.middlepage.InstalllerPage.InstallerAction;
import com.cmic.GoAppiumTest.util.JFreeCharUtil;
import com.cmic.GoAppiumTest.util.LogUtil;

import io.appium.java_client.android.Connection;

public class DownloadIndexCollect extends BaseTest4IndexCollect {

	private Map<String, HashMap<String, ArrayList<Double>>> indexMap;
	private HashMap<String, ArrayList<Double>> innerMapInWifi;
	private HashMap<String, ArrayList<Double>> innerMapNoWifi;

	@Override
	public void tearDownAfterClass() {
		// 已收集的装配和处理
		indexMap.put("Wifi情况下", innerMapInWifi);
		indexMap.put("非Wifi情况下", innerMapNoWifi);
		List<BarChartData> chartDatas = dataAssemble(indexMap);
		// 画图
		new JFreeCharUtil.BarChartBuilder()//
				.setTitle("不同情况下的应用下载指标")//
				.setXAxisName("X轴:测试情景").setYAxisName("Y轴:加载时间/秒|加载速率M/秒")//
				.setImagePath("D:\\EclipseWorkspace\\GoAppium\\GoAppiumTest\\target\\chart\\应用下载指标.jpg")//
				.setDataSource(chartDatas)//
				.outputImage();
		// 释放
		indexMap = null;
		innerMapInWifi = null;
		innerMapNoWifi = null;
	}

	@Override
	public void setUpBeforeClass() {
		// 初始化统计的数据
		indexMap = new HashMap<>();
		innerMapInWifi = new HashMap<>();
		innerMapNoWifi = new HashMap<>();
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
	}

	@Test(dependsOnMethods = "checkWifiOpen", invocationCount = 3)
	@Tips(description = "下载在Wifi环境", riskPoint = "变量控制")
	public void download() {
		LogUtil.printCurrentMethodNameInLog4J();
		// 寻找最佳匹配
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2AppReset();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("MM资讯");
		//
		SearchResultPage searchResultPage = new SearchResultPage();
		if (searchResultPage.isElementShown(searchResultPage.searchResultCount)) {
			double appSize = searchResultPage.getAppSize();
			LogUtil.e("应用包大小为{}", appSize);
			// 准备接收判断的逻辑
			InstalllerPage installlerPage = new InstalllerPage();
			InstallerAction installerAction = (InstallerAction) installlerPage.action;
			// 开始下载
			searchResultPage.action.go2Click(searchResultPage.btnAppFirstDownload);
			double downloadTime = installerAction.go2GetTimeDiffElementShow(installlerPage.tvInstallingAppName);
			LogUtil.i("应用下载时间为{}", downloadTime);
			addCollectedData(innerMapInWifi, "MM资讯下载时间", downloadTime);
			double formatValue = Double.parseDouble(String.format("%.2f", appSize / downloadTime));
			addCollectedData(innerMapInWifi, "MM资讯下载速率", formatValue);
			LogUtil.i("应用下载速率为{}{}/{}", formatValue, "M", "秒");
		} else {
			LogUtil.e("搜索{}失败", "MM资讯");
		}
	}

	@Test(dependsOnMethods = "download")
	public void checkWifiClose() {
		LogUtil.printCurrentMethodNameInLog4J();
		LogUtil.w("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2CloseTheWifi();
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, false);
	}

	@Test(dependsOnMethods = "checkWifiClose", invocationCount = 1)
	@Tips(description = "下载在移动数据网路", riskPoint = "变量控制")
	public void downloadWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		// 寻找最佳匹配
		SearchPage searchPage = new SearchPage();
		// 重新初始化应用
		searchPage.action.go2AppReset();
		// 回到本应用
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("MM资讯");
		//
		SearchResultPage searchResultPage = new SearchResultPage();
		if (searchResultPage.isElementShown(searchResultPage.searchResultCount)) {
			double appSize = searchResultPage.getAppSize();
			LogUtil.e("应用包大小为{}", appSize);
			// 准备接收判断的逻辑
			InstalllerPage installlerPage = new InstalllerPage();
			InstallerAction installerAction = (InstallerAction) installlerPage.action;
			// 开始下载
			searchResultPage.action.go2Click(searchResultPage.btnAppFirstDownload);
			// 开始统计
			double downloadTime = installerAction.go2GetTimeDiffElementShow(installlerPage.tvInstallingAppName);
			LogUtil.i("应用下载时间为{}", downloadTime);
			addCollectedData(innerMapNoWifi, "MM资讯下载时间", downloadTime);
			double formatValue = Double.parseDouble(String.format("%.2f", appSize / downloadTime));
			addCollectedData(innerMapNoWifi, "MM资讯下载速率", formatValue);
			LogUtil.i("应用下载速率为{}{}/{}", formatValue, "M", "秒");
		} else {
			LogUtil.e("搜索{}失败", "MM资讯");
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
}
