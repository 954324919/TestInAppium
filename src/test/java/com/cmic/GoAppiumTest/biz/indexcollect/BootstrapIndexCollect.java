package com.cmic.GoAppiumTest.biz.indexcollect;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.aventstack.extentreports.model.Log;
import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.bean.BarChartData;
import com.cmic.GoAppiumTest.biz.base.BaseTest4IndexCollect;
import com.cmic.GoAppiumTest.helper.AndroidDriverWait;
import com.cmic.GoAppiumTest.helper.ExpectedCondition4AndroidDriver;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SplashPage;
import com.cmic.GoAppiumTest.page.action.SplashAction;
import com.cmic.GoAppiumTest.page.middlepage.LauncherPage;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage;
import com.cmic.GoAppiumTest.page.middlepage.NotificationPage;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage.MainTempAction;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.EssentialUtil;
import com.cmic.GoAppiumTest.util.FileUtil;
import com.cmic.GoAppiumTest.util.JFreeCharUtil;
import com.cmic.GoAppiumTest.util.LogUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.Connection;

@Tips(description = "统计应用启动时间", riskPoint = "使用PageObject较之DriverWait不够准确，不采用")
public class BootstrapIndexCollect extends BaseTest4IndexCollect {

	private SplashPage mSplashPage;
	private SplashAction spalashAction;

	private Map<String, HashMap<String, ArrayList<Double>>> indexMap;
	private HashMap<String, ArrayList<Double>> innerMap;

	@Override
	public void tearDownAfterClass() {
		// 已收集的装配和处理
		List<BarChartData> chartDatas = dataAssemble(indexMap);
		// 画图
		new JFreeCharUtil.BarChartBuilder()//
				.setTitle("几种不同情况下应用平均启动速度")//
				.setXAxisName("X轴:测试情景").setYAxisName("Y轴:加载时间/秒")//
				.setImagePath(FileUtil.filePathTransformRelative("/target/chart/应用启动速度指标.jpg"))//
				.setDataSource(chartDatas)//
				.outputImage();
		// 释放
		indexMap = null;
		innerMap = null;
	}

	@Override
	public void setUpBeforeClass() {
		// 初始化统计的数据
		indexMap = new HashMap<>();
		innerMap = new HashMap<>();
		// PO初始化
		mSplashPage = new SplashPage();
		spalashAction = (SplashAction) mSplashPage.action;
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

	@Test(dependsOnMethods = "checkWifiOpen", timeOut = 15000, invocationCount = 2)
	public void bootstrapInResetApp() {
		LogUtil.printCurrentMethodNameInLog4J();
		AppUtil.resetApp();
		long beforeTime = System.currentTimeMillis();
		AndroidDriverWait wait = new AndroidDriverWait(mDriver, 15, 100);
		try {
			wait.until(new ExpectedCondition4AndroidDriver<Boolean>() {
				@Override
				public Boolean apply(AndroidDriver<AndroidElement> arg0) {
					AndroidElement e = arg0.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
					long afterTime = System.currentTimeMillis();
					if (e != null) {
						double diffTime = EssentialUtil.getTheTimeDiff(beforeTime, afterTime);
						LogUtil.i("启动完成时间为:{}", diffTime);
						addCollectedData("启动应用并清除缓存", diffTime);
					}
					return true;// 不关注结果
				}
			});
		} catch (Exception e) {
			LogUtil.e("元素寻找超时(15秒)");
		}
	}

	@Test(dependsOnMethods = "bootstrapInResetApp", timeOut = 15000, invocationCount = 2)
	public void bootstrapInLauncherApp() {
		LogUtil.printCurrentMethodNameInLog4J();
		// 杀死应用并直接启动
		spalashAction.go2killApp(App.PACKAGE_NAME);
		new LauncherPage().go2BootsTheApp();
		mSplashPage.forceWait(2);// 等待
		// 启动
		mSplashPage.action.goLaunchApp();
		//
		long beforeTime = System.currentTimeMillis();
		AndroidDriverWait wait = new AndroidDriverWait(mDriver, 15, 100);
		try {
			wait.until(new ExpectedCondition4AndroidDriver<Boolean>() {
				@Override
				public Boolean apply(AndroidDriver<AndroidElement> arg0) {
					AndroidElement e = arg0.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
					long afterTime = System.currentTimeMillis();
					if (e != null) {
						double diffTime = EssentialUtil.getTheTimeDiff(beforeTime, afterTime);
						LogUtil.i("启动完成时间为:{}", diffTime);
						addCollectedData("直接启动应用", diffTime);
					}
					return true;
				}
			});
		} catch (Exception e) {
			LogUtil.e("元素寻找超时(15秒)");
		}
	}

	@SuppressWarnings("unchecked")
	@Tips(description = "将一个图表类型装配到Map里面", riskPoint = "都是基本数据类型，直接调用Clone")
	@Test(dependsOnMethods = "bootstrapInLauncherApp")
	public void afterWifiConditinoDataCollect() {
		// 将数据加入到整个Map中
		indexMap.put("Wifi环境", (HashMap<String, ArrayList<Double>>) innerMap.clone());
		// 清空Map
		innerMap.clear();
	}

	@Tips(description = "关闭Wifi")
	@Test(dependsOnMethods = "afterWifiConditinoDataCollect")
	public void checkWifiClose() {
		LogUtil.printCurrentMethodNameInLog4J();
		LogUtil.w("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2CloseTheWifi();
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, false);
		// notificationPage.action.go2AppReset();
	}

	@Tips(description = "使用PageObject不够准确，还是采用显示等待")
	@Test(invocationCount = 2, dependsOnMethods = "checkWifiClose")
	public void bootstrapInResetAppWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSplashPage = new SplashPage();
		mSplashPage.action.go2AppReset();
		double diffTime = mSplashPage.action.go2GetTimeDiffElementShow(mSplashPage.btnAccept);
		LogUtil.i("启动完成时间为:{}", diffTime);
		addCollectedData("启动应用并清除缓存", diffTime);
	}

