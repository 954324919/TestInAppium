package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 搜索页面比较浅，暂不考虑太过复杂的耦合的问题
 * 
 * @author kiwi
 *
 */
public class TestSearchActivity {

	private String mTag;
	private AndroidDriver<AndroidElement> mDriver;

	private int originItemCount;
	private int currentItemCount;

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
	@Tips(description = "假设已经在MainAct", riskPoint = "耦合度暂不考虑，从MainTest完成进入")
	public void beforeClass() {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		WaitUtil.implicitlyWait(2);
		AndroidElement searchLayout = mDriver.findElement(By.id("com.cmic.mmnes:id/search_layout"));
		searchLayout.click();
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test
	public void initCheck() throws InterruptedException {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SearchActivity");
		ScreenUtil.screenShot("进入必备应用搜索界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击搜索ActionBar的后退")
	public void checkBack() throws InterruptedException {
		AndroidElement backIv = mDriver.findElement(By.id("com.cmic.mmnes:id/search_back_layout"));
		backIv.click();
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		// 获取瞬时滚动热词用于checkWordFromOutside测试
		String rollHotKeyWordUiSelector = "new UiSelector().className(\"android.widget.LinearLayout\").resourceId(\"com.cmic.mmnes:id/search_layout\")"
				+ ".childSelector(new UiSelector().className(\"android.widget.TextView\"))";
		System.out.println(rollHotKeyWordUiSelector);
		AndroidElement rollHotKeyWordTv = mDriver.findElementByAndroidUIAutomator(rollHotKeyWordUiSelector);
		System.out.println(rollHotKeyWordTv.getText());
		// 再次点击返回SearchActivity
		AndroidElement searchLayout = mDriver.findElement(By.id("com.cmic.mmnes:id/search_layout"));
		searchLayout.click();
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "checkBack" })
	@Tips(description = "被传入的热词")
	public void checkWordFromOutside() {//
		AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
		System.out.println(searchEt.getText());
	}

	@Test
	@Tips(description = "点击加载更多")
	public void checkGetMore() throws InterruptedException {
		originItemCount = mDriver.findElementsByClassName("android.widget.LinearLayout").size();
		AndroidElement getMoreIv = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_more"));
		getMoreIv.click();
		WaitUtil.forceWait(2);
		currentItemCount = mDriver.findElementsByClassName("android.widget.LinearLayout").size();
		assertEquals(originItemCount < currentItemCount, true);

	}

	@Test(dependsOnMethods = { "checkGetMore" })
	@Tips(description = "点击收起更多")
	public void closeTheWordList() throws InterruptedException {
		originItemCount = mDriver.findElementsByClassName("android.widget.LinearLayout").size();
		AndroidElement getMoreIv = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_more"));
		getMoreIv.click();
		WaitUtil.forceWait(2);
		currentItemCount = mDriver.findElementsByClassName("android.widget.LinearLayout").size();
		assertEquals(currentItemCount, originItemCount);
	}

	@Test
	@Tips(description = "热搜联想")
	public void checkSearchRalation() {

	}

	@Test
	@Tips(description = "点击随机的一个热词Item")
	public void randomCheckOne() {

	}

	@Test
	@Tips(description = "点击搜索栏目的clear图标")
	public void clickTheClearSearchRly() {

	}
}
