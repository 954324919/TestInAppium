package com.cmic.GoAppiumTest.page;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.TrafficAction;
import com.cmic.GoAppiumTest.util.WaitUtil;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class TrafficManagerPage extends BasePage {

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"按类型查看\")")
	public AndroidElement tabTrafficType;// 按类型查看

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"按套餐查看\")")
	public AndroidElement tabTrafficSuit;// 按套餐查看

	public TrafficManagerPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new TrafficAction();
	}

	@Tips(description = "检查页面是否加载")
	public boolean checkInit() {
		try {
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Tips(description = "点击切换到TrafficType")
	public void clickTrafficType() {
		action.go2Click(tabTrafficType);
		forceWait(1);
	}

	@Tips(description = "点击切换到TrafficSuit")
	public void clickTrafficSuit() {
		action.go2Click(tabTrafficSuit);
		forceWait(1);
	}

	@Tips(description = "下拉刷新")
	public void drawDown2Refresh(int precent) {
		WaitUtil.implicitlyWait(5);
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height / 2, width / 2, height / 2 + height * precent / 200, 500);
	}
}
