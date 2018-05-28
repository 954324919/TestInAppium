package com.cmic.GoAppiumTest.base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.bean.DeviceEntity;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.FileUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PropertiesUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class DriverManger {

	public static Properties capaConfig;
	// 获取当前挂载的设备列表
	static {
		// 初始化测试应用的配置信息
		capaConfig = PropertiesUtil.load(FileUtil.filePathTransformRelative("/res/ini/capa.properties"));
		if (capaConfig == null) {
			throw new RuntimeException("不存在目标的配置文件");
		}

	}

	private static AndroidDriver<AndroidElement> driver = null;

	public static AndroidDriver<AndroidElement> getDriver() {
		if (driver == null) {
			synchronized (DriverManger.class) {
				if (driver == null) {
					new DriverManger();
				}
			}
		}
		return driver;
	}

	@Tips(riskPoint = "必须联网防止Error: getaddrinfo ENOENT")
	private DriverManger() {
		DeviceEntity targetMouteDevice = AdbManager.targetDevice;// 当前只支持1个设备
		if (targetMouteDevice == null) {// 检查目标测试设备是否符合被挂载
			throw new RuntimeException("不存在对应的设备");
		}
		LogUtil.w(true, "设备信息为{},{}", targetMouteDevice.getDeviceBrand(), targetMouteDevice.getSerialNumber());
		// 获取测试应用
		String appDir = App.SAVEPATH + File.separator + "RawAttachment";
		File app = new File(appDir, "mmnes150.apk");
		// 配置区域
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("udid", targetMouteDevice.getSerialNumber());
		capabilities.setCapability("deviceName", targetMouteDevice.getDeviceBrand());
		capabilities.setCapability("platformVersion", targetMouteDevice.getTargetSdk());
		// 测试包名
		capabilities.setCapability("appPackage", capaConfig.getProperty("APP_PACKAGE_NAME"));
		capabilities.setCapability("appActivity", capaConfig.getProperty("APP_LAUNCHER_ACTIVITY"));
		// 键盘配置区域
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		// 每次测试都重新安装app
		capabilities.setCapability("noSign", true);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("app", app.getAbsolutePath());
		// 调用uiautomator2,获取toast
		capabilities.setCapability("automationName", "uiautomator2");
		// WebDriver
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		try {
			driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			driver = null;
		}
	}

	public void quitDriver() {
		if (driver != null) {
			driver.quit();
		}
	}

	public Properties getBaseCapabilities() {
		return capaConfig;
	}

	// @Tips(description = "获取UDID")
	// public static String getUdid() {
	// return device.getSerialNumber();
	// }
}
