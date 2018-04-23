package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;

/**
 * 搜索页面比较浅，暂不考虑太过复杂的耦合的问题
 * 
 * @author kiwi
 *
 */
@Listeners(ExtentReportListener.class)
public class TestSearchActivity extends BaseTest {

	private int originItemCount;
	private int currentItemCount;
	private String rollHotKeyInMainAct;// 滚动热词
	private String searchBeforePerform;

	@Tips(description = "假设已经在MainAct", riskPoint = "耦合度暂不考虑，从MainTest完成进入")
	@Override
	public void setUpBeforeClass() {
		try {
			PageRedirect.redirect2SearchActivity();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WaitUtil.implicitlyWait(2);
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() throws InterruptedException {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		WaitUtil.forceWait(2);
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SearchActivity");
		ScreenUtil.screenShot("进入必备应用搜索界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
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

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击收起更多")
	public void closeTheWordList() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement getMoreIv = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_more"));
		getMoreIv.click();
		WaitUtil.forceWait(2);
		currentItemCount = mDriver.findElementsByClassName("android.widget.LinearLayout").size();
		assertEquals(currentItemCount, originItemCount);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击随机的一个热词Item", //
			riskPoint = "耦合度过高，与下列clickTheClearSearchRly风险点太高")
	public void randomCheckOne() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		Random random = new Random();
		// TODO 模拟一个数字
		List<AndroidElement> list = mDriver.findElementsByClassName("android.widget.LinearLayout");
		int randomIndex = 0;
		if (list.size() > 3) {
			randomIndex = random.nextInt(list.size() - 3);
		} else {
			System.err.println("页面显示不全");
			ScreenUtil.screenShotForce("randomCheckOne页面显示不全");
			return;
		}
		AndroidElement hotkeyItem = list.get(randomIndex);
		searchBeforePerform = hotkeyItem.getText();
		if (searchBeforePerform.equals("软件") || searchBeforePerform.equals("游戏") || searchBeforePerform.equals("热门")) {
			searchBeforePerform = list.get(randomIndex + 1).getText();
		}
		// TODO 必要时截图
		hotkeyItem.click();
		WaitUtil.forceWait(2);
		// TODO 风险不一定退出
		PageRouteUtil.pressBack();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击搜索栏目的clear图标||同意默认情况写下为不显示，受randomCheckOne影响可见")
	public void clickTheClearSearchRly() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/search_clear_iv"))) {
			AndroidElement clearIv = mDriver.findElement(By.id("com.cmic.mmnes:id/search_clear_iv"));
			clearIv.click();
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击搜索ActionBar的后退")
	public void checkBack() throws InterruptedException {
		if (!ContextUtil.getCurrentActivity().equals(".activity.SearchActivity")) {
			System.err.println("checkBack当前不在目标页面无法测试");
			return;
		}
		WaitUtil.implicitlyWait(5);
		AndroidElement backIv = mDriver.findElement(By.id("com.cmic.mmnes:id/search_back_layout"));
		LogUtil.printCurrentMethodName();
		backIv.click();
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		// 获取瞬时滚动热词用于checkWordFromOutside测试
		String rollHotKeyWordUiSelector = "new UiSelector().className(\"android.widget.LinearLayout\").resourceId(\"com.cmic.mmnes:id/search_layout\")"
				+ ".childSelector(new UiSelector().className(\"android.widget.TextView\"))";
		AndroidElement rollHotKeyWordTv = mDriver.findElementByAndroidUIAutomator(rollHotKeyWordUiSelector);
		rollHotKeyInMainAct = rollHotKeyWordTv.getText();
		if (rollHotKeyInMainAct == null) {
			ScreenUtil.screenShotForce("checkBack获取不到滚动热词");
			WaitUtil.forceWait(1);
			rollHotKeyInMainAct = rollHotKeyWordTv.getText();
		}
		System.err.println("外界热词为：" + rollHotKeyInMainAct);
		// 再次点击返回SearchActivity
		WaitUtil.implicitlyWait(3);
		AndroidElement searchLayout = mDriver.findElement(By.id("com.cmic.mmnes:id/search_layout"));
		searchLayout.click();
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "checkBack" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "被传入的热词")
	public void checkWordFromOutside() {//
		if (!ContextUtil.getCurrentActivity().equals(".activity.SearchActivity")) {
			System.err.println("checkWordFromOutside页面异常");
			ScreenUtil.screenShotForce("checkWordFromOutside异常截图+i++");
		}
		LogUtil.printCurrentMethodName();
		try {
			WaitUtil.implicitlyWait(10);
			AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
			// TODO 必要时截图
			System.err.println("滚动热词为：" + searchEt.getText());
			assertEquals(searchEt.getText(), rollHotKeyInMainAct);
		} catch (Exception e) {
			ScreenUtil.screenShotForce("checkWordFromOutside失败时截图");
		}
	}
}
