package com.cmic.GoAppiumTest.base;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
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
import com.cmic.GoAppiumTest.util.FileUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.PropertiesUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public abstract class BaseTestNew {
	protected String mTag;
	protected static AndroidDriver<AndroidElement> mDriver;

	private static Properties capaConfig;
	static {// 最先执行
		// Log4j
		String log4jConfigFilePath = FileUtil.filePathTransformRelative("/res/log4j/log4j.properties");
		PropertyConfigurator.configure(log4jConfigFilePath);

		capaConfig = PropertiesUtil.load(FileUtil.filePathTransformRelative("/res/ini/capa.properties"));
		if (capaConfig == null) {
			throw new RuntimeException("不存在目标的配置文件");
		}
	}

	@BeforeClass
	public void beforeClass() {
		mTag = getClass().getSimpleName();
		// 屏幕截图
		setUpBeforeClassOrigin();
	}

	public abstract void setUpBeforeClassOrigin();

	public abstract void tearDownAfterClassOrigin();

	public abstract void setUpBeforeMTestCaseOrigin();

	public abstract void tearDownAfterMTestCaseOrigin();

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		tearDownAfterClassOrigin();
	}

	@BeforeMethod
	public void tipBeforeTestCase() {
		setUpBeforeMTestCaseOrigin();
	}

	@AfterMethod
	public void tipAfterTestCase() {
		tearDownAfterMTestCaseOrigin();
	}

	@BeforeSuite
	public void beforeSuit() {
		// TODO 补充适配
		String packageName = capaConfig.getProperty("APP_PACKAGE_NAME");
		// LogUtil.d("Hello SuitBefore{}",packageName);//TODO 0501 在有多个可调式设备时会出现异常，需要Fix
		if (AppUtil.isInstallWithoutDriver(packageName)) {
			AppUtil.unInstall(packageName);
		}
		mDriver = DriverManger.getDriver();
	}

	@AfterSuite
	@Tips(description = "结束整个测试Suit,暂时关闭卸载")
	public void afterSuit() {
		// AppUtil.unInstall(App.PACKAGE_NAME);
		mDriver.quit();
	}
}
