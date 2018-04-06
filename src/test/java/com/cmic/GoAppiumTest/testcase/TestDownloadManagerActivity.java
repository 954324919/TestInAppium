package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
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
		WaitUtil.implicitlyWait(2);// 等待1S
		AndroidElement managerRly = mDriver.findElement(By.id("com.cmic.mmnes:id/managerview"));
		managerRly.click();
		WaitUtil.forceWait(2);
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test(enabled = false)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.ManagerCenterActivity");
		ScreenUtil.screenShot("进入必备应用管理中心界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkSlide2OtherTab() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(3);
		AndroidElement downloadTabTip = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"下载\")");
		AndroidElement updateTabTip = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"更新\")");
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		WaitUtil.forceWait(3);
		Assert.assertEquals(updateTabTip.isSelected(), false);
		Assert.assertEquals(downloadTabTip.isSelected(), true);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(3);
		Assert.assertEquals(updateTabTip.isSelected(), true);
		Assert.assertEquals(downloadTabTip.isSelected(), false);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkClick2OtherTab() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(3);
		AndroidElement downloadTabTip = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"下载\")");
		AndroidElement updateTabTip = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"更新\")");
		downloadTabTip.click();
		WaitUtil.forceWait(3);
		Assert.assertEquals(updateTabTip.isSelected(), false);
		Assert.assertEquals(downloadTabTip.isSelected(), true);
		updateTabTip.click();
		WaitUtil.forceWait(3);
		Assert.assertEquals(updateTabTip.isSelected(), true);
		Assert.assertEquals(downloadTabTip.isSelected(), false);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkRamdomEnterDetail() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkUpdateOne() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkUpdateAll() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkUpdate2Baseline() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkDownloadPauseAndResumeOne() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkDownloadPauseAndResumeAll() {

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkDeleteDownloadTask() {

	}

	@Tips(description = "取消下载但保存文件", riskPoint = "缺乏稳定性较高的检验方法,先保留")
	public void checkDeleteTaskAndKeepTheFile() {

	}
}
