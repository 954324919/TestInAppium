package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.LoginAction;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage extends BasePage {
	public LoginPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new LoginAction();
	}
}
