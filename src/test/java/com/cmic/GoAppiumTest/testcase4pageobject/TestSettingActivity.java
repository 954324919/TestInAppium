package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SettingPage;
import com.cmic.GoAppiumTest.page.action.SettingAction;
import com.cmic.GoAppiumTest.page.middlepage.DownloadTipDialogPage;
import com.cmic.GoAppiumTest.page.middlepage.DownloadTipDialogPage.DownloadTipDialogAction;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.LogUtil;

@Listeners(ExtentReportListener.class)
public class TestSettingActivity extends BaseTest {

	// Page 部分
	private SettingPage settingPage;
	private DownloadTipDialogPage downloadTipDialogPage;

	// Action 部分
	private SettingAction settingAction;
	private DownloadTipDialogAction downloadTipDialogAction;

	@Override
	public void setUpBeforeClass() {
		settingPage = new SettingPage();
		settingAction = (SettingAction) settingPage.action;
		settingAction.go2SelfPage();
		downloadTipDialogPage = new DownloadTipDialogPage();
		downloadTipDialogAction = (DownloadTipDialogAction) downloadTipDialogPage.action;
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		LogUtil.w("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
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
		// 保存Page
		if (downloadTipDialogPage.isElementShown(downloadTipDialogPage.sbDownloadTip)) {
			downloadTipDialogAction.go2Backforward();
		} else {
			LogUtil.e("下载提示弹窗不显示");
			throw new RuntimeException("下载提示弹窗不显示");
		}
	}

	@Test(dependsOnMethods = { "downloadTipShow" })
	@Tips(description = "检查下载提示的影响")
	public void downloadTipCloseInOtherWay() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		boolean notifyLlyIsPresent;
		settingPage.go2ShowDownloadSettingTip();
		notifyLlyIsPresent = downloadTipDialogPage.isElementShown(downloadTipDialogPage.sbDownloadTip);
		assertEquals(notifyLlyIsPresent, true);
		downloadTipDialogAction.go2Click(downloadTipDialogPage.btnCloseDownloadTipDialog);
		notifyLlyIsPresent = downloadTipDialogPage.isElementShown(downloadTipDialogPage.sbDownloadTip);
		assertEquals(notifyLlyIsPresent, false);
		// 重新点击显示NotifyDialog
		settingPage.go2ShowDownloadSettingTip();
		downloadTipDialogAction.go2ClickAndWait(downloadTipDialogPage.btnCloseDownloadTipDialog, 1);
		notifyLlyIsPresent = downloadTipDialogPage.isElementShown(downloadTipDialogPage.sbDownloadTip);
		assertEquals(notifyLlyIsPresent, false);
		// 重新点击显示NotifyDialog
		settingPage.go2ShowDownloadSettingTip();
		downloadTipDialogAction.go2ClickAndWait(downloadTipDialogPage.btnCancelDownloadTipDialog, 1);
		notifyLlyIsPresent = downloadTipDialogPage.isElementShown(downloadTipDialogPage.sbDownloadTip);
		assertEquals(notifyLlyIsPresent, false);
	}

	@Test(dependsOnMethods = { "downloadTipCloseInOtherWay" })
	@Tips(description = "检查下载提示的影响")
	public void setRangeByEditText() {
		LogUtil.printCurrentMethodNameInLog4J();
		settingPage.go2ShowDownloadSettingTip();
		settingAction.go2SendWord(downloadTipDialogPage.etDownloadTip, "250");
		settingPage.snapScreen("输入250M时提示");
		// 点击确认
		downloadTipDialogAction.go2ClickAndWait(downloadTipDialogPage.btnDownloadTipDialogAccept, 1);
		String setDownloadRangerResult = settingAction.go2GetText(settingPage.tvDownloadTipNum).replaceAll("[^0-9]",
				"");
		assertEquals(setDownloadRangerResult, "250");
		// 回复原先的Seekbar状态
		settingPage.go2ShowDownloadSettingTip();
		downloadTipDialogAction.go2SendWord(downloadTipDialogPage.etDownloadTip, "");
		downloadTipDialogPage.snapScreen("输入为空时提示");
		// 点击确认
		downloadTipDialogAction.go2ClickAndWait(downloadTipDialogPage.btnDownloadTipDialogAccept, 1);
	}

	@Test(dependsOnMethods = { "setRangeByEditText" })
	public void setRangeBySeekbar() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		settingPage.go2ShowDownloadSettingTip();
		downloadTipDialogAction.go2Clear(downloadTipDialogPage.sbDownloadTip);
		// 点击Seekbar中间点
		downloadTipDialogPage.go2TapTheHalfSeekbar();
		settingPage.snapScreen("输入250M时提示");
		// 点击确认
		downloadTipDialogAction.go2ClickAndWait(downloadTipDialogPage.btnDownloadTipDialogAccept, 2);
		String setDownloadRangerResult = settingAction.go2GetText(settingPage.tvDownloadTipNum).replaceAll("[^0-9]",
				"");
		assertEquals(setDownloadRangerResult, "250");
	}
}
