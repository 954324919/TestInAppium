package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.MainSoftTabAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MainSoftTabPage extends BasePage {

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\").childSelector(new UiSelector().textContains(\"游戏\"))")
	public AndroidElement topGameTab;// 游戏tab

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\").childSelector(new UiSelector().textContains(\"软件\"))")
	public AndroidElement topSoftwareTab;// 软件tab

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\").textContains(\"精选福利\")")
	public AndroidElement wellSelectItemTv;// 精选福利

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"更多软件\").resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\")")
	public AndroidElement moreBtn;// 更多应用

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\").textContains(\"换一批\")")
	public AndroidElement refreshBatch;// 换一批

	public MainSoftTabPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new MainSoftTabAction();
	}

	@Tips(description = "切换到游戏")
	public void clickSoftTab() {
		action.go2Click(topSoftwareTab);
		forceWait(1);
	}

	@Tips(description = "切换到软件")
	public void clickGameTab() {
		action.go2Click(topGameTab);
		forceWait(2);
	}

	@Tips(description = "精选应用")
	public void clickWellSelect() {
		action.go2Click(wellSelectItemTv);
		forceWait(1);
	}

	@Tips(description = "更多应用")
	public void clickMoreSoft() {
		action.go2Click(moreBtn);
		forceWait(1);
	}

	@Tips(description = "换一批")
	public void clickRefreshBatch() {
		action.go2Click(refreshBatch);
		forceWait(1);
	}

	@Tips(description = "游戏Tab是否选中")
	public boolean getGameTabSelectedStatus() {
		return topGameTab.isSelected();
	}

	@Tips(description = "软件Tab是否选中")
	public boolean getSoftTabSelectedStatus() {
		return topSoftwareTab.isSelected();
	}
}