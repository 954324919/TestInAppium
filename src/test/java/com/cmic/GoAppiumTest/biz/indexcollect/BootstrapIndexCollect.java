package com.cmic.GoAppiumTest.biz.indexcollect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.bean.BarChartData;
import com.cmic.GoAppiumTest.biz.base.BaseTest4IndexCollect;
import com.cmic.GoAppiumTest.helper.AndroidDriverWait;
import com.cmic.GoAppiumTest.helper.ExpectedCondition4AndroidDriver;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SplashPage;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.EssentialUtil;
import com.cmic.GoAppiumTest.util.JFreeCharUtil;
import com.cmic.GoAppiumTest.util.LogUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

@Tips(description = "统计应用启动时间", riskPoint = "使用PageObject较之DriverWait不够准确，不采用")
public class BootstrapIndexCollect extends BaseTest4IndexCollect {

	private SplashPage mSplashPage;

	private Map<String, HashMap<String, ArrayList<Double>>> indexMap;
	private HashMap<String, ArrayList<Double>> innerMap;

	@Override
	public void tearDownAfterClass() {

		List<BarChartData> chartDatas = new ArrayList<>();

		// 进行图表绘制
		Iterator<Map.Entry<String, HashMap<String, ArrayList<Double>>>> it = indexMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, HashMap<String, ArrayList<Double>>> entryOutside = it.next();
			// 内部Map
			HashMap<String, ArrayList<Double>> innerMap = entryOutside.getValue();
			Iterator<Map.Entry<String, ArrayList<Double>>> itInner = innerMap.entrySet().iterator();
			while (it.hasNext()) {
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
		new JFreeCharUtil.BarChartBuilder()//
				.setTitle("几种不同情况下的页面平均加载速度")//
				.setXAxisName("X轴:测试事件").setYAxisName("Y轴:加载时间/秒")//
				.setImagePath("E:\\WebWorkSpace\\TestPackage\\target\\测试结果.jpg")//
				.setDataSource(chartDatas)//
				.outputImage();
	}

	@Override
	public void setUpBeforeClass() {
		AppUtil.killApp(App.PACKAGE_NAME);
		// 初始化统计的数据
		indexMap = new HashMap<>();
	}

	@Test(timeOut = 15000, invocationCount = 2)
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
						addCollectedData("bootstrapInResetApp", diffTime);
					}
					return true;// 不关注结果
				}
			});
		} catch (Exception e) {
			LogUtil.e("元素寻找超时(15秒)");
		}
	}

	@Test(timeOut = 15000, invocationCount = 2)
	public void bootstrapInLauncherApp() {
		LogUtil.printCurrentMethodNameInLog4J();
		AppUtil.launchApp();
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
						addCollectedData("bootstrapInLauncherApp", diffTime);
					}
					return true;
				}
			});
		} catch (Exception e) {
			LogUtil.e("元素寻找超时(15秒)");
		}
	}

	@Tips(description = "使用PageObject不够准确，还是采用显示等待")
	@Test(invocationCount = 3)
	public void bootstrapInResetAppByPO() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSplashPage = new SplashPage();
		mSplashPage.action.go2AppReset();
		double diffTime = mSplashPage.action.go2GetTimeDiffElementShow(mSplashPage.btnAccept);
		LogUtil.i("启动完成时间为:{}", diffTime);

	}

	@Tips(description = "使用PageObject不够准确，还是采用显示等待")
	@Test(invocationCount = 3)
	public void bootsrtapInLauncherAppByPO() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSplashPage = new SplashPage();
		mSplashPage.action.goLaunchApp();
		double diffTime = mSplashPage.action.go2GetTimeDiffElementShow(mSplashPage.btnAccept);
		LogUtil.i("启动完成时间为:{}", diffTime);
	}

	@Tips(description = "启动到首页|迁移到PageLoadingCollect")
	public void bootstrap2MainByPO() {
		LogUtil.printCurrentMethodNameInLog4J();
		// mSplashPage.action.go2PressAndroidKey(AndroidKeyCode.HOME);
		// mSplashPage.action.go2killApp(App.PACKAGE_NAME);
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

	public void addCollectedData(String category, double value) {
		if (innerMap.containsKey(category)) {
			innerMap.get(category).add(value);
		} else {
			ArrayList<Double> tempList = new ArrayList<>();
			tempList.add(value);
			innerMap.put(category, tempList);
		}
	}
}
