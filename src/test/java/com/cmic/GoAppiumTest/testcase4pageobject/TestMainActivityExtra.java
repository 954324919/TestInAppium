package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.LogUtil;

public class TestMainActivityExtra extends BaseTest {

	private MainTempPage mMainTempPage;

	@Override
	public void setUpBeforeClass() {
		// TODO 在没有卸载软件时，可能会报错
		mMainTempPage = new MainTempPage();
		mMainTempPage.action.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// 先确认是否进入该页面
		LogUtil.w("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(getCurrentPageName(), "MainActivity");
		mMainTempPage.snapScreen("进入必备应用主页界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void testNotification() {
		LogUtil.printCurrentMethodNameInLog4J();
		mMainTempPage.action.go2OpenNotifications();
		mMainTempPage.snapScreen("打开通知栏");
		mMainTempPage.action.go2CloseNotification();
		mMainTempPage.snapScreen("关闭通知栏");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkNotifyNum() {
		LogUtil.printCurrentMethodNameInLog4J();
		LogUtil.w("可更新的应用数目为{}", mMainTempPage.getCanUpdataNum());
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkSearchKeyWordCycle() {
		LogUtil.printCurrentMethodNameInLog4J();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void zoomAndShrink() {
		LogUtil.printCurrentMethodNameInLog4J();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void lockOrNotEffect() {
		LogUtil.printCurrentMethodNameInLog4J();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void configChangeOreatation() {
		LogUtil.printCurrentMethodNameInLog4J();
	}
}
