package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.SearchAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_refuse")
	public AndroidElement btnRefuce;// 拒绝Button

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_ok")
	public AndroidElement btnAccept;// 同意并使用Button

	public SearchPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new SearchAction();
	}
}
