package com.cmic.GoAppiumTest.base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public abstract class BaseTest {
	protected String mTag;
	protected static AndroidDriver<AndroidElement> mDriver;

	@BeforeClass
	public void beforeClass() {
		mTag = getClass().getSimpleName();
		LogUtil.w("测试用例集[{}]开始", mTag);
		// 屏幕截图
		setUpBeforeClass();
		ScreenUtil.screenShot("进入" + mTag);
	}

	public abstract void setUpBeforeClass();

	public abstract void tearDownAfterClass();

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		tearDownAfterClass();
		LogUtil.w("测试用例集[{}]结束", mTag);
	}

	@BeforeMethod
	public void tipBeforeTestCase() {
		// 点击同意并使用
		LogUtil.i("测试用例[{}]开始", ++App.CASE_COUNT);
	}

	@AfterMethod
	public void tipAfterTestCase() {
		LogUtil.i("测试用例[{}]结束", App.CASE_COUNT);
	}

	@BeforeSuite
	public void beforeSuit() {
		mDriver = DriverManger.getDriver();
		// TODO 补充适配
		String packageName = DriverManger.capaConfig.getProperty("APP_PACKAGE_NAME");
		LogUtil.d("自动化测试老子来了!!{}", packageName);
		if (AppUtil.isInstallWithoutDriver(packageName)) {
			AppUtil.unInstall(packageName);
		}
	}

	@AfterSuite
	@Tips(description = "结束整个测试Suit,暂时关闭卸载")
	public void afterSuit() {
		// AppUtil.unInstall(App.PACKAGE_NAME);
		mDriver.quit();
		AdbManager.excuteAdbShell("adb kill-server");
	}

	@Tips(description = "获取当前应用的Activity名称")
	public String getCurrentPageName() {
		return ContextUtil.getCurrentPageActivtiy();
	}

	@Tips(description = "获取当前应用的Activity名称")
	public String getCurrentPackageName() {
		return ContextUtil.getCurrentPageActivtiy();
	}

	@Tips(description = "用于非确定的页面返回")
	public void go2Backforward() {
		PageRouteUtil.pressBack();
		WaitUtil.forceWait(1);
	}

	@Tips(description = "用于某些非确定页面滚动到底部")
	public void go2Swipe2Bottom() {
		ScrollUtil.scrollToBase();
	}
}
