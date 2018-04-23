package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 用于测试一些主页（受影响较大的）非固定的功能，
 * 
 * @author kiwi
 */
@Listeners(ExtentReportListener.class)
public class TestMainActivityExtra extends BaseTest {

	@Override
	public void setUpBeforeClass() {
		// TODO 在没有卸载软件时，可能会报错
		WaitUtil.implicitlyWait(2);// 等待1S
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		ScreenUtil.screenShot("进入必备应用主页界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void testNotification() {
		WaitUtil.implicitlyWait(2);
		LogUtil.printCurrentMethodName();
		DeviceUtil.openNotification();
		WaitUtil.implicitlyWait(2);
		DeviceUtil.closeNotification();
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkNotifyNum() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkSearchKeyWordCycle() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void zoomAndShrink() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void lockOrNotEffect() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void configChangeOreatation() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
	}

}
