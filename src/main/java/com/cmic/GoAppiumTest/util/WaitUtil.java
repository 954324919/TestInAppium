package com.cmic.GoAppiumTest.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedCondition;

import com.cmic.GoAppiumTest.base.DriverManger;

public class WaitUtil {

	public static void forceWait(int secondWait) {
		try {
			Thread.sleep(secondWait * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void forceWait(double secondWait) {
		try {
			Thread.sleep((long) (secondWait * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void implicitlyWait(int secondWait) {
		DriverManger.getDriver().manage().timeouts().implicitlyWait(secondWait, TimeUnit.SECONDS);
	}

	public static void plicitlyWait(int secondWait, ExpectedCondition ec) {
		//
	}
}
