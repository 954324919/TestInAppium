package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Heading;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.SplashAction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SplashPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_refuse")
	public AndroidElement btnRefuce;// 拒绝Button

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_ok")
	public AndroidElement btnAccept;// 同意并使用Button

	@AndroidFindBy(id = "com.cmic.mmnes:id/cb_repeat")
	public AndroidElement cbNoTip; // 不再提示CheckBox

	@AndroidFindBy(className = "android.widget.ScrollView")
	public AndroidElement svProtocol;// 用户协议ScrollView

	public SplashPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new SplashAction();
	}

	@Tips(description = "滑动用户协议")
	public void swipeInTheProtocol() {
		forceWait(1);
		action.go2SwipeInElement(svProtocol, Heading.DOWN);
		forceWait(1);
	}

	@Tips(description = "点击不再提示")
	public void switchTheNoTip() {
		implicitlyWait(1);
		action.go2Click(cbNoTip);
	}

	@Tips(description = "点击拒接协议")
	public void clickRefuceProtocol() {
		implicitlyWait(1);
		action.go2Click(btnRefuce);
	}

	@Tips(description = "点击接收协议")
	public void clickAcceptProtocol() {
		implicitlyWait(1);
		action.go2Click(btnAccept);
	}

	@Tips(description = "只是为了测试加入，无意义")
	public void test4test() {
		((SplashAction) action).go2SelfPage();
	}
}
