package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LauncherPage extends BasePage {

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='必备应用' and @content-desc='必备应用']")
	public AndroidElement mmnesAppIcon;

	public LauncherPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new LaunchAction();
	}

	public void go2BootsTheApp() {
		action.go2Click(mmnesAppIcon);
	}

	public class LaunchAction extends BaseAction {
		@Override
		public void go2SelfPage() {
			// TODO Auto-generated method stub
		}
	}
}
