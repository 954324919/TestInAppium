package com.cmic.GoAppiumTest.base;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.FileUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class BaseTest {
	protected String mTag;
	protected static AndroidDriver<AndroidElement> mDriver;

	static {// 最先执行
		// Log4j
		String log4jConfigFilePath = FileUtil.filePathTransformRelative("/res/log4j/log4j.properties");
		PropertyConfigurator.configure(log4jConfigFilePath);

	}

	@BeforeMethod
	public void tipBeforeTestCase() {
		// 点击同意并使用
		System.out.println("测试用例[" + (++App.CASE_COUNT) + "]开始");
	}

	@AfterMethod
	public void tipAfterTestCase() {
		System.out.println("测试用例[" + (App.CASE_COUNT) + "]结束");
	}

	@Tips(description = "每个Classes的闭包xml中执行的测试用例，目前没有什么特殊性，暂且放入Base")
	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@BeforeSuite
	public void beforeSuit() {
		mDriver = DriverManger.getDriver();
		// TODO 补充适配
		String packageName = App.PACKAGE_NAME;
		if (AppUtil.isInstall(packageName)) {
			mDriver.resetApp();
		}
	}

	@AfterSuite
	public void afterSuit() {
		AppUtil.unInstall(App.PACKAGE_NAME);
		mDriver.quit();
	}
}
