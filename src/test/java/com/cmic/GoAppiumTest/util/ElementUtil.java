package com.cmic.GoAppiumTest.util;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Heading;
import com.cmic.GoAppiumTest.helper.Tips;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyMetastate;

public class ElementUtil {

	public static int getElementX(AndroidElement element) {// 左上角X
		return element.getLocation().getX();
	}

	public static int getElementY(AndroidElement element) {// 右上角Y
		return element.getLocation().getY();
	}

	public static int getElementWidth(AndroidElement element) {
		return element.getSize().width;
	}

	public static int getElementHeight(AndroidElement element) {// 右上角Y
		return element.getSize().height;
	}

	// 判断页面内是否存在元素
	public static boolean isElementExistByXpath(String xpath) {
		try {
			DriverManger.getDriver().findElement(By.xpath(xpath));
			return true;
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			return false;
		}
	}

	/**
	 * 控件内上下滑动
	 * @param e  控件
	 * @param heading 方向
	 */
	public static void swipeControl(AndroidElement e, Heading heading) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		// 获取控件开始位置的坐标轴
		Point start = e.getLocation();
		int startX = start.x;
		int startY = start.y;
		// 获取控件宽高
		Dimension q = e.getSize();
		int x = q.getWidth();
		int y = q.getHeight();
		// 计算出控件结束坐标
		int endX = x + startX;
		int endY = y + startY;
		// 计算中间点坐标
		int centreX = (endX + startX) / 2;
		int centreY = (endY + startY) / 2;
		switch (heading) {
		// 向上滑动
		case UP:
			driver.swipe(centreX, startY + 1, centreX, endY - 1, 1500);
			break;
		// 向下滑动
		case DOWN:
			driver.swipe(centreX, endY - 1, centreX, startY + 1, 1500);
			break;
		case LEFT:
			driver.swipe(endX - 1, centreY, startX + 1, centreY, 1500);
			break;
		case RIGHT:
			driver.swipe(startX + 1, centreY, endX - 1, centreY, 1500);
			break;
		default:
			break;
		}
	}

	/**
	 * 控件内上下滑动
	 *
	 * @param step
	 *            测试步骤
	 * @param by
	 *            控件定位方式
	 * @param heading
	 *            滑动方向 UP DOWN
	 */
	public static void swipeControl(By by, Heading heading) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		// 获取控件开始位置的坐标轴
		Point start = driver.findElement(by).getLocation();
		int startX = start.x;
		int startY = start.y;
		// 获取控件宽高
		Dimension q = driver.findElement(by).getSize();
		int x = q.getWidth();
		int y = q.getHeight();
		// 计算出控件结束坐标
		int endX = x + startX;
		int endY = y + startY;
		// 计算中间点坐标
		int centreX = (endX + startX) / 2;
		int centreY = (endY + startY) / 2;
		switch (heading) {
		// 向上滑动
		case UP:
			driver.swipe(centreX, startY + 1, centreX, endY - 1, 1500);
			break;
		// 向下滑动
		case DOWN:
			driver.swipe(centreX, endY - 1, centreX, startY + 1, 1500);
			break;
		case LEFT:
			driver.swipe(endX - 1, centreY, startX + 1, centreY, 1500);
			break;
		case RIGHT:
			driver.swipe(startX + 1, centreY, endX - 1, centreY, 1500);
			break;
		default:
			break;
		}
	}

	@Tips(description = "与isElementAccessable共同页面验证存在控件")
	public static boolean isElementPresent(By by) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Tips(description = "比isElementPresent更加稳健判断的方法，需要增加额外等待时间")
	public static boolean isElementPresentSafe(By by) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		try {
			WaitUtil.implicitlyWait(5);// 添加等待
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Tips(description = "与isElementAccessable共同页面验证存在控件")
	public static boolean isElementPresent(String uiSelector) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		try {
			driver.findElementByAndroidUIAutomator(uiSelector);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Tips(description = "与isElementPresent共同页面验证存在控件")
	public boolean isElementAccessable(By by) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		try {
			driver.findElement(by).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Tips(description = "黏贴复制内容", riskPoint = "未测试")
	public static void ctrlVPaste(AndroidElement el) {
		el.click(); // 获取焦点
		// Ctrl+V组合操作
		DriverManger.getDriver().pressKeyCode(50, AndroidKeyMetastate.META_CTRL_ON);
	}

	public static boolean isTargetToast(String targetToast) {
		try {
			final WebDriverWait wait = new WebDriverWait(DriverManger.getDriver(), 3);
			Assert.assertNotNull(wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath(".//*[contains(@text,'" + targetToast + "')]"))));
			System.out.println("找到了toast " + targetToast);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
