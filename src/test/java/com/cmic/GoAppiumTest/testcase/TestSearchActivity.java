package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import java.util.Random;

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
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
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
	private String rollHotKeyInMainAct;// 滚动热词
	private String searchBeforePerform;

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
		PageRedirect.redirect2MainActivity();
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

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "点击搜索ActionBar的后退")
	public void checkBack() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		AndroidElement backIv = mDriver.findElement(By.id("com.cmic.mmnes:id/search_back_layout"));
		backIv.click();
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		// 获取瞬时滚动热词用于checkWordFromOutside测试
		String rollHotKeyWordUiSelector = "new UiSelector().className(\"android.widget.LinearLayout\").resourceId(\"com.cmic.mmnes:id/search_layout\")"
				+ ".childSelector(new UiSelector().className(\"android.widget.TextView\"))";
		System.out.println(rollHotKeyWordUiSelector);
		AndroidElement rollHotKeyWordTv = mDriver.findElementByAndroidUIAutomator(rollHotKeyWordUiSelector);
		rollHotKeyInMainAct = rollHotKeyWordTv.getText();
		// 再次点击返回SearchActivity
		AndroidElement searchLayout = mDriver.findElement(By.id("com.cmic.mmnes:id/search_layout"));
		searchLayout.click();
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "checkBack" }, enabled = false)
	@Tips(description = "被传入的热词")
	public void checkWordFromOutside() {//
		LogUtil.printCurrentMethodName();
		AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
		// TODO 必要时截图
		assertEquals(searchEt.getText(), rollHotKeyInMainAct);
	}

	@Test(dependsOnMethods = { "checkWordFromOutside" }, enabled = false)
	@Tips(description = "点击加载更多")
	public void checkGetMore() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		originItemCount = mDriver.findElementsByClassName("android.widget.LinearLayout").size();
		AndroidElement getMoreIv = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_more"));
		getMoreIv.click();
		WaitUtil.forceWait(2);
		currentItemCount = mDriver.findElementsByClassName("android.widget.LinearLayout").size();
		assertEquals(originItemCount < currentItemCount, true);

	}

	@Test(dependsOnMethods = { "checkGetMore" }, enabled = false)
	@Tips(description = "点击收起更多")
	public void closeTheWordList() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		AndroidElement getMoreIv = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_more"));
		getMoreIv.click();
		WaitUtil.forceWait(2);
		currentItemCount = mDriver.findElementsByClassName("android.widget.LinearLayout").size();
		assertEquals(currentItemCount, originItemCount);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击随机的一个热词Item", riskPoint = "耦合度过高，与下列clickTheClearSearchRly风险点太高")
	public void randomCheckOne() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		Random random = new Random();
		//TODO 模拟一个数字
		int randomIndex = random.nextInt(mDriver.findElementsByClassName("android.widget.LinearLayout").size());
		String hotKeyItemXPath = "//android.widget.ScrollView/android.widget.LinearLayout[" + randomIndex + "]";
		AndroidElement hotkeyItem = mDriver.findElement(By.xpath(hotKeyItemXPath));
		String hotKeyInnerTvXpath = "//android.widget.FrameLayout[1]/android.widget.ScrollView[1]/android.widget.LinearLayout["
				+ randomIndex + "]/android.widget.TextView[1]";
		System.out.println(hotKeyInnerTvXpath);
		AndroidElement hotKeyInnerTv = mDriver.findElement(By.xpath(hotKeyInnerTvXpath));
		System.out.println(searchBeforePerform = hotKeyInnerTv.getText());
		// TODO 必要时截图
		hotkeyItem.click();
		WaitUtil.forceWait(2);
		// TODO 风险不一定退出
		PageRouteUtil.pressBack();
	}

	@Test(dependsOnMethods = { "randomCheckOne" })
	@Tips(description = "点击搜索栏目的clear图标||同意默认情况写下为不显示，受randomCheckOne影响可见")
	public void clickTheClearSearchRly() {
		LogUtil.printCurrentMethodName();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "热搜联想")
	public void checkSearchRalation() {
		LogUtil.printCurrentMethodName();
	}
}
