package com.cmic.GoAppiumTest.base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

	private static List<DeviceEntity> deviceList = new ArrayList<>();

	// 获取当前挂载的设备列表
	static {
		deviceList = AdbManager.fetchTheMountDeviceInfo();
	}

	private static AndroidDriver<AndroidElement> driver = null;

	private Properties capaConfig;

	public static AndroidDriver<AndroidElement> getDriver() {
		if (driver == null) {
			new DriverManger();
		}
		return driver;
	}

	@Tips(riskPoint = "必须联网防止Error: getaddrinfo ENOENT")
	public DriverManger() {
		LogUtil.e("FromHere2TheAppiumAutoTest{}", deviceList.size());
		// String filePath = App.savePath + File.separator + "RawAttachment";
		String filePath = "D:\\EclipseWorkspace\\GoAppium\\GoAppiumTest\\res\\apps";
		// LogUtil.e("格式化路径为{}", filePath);
		// String appDir = FileUtil.filePathTransformRelative("/res/apps");
		File app = new File(filePath, "mmnes150.apk");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		// 读取设备配置文件
		capaConfig = PropertiesUtil.load(FileUtil.filePathTransformRelative("/res/ini/capa.properties"));
		if (capaConfig == null) {
			throw new RuntimeException("不存在目标的配置文件");
		}
		// 配置区域
		capabilities.setCapability("platformName", capaConfig.getProperty("PLATFORM_TYPE"));// hock2Appium
		capabilities.setCapability("udid", capaConfig.getProperty("UDID"));
		capabilities.setCapability("deviceName", capaConfig.getProperty("DEVICE_NAME"));
		capabilities.setCapability("platformVersion", capaConfig.getProperty("PLATFORM_VERSION"));
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
}
