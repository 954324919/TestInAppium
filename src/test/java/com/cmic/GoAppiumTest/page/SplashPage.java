package com.cmic.GoAppiumTest.page;

import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Heading;
import com.cmic.GoAppiumTest.helper.Tips;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SplashPage extends BasePage {

	@Tips(description = "操作管理类")
	public SplashAction action;

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_refuse")
	public AndroidElement btnRefuce;// 拒绝Button

	@FindBy(id = "com.cmic.mmnes:id/tv_ok")
	public AndroidElement btnAccept;// 同意并使用Button

	@AndroidFindBy(id = "com.cmic.mmnes:id/cb_repeat")
	public AndroidElement cbNoTip; // 不再提示CheckBox

	@AndroidFindBy(className = "android.widget.ScrollView")
	public AndroidElement svProtocol;// 用户协议ScrollView

	public SplashPage(AndroidDriver<AndroidElement> driver) {
		super(driver);
		try {
			PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.action = new SplashAction(driver);
	}

	@Tips(description = "滑动用户协议")
	public void swipeInTheProtocol() {
		waitForce(1);
		action.go2SwipeInElement(svProtocol, Heading.DOWN);
		waitForce(1);
	}

	@Tips(description = "点击不再提示")
	public void switchTheNoTip() {
		sleepImplicitly(1);
		assertNotNull(action);
		assertNotNull(cbNoTip);
		action.go2Click(cbNoTip);
	}

	@Tips(description = "点击拒接协议")
	public void clickRefuceProtocol() {
		sleepImplicitly(1);
		action.go2Click(btnRefuce);
	}

	@Tips(description = "点击接收协议")
	public void clickAcceptProtocol() {
		sleepImplicitly(1);
		action.go2Click(btnAccept);
	}

	@Tips(description = "Spalsh操作管理内部类")
	public class SplashAction extends BaseAction {
		public SplashAction(AndroidDriver<AndroidElement> driver) {
			super(driver);
		}
	}
}
