package com.cmic.GoAppiumTest.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import sun.security.jca.GetInstance.Instance;

public class BasePage {

	protected static AndroidDriver<AndroidElement> driver = DriverManger.getDriver();

	@Tips(description = "操作管理类")
	public BaseAction action;

	/**
	 * 
	 * @param object
	 *            PageObject的具体类型
	 */
	@Tips(description = "构造函数")
	public BasePage(Object clazz) {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), clazz);
	}

	@Tips(description = "构造函数", riskPoint = "造成子类action操作需要强制转换")
	public BasePage(BaseAction action) {
	}

	@Tips(description = "无参数构造方法")
	public BasePage() {

	}

	@Tips(description = "等待直到控件可可视")
	protected void waitForVisibilityOf(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	@Tips(description = "等待直达控件可点击")
	protected void waitForClickabilityOf(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	@Tips(description = "强制休眠")
	public void forceWait(int sleepTime) {
		WaitUtil.forceWait(sleepTime);
	}

	@Tips(description = "进行截屏")
	public void snapScreen(String snapMsg) {
		ScreenUtil.screenShot(snapMsg);
		WaitUtil.implicitlyWait(2);
	}

	@Tips(description = "隐士等待")
	public void implicitlyWait(int implicitlyTime) {
		WaitUtil.implicitlyWait(implicitlyTime);
	}

	@Tips(description = "获取当前的activity,返回文件名")
	public String getCurrActivity() {
		String str = driver.currentActivity();
		return str.substring(str.lastIndexOf(".") + 1);
	}

	@Tips(description = "用于获取设备的dpi", //
			riskPoint = "目前只支持Win，不同的操作系统需要兼容")
	public int getScreenDpi() {
		return 0;
	}
	
	@Tips(description = "获取当前屏幕宽度")
	public static int getDeviceWidth() {
		return driver.manage().window().getSize().width;
	}

	@Tips(description = "获取当前设备屏幕高度")
	public static int getDeviceHeight() {
		return driver.manage().window().getSize().height;// 获取手机屏幕高度
	}

	@Tips(description = "检测元素是否显示")
	public boolean isElementIsPresent(AndroidElement e) {
		return false;
	}
}
