package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 用于测试一些主页（受影响较大的）非固定的功能，
 * @author kiwi
 */
public class TestMainActivityExtra {

	private String mTag;
	private AndroidDriver<AndroidElement> mDriver;

	@BeforeMethod
	public void tipBeforeTestCase() {
		// 点击同意并使用
		System.out.println("测试用例[" + (++App.CASE_COUNT) + "]开始");
	}

	@AfterMethod
	public void tipAfterTestCase() {
		System.out.println("测试用例[" + (App.CASE_COUNT) + "]结束");
	}

	@BeforeClass
	@Tips(description="假设已经入MainAct&&未跳转到其他页面")
	public void beforeClass() {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		WaitUtil.implicitlyWait(2);// 等待1S
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test(enabled=false)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		ScreenUtil.screenShot("进入必备应用主页界面");
		WaitUtil.implicitlyWait(2);
	}
	
	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkNotifyNum(){
		
	}
	
	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkSearchKeyWordCycle(){
		
	}
	
	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void zoomAndShrink(){
		
	}
	
	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void lockOrNotEffect(){
		
	}
	
	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void configChangeOreatation(){
		
	}
	
}