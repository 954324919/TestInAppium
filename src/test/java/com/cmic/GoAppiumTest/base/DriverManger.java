package com.cmic.GoAppiumTest.base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class DriverManger {

	//TODO 后期可移动至BaseTest
	static {
		String configFile = "D:\\EclipseWorkspace\\GoAppium\\GoAppiumTest\\res\\log4j\\log4j.properties";
		PropertyConfigurator.configure(configFile);
	}
	
	private static AndroidDriver<AndroidElement> driver = null;

	public static AndroidDriver<AndroidElement> getDriver() {
		if (driver == null) {
			new DriverManger();
		}
		return driver;
	}

	@SuppressWarnings("rawtypes")
	public DriverManger() {
		// TODO Auto-generated constructor stub
//		File appDir = new File("F:/WorkSpace4Mars/GoAppiumTest/src/test/java/apps");
		File appDir = new File("D:/EclipseWorkspace/GoAppium/GoAppiumTest/src/test/java/apps");
		File app = new File(appDir, "mmnes150.apk");
		// File app = new File(appDir, "ContactManager.apk");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("platformName", "Android");// hock2Appium
		// Emulator
		// capabilities.setCapability("deviceName", "192.168.26.101:5555");
		// capabilities.setCapability("udid", "192.168.26.101:5555");
		// capabilities.setCapability("platformVersion", "6.0");
		// XiaoMi4
		// capabilities.setCapability("udid", "1475993b");
		// capabilities.setCapability("deviceName", "1475993b");
		// capabilities.setCapability("platformVersion", "6.0.1");
		// XiaoMi6
		// capabilities.setCapability("udid", "b31a1bc0");
		// capabilities.setCapability("deviceName", "b31a1bc0");
		// capabilities.setCapability("platformVersion", "7.1.1");
		// N3
		capabilities.setCapability("udid", "cf49f213");
		capabilities.setCapability("deviceName", "cf49f213");
		capabilities.setCapability("platformVersion", "7.1.2");
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		capabilities.setCapability("noSign", true);
		// 每次测试都重新安装app
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("app", app.getAbsolutePath());
		// capabilities.setCapability("appPackage",
		// "com.example.android.contactmanager");
		// capabilities.setCapability("appActivity", ".ContactManager");
		capabilities.setCapability("appPackage", "com.cmic.mmnes");
		capabilities.setCapability("appActivity", "com.cmic.mmnes.activity.SplashActivity");
		// 调用uiautomator2,获取toast
		capabilities.setCapability("automationName", "uiautomator2");
		try {
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
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
}
