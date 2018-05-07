package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.WebViewAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WebViewAdPage extends BasePage {

	public AndroidElement xxx;

	public WebViewAdPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new WebViewAction();
	}
}
