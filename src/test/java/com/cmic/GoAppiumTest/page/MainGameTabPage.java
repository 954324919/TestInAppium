package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.MainGameTabAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MainGameTabPage extends BasePage {

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\").childSelector(new UiSelector().textContains(\"游戏\"))")
	private AndroidElement topGameTab;// 游戏tab

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"品牌游戏\").resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\")")
	private AndroidElement wellGreatGameItemTv;// 品牌游戏

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"更多游戏\").resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\")")
	private AndroidElement moreBtn;// 更多游戏

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.cmic.mmnes:id/recommend_item_appname_tv\").textContains(\"换一批\")")
	private AndroidElement moreRefreshBatch;// 换一批

	public MainGameTabPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new MainGameTabAction();
	}

	@Tips(description = "点击精品游戏")
	public void clickGreatGame() {
		action.go2Click(wellGreatGameItemTv);
		forceWait(1);
	}

	@Tips(description = "")
	public void clickMoreGame() {
		action.go2Click(moreBtn);
		forceWait(1);
	}

	@Tips(description = "")
	public void clickRefreshBatch() {
		action.go2Click(moreRefreshBatch);
		forceWait(1);
	}

	public boolean getGameTabSelectedStatus() {
		return topGameTab.isSelected();
	}
}