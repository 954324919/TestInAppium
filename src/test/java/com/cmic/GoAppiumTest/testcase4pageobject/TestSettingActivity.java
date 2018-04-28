package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SettingPage;
import com.cmic.GoAppiumTest.page.action.SettingAction;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;

@Listeners(ExtentReportListener.class)
public class TestSettingActivity extends BaseTest {

	private SettingPage settingPage;
	private SettingAction settingAction;

	@Override
	public void setUpBeforeClass() {
		settingPage = new SettingPage();
		settingAction = (SettingAction) settingPage.action;
		settingAction.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		LogUtil.e("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
		assertEquals(getCurrentPageName(), "SettingActivity");
		settingPage.snapScreen("进入必备应用设置中心界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查自更新设置的影响")
	public void checkAutoUpdate() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		settingAction.go2ClickAndWait(settingPage.llyAutoUpdateSetting, 2);
		settingPage.snapScreen("前后对比");
		settingAction.go2ClickAndWait(settingPage.llyAutoUpdateSetting, 2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查下载提示的影响")
	public void downloadTipShow() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		// TODO 检查影响，0404暂不实现
		settingPage.go2ShowDownloadSettingTip();
		if (settingPage.sbDownloadTip != null && settingPage.sbDownloadTip.isDisplayed()) {
			settingAction.go2Backforward();
		} else {
			LogUtil.e("下载提示弹窗不显示");
			throw new RuntimeException("下载提示弹窗不显示");
		}
	}

	public void showDialog() {
		WaitUtil.implicitlyWait(2);
		AndroidElement notifyLly = mDriver.findElement(By.id("com.cmic.mmnes:id/setting_download_notice_layout"));
		notifyLly.click();
		WaitUtil.forceWait(1);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查下载提示的影响")
	public void downloadTipCloseInOtherWay() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		boolean notifyLlyIsPresent;
		WaitUtil.implicitlyWait(2);
		settingPage.go2ShowDownloadSettingTip();
		notifyLlyIsPresent = settingPage != null && settingPage.sbDownloadTip.isDisplayed();
		assertEquals(notifyLlyIsPresent, true);
		settingAction.go2Click(settingPage.btnCloseDownloadTipDialog);
		notifyLlyIsPresent = settingPage != null && settingPage.sbDownloadTip.isDisplayed();
		assertEquals(notifyLlyIsPresent, false);
		// 重新点击显示NotifyDialog
		settingAction.go2ClickAndWait(settingPage.btnCloseDownloadTipDialog, 1);
		notifyLlyIsPresent = settingPage != null && settingPage.sbDownloadTip.isDisplayed();
		assertEquals(notifyLlyIsPresent, false);
		settingAction.go2ClickAndWait(settingPage.btnCancelDownloadTipDialog, 1);
		notifyLlyIsPresent = settingPage != null && settingPage.sbDownloadTip.isDisplayed();
		assertEquals(notifyLlyIsPresent, false);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查下载提示的影响")
	public void setRangeByEditText() {
		LogUtil.printCurrentMethodNameInLog4J();
		settingPage.go2ShowDownloadSettingTip();

		settingAction.go2SendWord(settingPage.etDownloadTip, "250");
		settingPage.snapScreen("输入250M时提示");
		// 点击确认
		settingAction.go2ClickAndWait(settingPage.btnDownloadTipDialogAccept, 2);
		String setDownloadRangerResult = settingAction.go2GetText(settingPage.tvDownloadTipNum).replaceAll("[^0-9]",
				"");
		assertEquals(setDownloadRangerResult, "250");
		// 回复原先的Seekbar状态
		settingAction.go2SendWord(settingPage.etDownloadTip, "");
		settingPage.snapScreen("输入为空时提示");
		// 点击确认
		settingAction.go2ClickAndWait(settingPage.btnDownloadTipDialogAccept, 2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void setRangeBySeekbar() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		settingPage.go2ShowDownloadSettingTip();
		WaitUtil.implicitlyWait(2);
		settingAction.go2Clear(settingPage.sbDownloadTip);

		// 点击Seekbar中间点
		settingPage.go2TapTheHalfSeekbar();
		settingPage.snapScreen("输入250M时提示");
		// 点击确认
		settingAction.go2ClickAndWait(settingPage.btnDownloadTipDialogAccept, 2);
		String setDownloadRangerResult = settingAction.go2GetText(settingPage.tvDownloadTipNum).replaceAll("[^0-9]",
				"");
		assertEquals(setDownloadRangerResult, "250");
	}
}
