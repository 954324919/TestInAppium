package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.TrafficManagerPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.AndroidElement;

public class TestTrafficManagerActivitiy extends BaseTest {

	private int mRetryTime = 3;
	private TrafficManagerPage mTrafficPage;

	@Override
	public void setUpBeforeClass() {
		// TODO 在没有卸载软件时，可能会报错
		mTrafficPage = new TrafficManagerPage();
		mTrafficPage.action.go2SelfPage();
		mTrafficPage.forceWait(3);// 加载比较慢
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// 先确认是否进入该页面
		LogUtil.w("进行{}用例集的初始化检验，失败则跳过该用例集的所有测试", mTag);
		if (!getCurrentPageName().equals("TrafficDetailActivity")) {
			mTrafficPage.action.go2SelfPage();
			mTrafficPage.forceWait(mRetryTime + 2);
		}
		assertEquals(getCurrentPageName(), "TrafficDetailActivity");
		mTrafficPage.snapScreen("进入必备应用流量管家界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkTrafficInfo() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		mTrafficPage.snapScreen("查询页面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试滑动切换")
	public void checkSlipToOtherTab() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		mTrafficPage.action.go2SwipeFullScreen(Direction.LEFT, 80);
		mTrafficPage.forceWait(2);
		mTrafficPage.snapScreen("滑动-切换到按类型查看");
		mTrafficPage.action.go2SwipeFullScreen(Direction.RIGHT, 80);
		mTrafficPage.forceWait(2);
		mTrafficPage.snapScreen("滑动-切换到按套餐查看");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试点击切换")
	public void checkClick2OtherTab() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		mTrafficPage.clickTrafficType();// 按类型查看
		mTrafficPage.snapScreen("切换到按类型查看");
		mTrafficPage.clickTrafficSuit();// 按套餐查看
		mTrafficPage.snapScreen("点击-切换到按套餐查看");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试下拉刷新", riskPoint = "截图不一定准确，需要调整")
	public void checkDropDown2Refesh() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		mTrafficPage.drawDown2Refresh(3);// 下拉3%
		mTrafficPage.snapScreen("小滑动未触发加载");
		mTrafficPage.forceWait(1);
		mTrafficPage.drawDown2Refresh(50);// 下拉50%
		mTrafficPage.snapScreen("大滑动触发加载");
		mTrafficPage.forceWait(2);
	}
}
