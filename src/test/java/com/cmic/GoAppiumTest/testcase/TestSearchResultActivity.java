package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 当前0405从SearchActivity中进入
 * 
 * @author kiwi
 */
public class TestSearchResultActivity {
	private String mTag;
	private AndroidDriver<AndroidElement> mDriver;

	@BeforeMethod
	public void tipBeforeTestCase() {
		// 点击同意并使用
		System.out.println("测试用例[" + (++App.CASE_COUNT) + "]开始");
	}

	@AfterMethod
	public void tipAfterTestCase() {
		System.out.println("测试用例[" + (App.CASE_COUNT) + "]结束");
	}

	@BeforeClass
	@Tips(description = "假设已经入Setting&&未跳转到其他页面")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		PageRedirect.redirect2SearchActivity();
		AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
		searchEt.click();
		searchEt.clear();
		searchEt.sendKeys("和飞信");
		WaitUtil.implicitlyWait(2);
		mDriver.findElement(By.id("com.cmic.mmnes:id/search_icon_layout")).click();
		WaitUtil.forceWait(2);
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SearchActivity");
		boolean isPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/search_count_tv"));
		assertEquals(isPresent, true);
		ScreenUtil.screenShot("进入必备搜索结果界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "滑动切换页面", riskPoint = "UI变动")
	public void checkSild2OtherTab() throws InterruptedException {
		AndroidElement e = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"全部\")");
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

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "测试点击切换", riskPoint = "页面变动|网络变动")
	public void checkClick2OtherTab() throws InterruptedException {
		AndroidElement eAll = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"全部\")");
		AndroidElement eSoftware = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"软件\")");
		AndroidElement eGame = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"游戏\")");
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

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkRandomClick2Detail() throws InterruptedException {
		List<AndroidElement> eListItem = mDriver.findElements(By.id("com.cmic.mmnes:id/item_layout"));
		eListItem.get(RandomUtil.getRandomNum(eListItem.size() - 1));
		WaitUtil.forceWait(3);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.DetailActivity");
		PageRouteUtil.pressBack();
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkRandomClick2Download() throws InterruptedException {
		String statusBtnUiSelector = "new UiSelector().className(\"android.widget.TextView\").textContains(\"下载\").resourceId(\"com.cmic.mmnes:id/status_btn\")";
		List<AndroidElement> eListStatusBtn = mDriver.findElementsByAndroidUIAutomator(statusBtnUiSelector);
		assertEquals(eListStatusBtn.size() > 0, true);
		//
		AndroidElement targetElement = eListStatusBtn.get(RandomUtil.getRandomNum(eListStatusBtn.size() - 1));
		assertEquals(targetElement.getText(), "下载");
		targetElement.click();
		// TODO 网速判断
		// 开始下载
		mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
		WaitUtil.forceWait(2);
		assertEquals(targetElement.getText(), "暂停");
		// 暂停下载
		targetElement.click();
		WaitUtil.forceWait(1);
		assertEquals(targetElement.getText(), "继续");
		// TODO 不稳定待日后完善
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkDownloadButtonOpenStatus() {
		String statusBtnUiSelector = "new UiSelector().className(\"android.widget.TextView\").textContains(\"打开\").resourceId(\"com.cmic.mmnes:id/status_btn\")";
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

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkSearch2Baseline() {
		WaitUtil.implicitlyWait(5);
		AndroidElement eItem = mDriver.findElement(By.id("com.cmic.mmnes:id/item_layout"));
		ScrollUtil.scrollToBase();
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkErrorInput4EmptyPage() throws InterruptedException {
		String randomString = RandomUtil.getRandomString(10);
		AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
		searchEt.click();
		searchEt.clear();
		searchEt.sendKeys(randomString);
		WaitUtil.implicitlyWait(2);
		mDriver.findElement(By.id("com.cmic.mmnes:id/search_icon_layout")).click();
		WaitUtil.forceWait(2);

		// 查找
		boolean isPresent = ElementUtil.isElementExistByXpath(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"建议你 \")");
		assertEquals(isPresent, true);
	}
}
