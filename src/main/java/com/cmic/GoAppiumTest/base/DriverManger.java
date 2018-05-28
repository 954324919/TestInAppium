package com.cmic.GoAppiumTest.base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
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
	public static Properties capaConfig;
	public static DeviceEntity device;
	// 获取当前挂载的设备列表
	static {
		// 初始化测试应用的配置信息
		capaConfig = PropertiesUtil.load(FileUtil.filePathTransformRelative("/res/ini/capa.properties"));
		if (capaConfig == null) {
			throw new RuntimeException("不存在目标的配置文件");
		}
		// 加载挂载设备列表
		deviceList = AdbManager.fetchTheMountDeviceInfo();
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
		device = deviceCheck();// 当前只支持1个设备
		DeviceEntity targetMouteDevice = device;// 当前只支持1个设备
		if (targetMouteDevice == null) {// 检查目标测试设备是否符合被挂载
			throw new RuntimeException("不存在对应的设备");
		}
		LogUtil.e("设备信息为{},{}",targetMouteDevice.getDeviceBrand(),targetMouteDevice.getSerialNumber());
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

	private DeviceEntity deviceCheck() {
		String deviceName = App.DEVICENAME_LIST;
		String deviceModelName = App.DEVICEMODEL_LIST;
		// 0527当前默认只挂载一个设备
		deviceName = deviceName.split(",")[0];
		deviceModelName = deviceModelName.split(",")[0];
		// FIXME 该部分的代码逻辑还需要优化，需求不太情绪
		for (int i = 0; i < deviceList.size(); i++) {// 找到1个匹配
			if (deviceList.get(i).getDeviceModelName().equals(deviceModelName)) {
				DeviceEntity temp = deviceList.get(i);
				temp.setDeviceBrand(deviceName);
				return temp;
			}
		}
		return null;
	}

	public void quitDriver() {
		if (driver != null) {
			driver.quit();
		}
	}

	public Properties getBaseCapabilities() {
		return capaConfig;
	}

	@Tips(description = "获取UDID")
	public static String getUdid() {
		return device.getSerialNumber();
	}
}
