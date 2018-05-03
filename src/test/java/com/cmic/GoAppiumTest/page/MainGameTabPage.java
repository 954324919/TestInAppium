package com.cmic.GoAppiumTest.page;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.MainGameTabAction;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
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

	@AndroidFindBy(className = "android.support.v4.view.ViewPager")
	public List<AndroidElement> elementGameViewPageList;// 底部集团广告

	@AndroidFindBy(id = "com.cmic.mmnes:id/index_item_rl")
	public List<AndroidElement> pageGameItemList;

	public void clickGameAdBannner() {// 点击底部集团广告
		if (pageGameItemList.isEmpty()) {// 如果列表为空，表示页面未能正常加载
			LogUtil.e("判断子项列表为空");
			throw new RuntimeException("判断子项列表为空");
		}
		AndroidElement bottomBanner = pageGameItemList.get(pageGameItemList.size() - 1);
		action.go2Click(bottomBanner);
		WaitUtil.forceWait(3);
	}

	@Tips(description = "获取ViewPage的数目")
	public int gameBannerSize() {
		return elementGameViewPageList.size() - 1;
	}

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