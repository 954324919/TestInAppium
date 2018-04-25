package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.Tips;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

@Tips(description = "Spalsh操作管理内部类")
public class SplashAction extends BaseAction {
	public SplashAction(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
}