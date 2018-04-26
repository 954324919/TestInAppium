package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.SettingAction;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SettingPage extends BasePage {
	public SettingPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new SettingAction();
	}
}
