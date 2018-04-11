package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
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
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 测试下载管理页面
 * 
 * @author kiwi
 * @风险 不同下载的页面可能启动页的不同
 * @TODO 添加不同的其他的测试，如锁屏选中，增加对下载任务的压力，检查稳定性
 */
public class TestDownloadManagerActivity {
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
	@Tips(description = "假设已经入MainAct&&未跳转到其他页面")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		// PageRedirect.redirect2DownloadManagerActivity();
		PageRedirect.redirect2MainActivity();
		WaitUtil.implicitlyWait(2);// 等待1S
		AndroidElement managerRly = mDriver.findElement(By.id("com.cmic.mmnes:id/managerview"));
		managerRly.click();
		WaitUtil.forceWait(2);
		System.err.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.err.println("测试用例集[" + mTag + "]结束");
		AppUtil.unInstall(App.PACKAGE_NAME);
	}

	@Test
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.ManagerCenterActivity");
		ScreenUtil.screenShot("进入必备应用管理中心界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkSlide2OtherTab() throws InterruptedException {
		// TODO 加入页面空的判断
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement downloadTabTip = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"下载\")");
		AndroidElement updateTabTip = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"更新\")");
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		WaitUtil.forceWait(2);
		Assert.assertEquals(updateTabTip.isSelected(), false);
		Assert.assertEquals(downloadTabTip.isSelected(), true);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(2);
		Assert.assertEquals(updateTabTip.isSelected(), true);
		Assert.assertEquals(downloadTabTip.isSelected(), false);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkClick2OtherTab() throws InterruptedException {
		// TODO 加入页面空的判断
		WaitUtil.implicitlyWait(3);
		AndroidElement downloadTabTip = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"下载\")");
		LogUtil.printCurrentMethodName();
		AndroidElement updateTabTip = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"更新\")");
		downloadTabTip.click();
		WaitUtil.forceWait(2);
		Assert.assertEquals(updateTabTip.isSelected(), false);
		Assert.assertEquals(downloadTabTip.isSelected(), true);
		updateTabTip.click();
		WaitUtil.forceWait(2);
		Assert.assertEquals(updateTabTip.isSelected(), true);
		Assert.assertEquals(downloadTabTip.isSelected(), false);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击更新的Item", riskPoint = "页面不显示")
	public void checkRamdomEnterDetail() throws InterruptedException {
		switchUpdate();
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(3);
		List<AndroidElement> eList = mDriver.findElements(By.id("com.cmic.mmnes:id/app_name"));
		if (eList.size() > 0) {
			int minItemSize = Math.min(eList.size(), 5);
			int randomIndex = RandomUtil.getRandomNum(minItemSize);
			eList.get(randomIndex).click();
			WaitUtil.forceWait(2);
			// TODO 不稳定待解决
			assertEquals(ContextUtil.getCurrentActivity(), ".activity.DetailActivity");
			WaitUtil.implicitlyWait(3);
			mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
			WaitUtil.forceWait(2);
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkUpdate2Baseline() {
		LogUtil.printCurrentMethodName();
		ScrollUtil.scrollToBase();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "更新一个Itme", riskPoint = "下载速度太快，进入安装页面")
	public void checkUpdateOne() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		String updateBtnUiSelector = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")";
		List<AndroidElement> eList = mDriver.findElementsByAndroidUIAutomator(updateBtnUiSelector);
		if (eList.size() > 0) {
			int minItemSize = Math.min(eList.size(), 5);
			AndroidElement targetElement = eList.get(RandomUtil.getRandomNum(minItemSize));
			assertEquals(targetElement.getText(), "更新");
			targetElement.click();
			// TODO 网速判断
			// 开始下载
			if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/mm_down_goon"))) {
				mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
				WaitUtil.forceWait(2);
				assertEquals(targetElement.getText(), "暂停");
			}
			// 暂停下载
			WaitUtil.forceWait(1);// 下载速度过快导致NoSuchEx
			targetElement.click();
			WaitUtil.forceWait(1);
			// TODO 提高对安装界面的兼容性需要修复
			if (targetElement.getText().equals("安装")) {
				System.err.println("已经进入安装");
				PageRouteUtil.pressBack();// 回退
			}
			assertEquals(targetElement.getText(), "继续");
			// TODO 不稳定待日后完善
		}
	}

	// TODO 过于耗费流量暂时关闭
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击全部下载|马上切换到下载页关闭")
	public void checkUpdateAll() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/updateall_tx"));
		e.click();
		// 进入取消
		WaitUtil.implicitlyWait(3);
		AndroidElement dialogCancel = mDriver.findElement(By.id("com.cmic.mmnes:id/mm_dialog_cancel"));
		// 先点击取消检验效果
		dialogCancel.click();
		// 进入确认
		WaitUtil.implicitlyWait(3);
		e = mDriver.findElement(By.id("com.cmic.mmnes:id/updateall_tx"));
		e.click();
		WaitUtil.implicitlyWait(3);
		AndroidElement dialogOk = mDriver.findElement(By.id("com.cmic.mmnes:id/mm_dialog_ok"));
		// 点击确认下载
		dialogOk.click();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "切换到下载页面", riskPoint = "存在不稳定的因素")
	public void checkDownloadPauseAndResumeAll() throws InterruptedException {
		// 切换到下载页面
		LogUtil.printCurrentMethodName();
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/function_tv"));
		e.click();
		WaitUtil.forceWait(1);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击继续和暂停", riskPoint = "可能进入安装状态")
	public void checkDownloadPauseAndResumeOne() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		List<AndroidElement> eList = mDriver.findElements(By.id("com.cmic.mmnes:id/status_btn"));
		if (eList.size() > 0) {
			int minItemSize = Math.min(eList.size(), 5);
			int randomIndex = RandomUtil.getRandomNum(minItemSize);
			AndroidElement targetElement = eList.get(randomIndex);
			targetElement.click();// 开始下载
			// TODO 网速判断
			// 开始下载
			WaitUtil.implicitlyWait(5);
			if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/mm_down_goon"))) {
				mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
				WaitUtil.forceWait(1);
				assertEquals(targetElement.getText(), "暂停");
			}
			targetElement.click(); // 暂停下载
			WaitUtil.implicitlyWait(2);
			eList.clear();
			eList = mDriver.findElements(By.id("com.cmic.mmnes:id/status_btn"));
			targetElement = eList.get(randomIndex);
			assertEquals(targetElement.getText(), "继续");
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkDeleteDownloadTask() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		List<AndroidElement> eList = mDriver.findElements(By.id("com.cmic.mmnes:id/delete_btn"));
		if (eList.size() > 0) {
			int minItemSize = Math.min(eList.size(), 5);
			WaitUtil.implicitlyWait(3);
			int randomIndex = RandomUtil.getRandomNum(minItemSize);
			String temp = mDriver.findElements(By.id("com.cmic.mmnes:id/app_name")).get(randomIndex).getText();
			AndroidElement targetElement = eList.get(randomIndex);
			// 点击删除
			targetElement.click();
			// 点击确认
			WaitUtil.implicitlyWait(3);
			mDriver.findElement(By.id("com.cmic.mmnes:id/mm_dialog_ok")).click();
			WaitUtil.implicitlyWait(3);
			String deleteUiSelect = "new UiSelector().className(\"android.widget.TextView\").textContains(\"" + temp
					+ "\").resourceId(\"com.cmic.mmnes:id/app_name\")";
			assertEquals(ElementUtil.isElementPresent(deleteUiSelect), false);
		}
	}

	// @Test(dependsOnMethods = { "checkDownloadPauseAndResumeAll" })
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "取消下载但保存文件", riskPoint = "缺乏稳定性较高的检验方法,先保留")
	public void checkDeleteTaskAndKeepTheFile() {
		// TODO 业务逻辑不清楚，不写用例
		LogUtil.printCurrentMethodName();
	}

	@Tips(description = "普通方法")
	private void switchUpdate() throws InterruptedException {
		AndroidElement eCurrentTab = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"更新\")");
		if (!eCurrentTab.isSelected()) {
			eCurrentTab.click();
			WaitUtil.forceWait(2);
		}
	}
}
