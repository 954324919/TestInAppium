package com.cmic.GoAppiumTest.page.middlepage;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.action.SearchAction;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.RandomUtil;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchResultPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/search_count_tv")
	public AndroidElement searchResultCount;// 搜索结果数目标签

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"全部\")")
	public AndroidElement searchResultTabAll;// 搜索结果全部的Tab

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"软件\")")
	public AndroidElement searchResultTabGame;// 搜索结果Game的Tab

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"游戏\")")
	public AndroidElement searchResultTabSoft;// 搜索结果Software的Tab

	@AndroidFindBy(id = "com.cmic.mmnes:id/item_layout")
	public List<AndroidElement> targetSerachResultList;// 具体到某个Tab中的SearchResult结果

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")")
	public List<AndroidElement> statusBtnList;// 具体到某个Tab中的下载按钮结果

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_down_goon")
	public AndroidElement downLoadGoOn;// 下载继续按钮

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\").textContains(\"打开\")")
	public List<AndroidElement> downLoadOpenList;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"建议你 \")")
	public AndroidElement errorTip;

	public SearchResultPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new SearchResultAction();
	}

	public boolean getErrorTipVisiable() {
		return errorTip.isDisplayed();
	}

	public boolean getPageLoadFinishOrNot() {
		return isElementIsPresent(searchResultCount);
	}

	public boolean isDownloadGoOnShow() {
		return downLoadGoOn.isDisplayed();
	}

	public void click2GameTab() {
		action.go2Click(searchResultTabGame);
		forceWait(1);
	}

	public void click2GoOnDownload() {
		action.go2Click(downLoadGoOn);
		forceWait(1);
	}

	public void click2SoftTab() {
		action.go2Click(searchResultTabSoft);
		forceWait(1);
	}

	public void click2AllTab() {
		action.go2Click(searchResultTabAll);
		forceWait(1);
	}

	public boolean allTabSelected() {
		return searchResultTabAll.isSelected();
	}

	public boolean softTabSelected() {
		return searchResultTabSoft.isSelected();
	}

	public boolean gameTabSelected() {
		return searchResultTabGame.isSelected();
	}

	public int getSearchResultCount() {
		return targetSerachResultList.size();
	}

	public int getTargetTabDownloadBtnCount() {
		return statusBtnList.size();
	}

	@Tips(description = "随机点击")
	public void randomClick() {
		AndroidElement e = targetSerachResultList.get(RandomUtil.getRandomNum(getSearchResultCount() - 1));
		action.go2Click(e);
		forceWait(1);
	}

	public String getRandomTargetText() {
		return statusBtnList.get(targetElementIndex).getText();
	}

	@Tips(description = "用于缓存随机点击的按钮")
	public int targetElementIndex = 0;

	@Tips()
	public void randomGo2Download() {
		if (statusBtnList.size() > 0) {
			if (targetElementIndex == 0) {
				targetElementIndex = RandomUtil.getRandomNum(statusBtnList.size() - 1);
			}
			AndroidElement targetElement = statusBtnList.get(targetElementIndex);
			if (targetElement.getText().equals("打开")) {
				LogUtil.e("已经是打开的状态");
				return;
			}
			assertEquals(targetElement.getText(), "下载");
			// TODO 网速判断
			// 开始下载
			targetElement.click();
		} else {
			LogUtil.e("没有可下载的搜索结果");
			throw new RuntimeException("没有可下载的搜索结果");
		}
	}

	public void randomGo2Open() {
		if (downLoadOpenList.size() > 0) {
			AndroidElement tElement = downLoadOpenList.get(RandomUtil.getRandomNum(downLoadOpenList.size() - 1));
			tElement.click();
			forceWait(1);
		} else {
			LogUtil.e("没有可下载的搜索结果");
			throw new RuntimeException("没有可下载的搜索结果");
		}
	}

	public class SearchResultAction extends BaseAction {

		public SearchResultAction() {
			// TODO Auto-generated constructor stub
		}

		@Override
		@Tips(description = "该过度页不可直接到达")
		public void go2SelfPage() {
			// TODO Auto-generated method stub
		}

		public void go2SelfPageWithKey(String keyword) {
			if (!getCurrActivity().equals("SearchActivity")) {// 非SearchAct页面
				PageRedirect.redirect2SearchActivity();
			}
			SearchPage searchPageTemp = new SearchPage();
			searchPageTemp.go2SearchByKeyWord(keyword);
		}
	}
}
