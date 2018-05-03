package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.DownloadManagerDownloadPage;
import com.cmic.GoAppiumTest.page.action.DownloadManagerDownloadAction;
import com.cmic.GoAppiumTest.page.action.DownloadManagerUpdateAction;
import com.cmic.GoAppiumTest.page.middlepage.DownloadDialogPage;
import com.cmic.GoAppiumTest.page.middlepage.DownloadDialogPage.DownloadDialogAction;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.Connection;

@Tips(description = "用于测试下载管理中心的下载Tab")
@Listeners(ExtentReportListener.class)
public class TestDownloadManagerActivityDTab extends BaseTest {

	private DownloadManagerDownloadPage downloadTabPage;
	private DownloadManagerDownloadAction downloadTabAction;

	@Override
	public void setUpBeforeClass() {
		downloadTabPage = new DownloadManagerDownloadPage();
		downloadTabAction = (DownloadManagerDownloadAction) downloadTabPage.action;
		downloadTabAction.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Test
	public void initCheck() {// TODO 验证比较弱
		assertEquals(getCurrentPageName(), "ManagerCenterActivity");
		downloadTabPage.snapScreen("进入必备应用管理中心界面的下载Tab");
		if (downloadTabPage.targetPageItemList.size() <= 0) {
			throw new RuntimeException("没有正在下载的Item");
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "切换到下载页面", riskPoint = "存在不稳定的因素")
	public void checkDownloadPauseAndResumeAll() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		downloadTabAction.go2ClickAndWait(downloadTabPage.btnAllOperate, 1);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击继续和暂停", riskPoint = "可能进入安装状态")
	public void checkDownloadPauseAndResumeOne() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		// 开始检查网络
		Connection temp = downloadTabAction.go2GetNetWorkStatus();
		LogUtil.w("当前网络状态为{}", temp.name());
		boolean isDataStatu = (temp == Connection.DATA);// 移动数据网络状态
		downloadTabPage.randomGo2StartDownload();

		if (!isDataStatu) {// 不是移动网络状态//WIFI
			downloadTabPage.randomGo2PauseDownload();// 马上点击停止，防止进入安装界面
			WaitUtil.forceWait(0.5);
		} else {// 移动网络状态//DATA//CANTUSE
			LogUtil.w("由于处于移动网络，进入弹窗提示页面");
			// 获取页面的Page实例
			DownloadDialogPage downloadDialogPage = new DownloadDialogPage();
			DownloadDialogAction downloadDialogAction = (DownloadDialogAction) downloadDialogPage.action;
			downloadDialogAction.go2ClickAndWait(downloadDialogPage.downLoadGoOn, 1);
			// 再点击暂停
			downloadTabPage.randomGo2PauseDownload();// 马上点击停止，防止进入安装界面
		}
		assertEquals(downloadTabPage.getRandomTargetText(), "继续");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkDeleteDownloadTask() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		// 点击删除
		String tempDetelteAppName = downloadTabPage.randomGo2Delete();
		downloadTabAction.go2ClickAndWait(downloadTabPage.okBtn, 1);
		if (!downloadTabPage.findTargetElement(tempDetelteAppName)) {
			throw new RuntimeException("下载Tab中目标应用没有被成功删除");
		}
	}

	// @Test(dependsOnMethods = { "checkDownloadPauseAndResumeAll" })
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "取消下载但保存文件", riskPoint = "缺乏稳定性较高的检验方法,先保留")
	public void checkDeleteTaskAndKeepTheFile() {
		// TODO 业务逻辑不清楚，不写用例
		LogUtil.printCurrentMethodNameInLog4J();
	}
}