	@Tips(description = "使用PageObject不够准确，还是采用显示等待")
	@Test(invocationCount = 2, dependsOnMethods = "bootstrapInResetAppWithoutWifi")
	public void bootsrtapInLauncherAppWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();
		// 杀死应用并直接启动
		spalashAction.go2killApp(App.PACKAGE_NAME);
		new LauncherPage().go2BootsTheApp();
		mSplashPage.forceWait(2);// 等待
		// 启动
		mSplashPage.action.goLaunchApp();
		double diffTime = mSplashPage.action.go2GetTimeDiffElementShow(mSplashPage.btnAccept);
		LogUtil.i("启动完成时间为:{}", diffTime);
		addCollectedData("直接启动应用", diffTime);
	}

	@SuppressWarnings("unchecked")
	@Test(dependsOnMethods = "bootsrtapInLauncherAppWithoutWifi")
	public void afterNoWifiConditinoDataCollect() {
		// 将数据加入到整个Map中
		indexMap.put("非Wifi环境", (HashMap<String, ArrayList<Double>>) innerMap.clone());
		// 清空Map
		innerMap.clear();
	}

	@Tips(description = "[非测试用例]将收集到的数据添加到容器中")
	public void addCollectedData(String category, double value) {
		if (innerMap.containsKey(category)) {
			innerMap.get(category).add(value);
		} else {
			ArrayList<Double> tempList = new ArrayList<>();
			tempList.add(value);
			innerMap.put(category, tempList);
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

	// ========================= 以下为暂存代码 暂时保留=========================

	@Tips(description = "启动到首页|迁移到PageLoadingCollect")
	public void bootstrap2MainByPO() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSplashPage.action.go2SoftReset();
		MainTempPage mainTempPage = new MainTempPage();
		double diffTime = mainTempPage.action.go2GetTimeDiffElementShow(mainTempPage.rollKeyword);
		LogUtil.i("启动完成时间为:{}", diffTime);
	}

	@Tips(description = "启动到首页|迁移到PageLoadingCollect")
	public void bootstrap2Main() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSplashPage.action.go2SoftReset();
		String rollKeywordUiSelector = "new UiSelector().className(\"android.widget.LinearLayout\").resourceId(\"com.cmic.mmnes:id/search_layout\").childSelector(new UiSelector().className(\"android.widget.TextView\"))";
		long beforeTime = System.currentTimeMillis();
		AndroidDriverWait wait = new AndroidDriverWait(mDriver, 15, 100);
		try {
			wait.until(new ExpectedCondition4AndroidDriver<Boolean>() {
				@Override
				public Boolean apply(AndroidDriver<AndroidElement> arg0) {
					AndroidElement e = arg0.findElementByAndroidUIAutomator(rollKeywordUiSelector);
					long afterTime = System.currentTimeMillis();
					if (e != null)
						LogUtil.i("启动完成时间为:{}", EssentialUtil.getTheTimeDiff(beforeTime, afterTime));
					return true;
				}
			});
		} catch (Exception e) {
			LogUtil.e("元素寻找超时(15秒)");
		}
	}

	@Test(enabled = false)
	public void bootstrapWithoutWithoutResetApp() {

	}

	@Test(enabled = false)
	public void bootstrapInBackground() {

	}
}
