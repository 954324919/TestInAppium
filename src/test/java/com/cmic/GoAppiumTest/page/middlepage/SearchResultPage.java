package com.cmic.GoAppiumTest.page.middlepage;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.RandomUtil;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

public class SearchResultPage extends BasePage {

	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
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

	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")")
	public List<AndroidElement> statusBtnList;// 具体到某个Tab中的下载按钮结果

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\").textContains(\"打开\")")
	public List<AndroidElement> downLoadOpenList;

	@AndroidFindBy(id = "com.cmic.mmnes:id/app_name")
	public List<AndroidElement> searchResultList;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"建议你 \")")
	public AndroidElement errorTip;

	@AndroidFindBy(xpath = "//android.support.v7.widget.RecyclerView[@resource-id='com.cmic.mmnes:id/search_rv']/android.widget.RelativeLayout[1]")
	public AndroidElement firstItem;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.support.v7.widget.RecyclerView\").resourceId(\"com.cmic.mmnes:id/search_rv\").childSelector(new UiSelector().className(\"android.widget.RelativeLayout\")).instance(0).childSelector(new UiSelector().resourceId(\"com.cmic.mmnes:id/slogan2\"))")
	public AndroidElement tvAppFirstSize;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.support.v7.widget.RecyclerView\").resourceId(\"com.cmic.mmnes:id/search_rv\").childSelector(new UiSelector().className(\"android.widget.RelativeLayout\")).instance(0).childSelector(new UiSelector().resourceId(\"com.cmic.mmnes:id/status_btn\"))")
	public AndroidElement btnAppFirstDownload;

	public SearchResultPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new SearchResultAction();
	}

	public double getAppSize() {
		String appSizeText = action.go2GetText(tvAppFirstSize);
		return Double.parseDouble(appSizeText.replaceAll("M", ""));
	}

	public boolean getErrorTipVisiable() {
		return errorTip.isDisplayed();
	}

	public boolean getPageLoadFinishOrNot() {
		return isElementIsPresent(searchResultCount);
	}

	public void click2GameTab() {
		action.go2Click(searchResultTabGame);
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

	public String go2GetDownloadSpeedRate() {
		return null;
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

	@Tips(description = "随机点击开始下载")
	public AndroidElement randomGo2DownloadStart() {
		if (statusBtnList.size() > 0) {
			targetElementIndex = Math.min(RandomUtil.getRandomNum(statusBtnList.size() - 1), 5);// 防止点击到超出显示页面Item
			LogUtil.w("选中开始下载位置为{}", targetElementIndex);
			AndroidElement targetElement = statusBtnList.get(targetElementIndex);
			if (targetElement.getText().equals("打开")) {
				LogUtil.e("已经是打开的状态");
				return null;
			}
			assertEquals(targetElement.getText(), "下载");
			// 开始下载
			targetElement.click();
			return targetElement;
		} else {
			LogUtil.e("没有可下载的搜索结果");
			throw new RuntimeException("没有可下载的搜索结果");
		}
	}

	@Tips(description = "将随机开始的下载暂停", riskPoint = "在移动网络会进入提示页面")
	public void randomGo2DownloadPause() {
		AndroidElement targetElement = statusBtnList.get(targetElementIndex);
		if (targetElement.getText().equals("暂停")) {
			targetElement.click();
		} else if (targetElement.getText().equals("打开")) {
			LogUtil.e("已经是打开的状态");
			return;
		} else {
			LogUtil.e("当前按钮状态为{}", targetElement.getText());
			return;
		}
	}

	@Tips(description = "不依赖PageFactory的点击方法", riskPoint = "在移动网络会进入提示页面")
	public void randomGo2DownloadPause1() throws NoSuchElementException {
		// TODO
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

		@Tips(description = "通过搜索Keyword进入SearchResult页面")
		public void go2SelfPageWithKey(String keyword) {
			if (ContextUtil.getCurrentPageActivtiy().equals("SearchActivity")) {
			} else if (ContextUtil.getCurrentPageActivtiy().equals("MainActivity")) {// 非SearchAct页面
				PageRedirect.incFromMain2Search();
			} else {// 非SearchAct页面
				PageRedirect.redirect2SearchActivity();
			}
			SearchPage searchPageTemp = new SearchPage();
			searchPageTemp.go2SearchByKeyWord(keyword);
		}
	}

	public List<String> getSearchResultList() {
		List<String> textList = new ArrayList<String>();
		for (AndroidElement e : searchResultList) {
			textList.add(e.getText());
		}
		return textList;
	}
}
