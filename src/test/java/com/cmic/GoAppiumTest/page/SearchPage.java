package com.cmic.GoAppiumTest.page;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.SearchAction;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_more")
	public AndroidElement btnGetMore;// 加载更多的Button|收起

	@AndroidFindBy(className = "android.widget.LinearLayout")
	public List<AndroidElement> hotwordItemList;// 热词列表

	@AndroidFindBy(id = "com.cmic.mmnes:id/search_clear_iv")
	public AndroidElement btnClear;// 清除历史的按钮

	@AndroidFindBy(id = "com.cmic.mmnes:id/searchText")
	private AndroidElement searchContainer;// 内部热词

	public SearchPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new SearchAction();
	}

	public void clickCleanHistory() {
		action.go2Click(btnClear);
		forceWait(1);
	}

	public void clickLoadMoreSwitch() {
		action.go2Click(btnGetMore);
	}

	public int getCountOfTargetHotWord() {
		return hotwordItemList != null ? hotwordItemList.size() : 0;
	}

	public String getCurrentSearchKeyWord() {
		return searchContainer.getText();
	}

	public void randomClickHotword() {
		Random random = new Random();
		// TODO 模拟一个数字
		int randomIndex = 0;
		if (hotwordItemList.size() > 3) {
			randomIndex = random.nextInt(hotwordItemList.size() - 3);
			AndroidElement hotkeyItem = hotwordItemList.get(randomIndex);
			String searchBeforePerform = hotkeyItem.getText();
			if (searchBeforePerform.equals("软件") || searchBeforePerform.equals("游戏")
					|| searchBeforePerform.equals("热门")) {
				searchBeforePerform = hotwordItemList.get(randomIndex + 1).getText();
			}
			action.go2Click(hotkeyItem);
			forceWait(1);
		}
	}
}
