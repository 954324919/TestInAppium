package com.cmic.GoAppiumTest.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedCondition;

import com.cmic.GoAppiumTest.base.DriverManger;

public class WaitUtil {

	public static void forceWait(int secondWait) throws InterruptedException {
		Thread.sleep(secondWait * 1000);
	}

	public static void forceWait(double secondWait) throws InterruptedException {
		Thread.sleep((long) (secondWait * 1000));
	}

	public static void implicitlyWait(int secondWait) {
		DriverManger.getDriver().manage().timeouts().implicitlyWait(secondWait, TimeUnit.SECONDS);
	}

	public static void plicitlyWait(int secondWait, ExpectedCondition ec) {
		//
	}
}
