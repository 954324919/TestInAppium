package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.DriverManger;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class GoCheck {

	private AndroidDriver<AndroidElement> driver = DriverManger.getDriver();

	@Test
	public void test() throws InterruptedException {
		AndroidElement e1 = driver.findElementByXPath("//android.widget.Button[@content-desc=\"Add Contact\"]");
		e1.click();
		Thread.sleep(3000);
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}

	@Test
	public void test1() throws InterruptedException {
		AndroidElement el = driver.findElementById("com.example.android.contactmanager:id/addContactButton");
		if (el == null) {
			assertEquals(true, false);
			return;
		}
		el.click();
		Thread.sleep(3000);
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}
}
