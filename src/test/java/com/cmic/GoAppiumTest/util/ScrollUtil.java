package com.cmic.GoAppiumTest.util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;

import com.cmic.GoAppiumTest.base.DriverManger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class ScrollUtil {
	public static final int SCROLL_TIME = 1000;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public static void scrollToPrecent(ScrollUtil.Direction direction, int precentRate) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		// 屏幕宽高
		int width = ScreenUtil.getDeviceWidth() - 1;
		int height = ScreenUtil.getDeviceHeight() - 1;
		switch (direction) {
		case UP:
			driver.swipe(width / 2, height, width / 2, height * precentRate / 100, SCROLL_TIME);
			break;
		case DOWN:
			driver.swipe(width / 2, width * precentRate / 100, width / 2, 1, SCROLL_TIME);
			break;
		case LEFT:
			driver.swipe(width, height / 2, (int) (width * (1-precentRate / 100.0)), height / 2, SCROLL_TIME);
			break;
		case RIGHT:
			driver.swipe(1, height / 2, width * precentRate / 100, height / 2, SCROLL_TIME);
			break;
		default:
			break;
		}
	}

	public static void screentRotate(ScreenOrientation oreentation) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		driver.rotate(oreentation);
	}

	/**
	 * 如果是滑动的话，建议使用classname 来遍历，
	 * 通过for循环遍历出当前显示的text是否是需要查找的如果不是，滑动操作，直到找到并点击，xpath不太实用这种滑动页面
	 * 
	 * @param element
	 */
	public static void scrollToElement(String xPath) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		if (ElementUtil.isElementExistByXpath(xPath)) {
			scrollToPrecent(Direction.DOWN, 50);
		}
		// 存在xpath已经定位而没有出现的情况
		// TODO 优化
	}

	public static void scrollToBase() {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		String str1;
		String str2;
		do {
			str1 = driver.getPageSource();
			driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, 500);
			WaitUtil.implicitlyWait(1);
			str2 = driver.getPageSource();
		} while (!str1.equals(str2));
	}

	public static void scrollToTargetWidget(AndroidDriver<AndroidElement> driver, By by) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;

		while (!ElementUtil.isElementPresent(by)) {
			try {
				driver.findElement(by);
			} catch (NoSuchElementException e) {
				driver.swipe(width / 2, height / 2, width / 2, height / 3, 100);
			}
		}
	}
	
	public static void scrollToHalfScreen() {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		DriverManger.getDriver().swipe(width / 2, height * 3 / 4, width / 2, height / 4, 750);
	}

}
