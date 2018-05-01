package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.RandomUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.AndroidElement;

/**
 * 当前0405从SearchActivity中进入
 * 
 * @author kiwi
 */
@Listeners(ExtentReportListener.class)
public class TestSearchResultActivity extends BaseTest {

	private SearchResultPage mSearchResultPage;

	@Tips(description = "假设已经入SearchActivity的热词界面")
	@Override
	public void setUpBeforeClass() {
		mSearchResultPage = new SearchResultPage();
		mSearchResultPage.action.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// 先确认是否进入该页面
		LogUtil.e("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(getCurrentPageName(), "SearchActivity");
		boolean isPresent = ElementUtil.isElementPresentSafe(By.id("com.cmic.mmnes:id/search_count_tv"));
		assertEquals(isPresent, true);
		ScreenUtil.screenShot("进入必备搜索结果界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "滑动切换页面", riskPoint = "UI变动")
	public void checkSild2OtherTab() throws InterruptedException {
		AndroidElement e = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"全部\")");
		LogUtil.printCurrentMethodName();
		assertEquals(e.isSelected(), true);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(3);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(3);
		assertEquals(e.isSelected(), false);
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		WaitUtil.forceWait(3);
		assertEquals(e.isSelected(), false);
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		WaitUtil.forceWait(3);
		AndroidElement e1 = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"全部\")");
		assertEquals(e1.isSelected(), true);
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "测试点击切换", riskPoint = "页面变动|网络变动")
	public void checkClick2OtherTab() throws InterruptedException {
		AndroidElement eAll = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"全部\")");
		AndroidElement eSoftware = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"软件\")");
		AndroidElement eGame = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"游戏\")");
		LogUtil.printCurrentMethodName();
		assertEquals(eAll.isSelected(), true);
		eSoftware.click();// 点击切换到SoftWartTab
		WaitUtil.implicitlyWait(5);
		List<AndroidElement> eListSoft = mDriver.findElements(By.id("com.cmic.mmnes:id/item_layout"));
		assertEquals(eListSoft.size() > 0, true);
		assertEquals(eAll.isSelected(), false);

		eGame.click();
		WaitUtil.implicitlyWait(5);
		List<AndroidElement> eListGame = mDriver.findElements(By.id("com.cmic.mmnes:id/item_layout"));
		assertEquals(eListGame.size() > 0, true);
		assertEquals(eAll.isSelected(), false);

		eAll.click();
		WaitUtil.implicitlyWait(5);
		List<AndroidElement> eListAll = mDriver.findElements(By.id("com.cmic.mmnes:id/item_layout"));
		assertEquals(eListAll.size() > 0, true);
		assertEquals(eAll.isSelected(), true);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkRandomClick2Detail() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		List<AndroidElement> eListItem = mDriver.findElements(By.id("com.cmic.mmnes:id/item_layout"));
		LogUtil.printCurrentMethodName();
		if (eListItem.isEmpty()) {
			LogUtil.e("列表为空");
			return;
		}
		eListItem.get(RandomUtil.getRandomNum(eListItem.size() - 1)).click();
		WaitUtil.forceWait(2);
		assertEquals(getCurrentPageName(), ".activity.DetailActivity");
		PageRouteUtil.pressBack();
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查随机下载", riskPoint = "下载状态实际难以预测，当前findView不够稳定")
	public void checkRandomClick2Download() throws InterruptedException {
		// String statusBtnUiSelector = "new
		// UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\").textContains(\"下载\")";
		String statusBtnUiSelector = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")";
		WaitUtil.implicitlyWait(5);
		List<AndroidElement> eListStatusBtn = mDriver.findElementsByAndroidUIAutomator(statusBtnUiSelector);
		LogUtil.printCurrentMethodName();
		assertEquals(eListStatusBtn.size() > 0, true);
		int randomInder = RandomUtil.getRandomNum(eListStatusBtn.size() - 1);
		AndroidElement targetElement = eListStatusBtn.get(randomInder);
		// TODO 暂时取巧不够稳定
		if (targetElement.getText().equals("打开")) {
			LogUtil.e("已经是打开的状态");
			return;
		}
		assertEquals(targetElement.getText(), "下载");
		// TODO 网速判断
		// 开始下载

		targetElement.click();
		// TODO 新增
		WaitUtil.forceWait(1);
		targetElement.click();// TODO 新增，马上点击停止
		WaitUtil.forceWait(0.5);
		if (!targetElement.getText().contains("继续")) {
			if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/mm_down_goon"))) {
				mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
				WaitUtil.forceWait(1);
				targetElement.click();
				WaitUtil.forceWait(0.5);
			}
		}
		// 如果实在移动网络的情况下
		// 开始下载
		assertEquals(targetElement.getText().contains("继续"), true);
	}

	// TODO 及其不稳定，暂不考虑放开
	@Test(dependsOnMethods = { "initCheck" })
	public void checkDownloadButtonOpenStatus() {
		String statusBtnUiSelector = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\").textContains(\"打开\")";
		LogUtil.printCurrentMethodName();
		List<AndroidElement> eListStatusBtn = mDriver.findElementsByAndroidUIAutomator(statusBtnUiSelector);
		int statusOpenBtnNum = eListStatusBtn.size();
		if (statusOpenBtnNum > 0) {
			eListStatusBtn.get(RandomUtil.getRandomNum(statusOpenBtnNum) - 1).click();
			String newPackageName = ContextUtil.getPackageName();
			assertEquals(newPackageName != App.PACKAGE_NAME, true);
			// TODO 不稳定
			AppUtil.softResetApp();
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkSearch2Baseline() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		mDriver.findElement(By.id("com.cmic.mmnes:id/item_layout"));
		ScrollUtil.scrollToBase();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查没有搜索内容时的情况", riskPoint = "搜索关键词可能存在搜索结果")
	public void checkErrorInput4EmptyPage() throws InterruptedException {
		// String randomString = RandomUtil.getRandomString(10);
		String randomString = "assssss";
		WaitUtil.implicitlyWait(5);
		AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
		LogUtil.printCurrentMethodName();
		searchEt.click();
		searchEt.clear();
		searchEt.sendKeys(randomString);
		WaitUtil.implicitlyWait(2);
		mDriver.findElement(By.id("com.cmic.mmnes:id/search_icon_layout")).click();
		WaitUtil.forceWait(2);
		// 查找
		WaitUtil.implicitlyWait(2);
		AndroidElement eGame = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"游戏\")");
		eGame.click();
		boolean isPresent = ElementUtil
				.isElementPresent("new UiSelector().className(\"android.widget.TextView\").textContains(\"建议你 \")");
		assertEquals(isPresent, true);
	}
}
