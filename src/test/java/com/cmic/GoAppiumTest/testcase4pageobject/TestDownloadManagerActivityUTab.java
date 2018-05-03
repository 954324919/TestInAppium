package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.DownloadManagerUpdatePage;
import com.cmic.GoAppiumTest.page.action.DownloadManagerUpdateAction;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.Connection;

/**
 * 测试下载管理页面
 * 
 * @author kiwi
 * @风险 不同下载的页面可能启动页的不同
 * @TODO 添加不同的其他的测试，如锁屏选中，增加对下载任务的压力，检查稳定性
 */
@Tips(description = "用于测试下载管理中心的更新Tab")
@Listeners(ExtentReportListener.class)
public class TestDownloadManagerActivityUTab extends BaseTest {

	private DownloadManagerUpdatePage updateTabPage;
	private DownloadManagerUpdateAction updateTabAction;

	@Tips(description = "继承自BaseActivity,用于增强@BeforeClass", triggerTime = "假设已经入首页且显示正常，开始准备跳转到下载管理页")
	@Override
	public void setUpBeforeClass() {
		updateTabPage = new DownloadManagerUpdatePage();
		updateTabAction = (DownloadManagerUpdateAction) updateTabPage.action;
		updateTabAction.go2SelfPage();
	}

	@Tips(description = "继承自BaseActivity,用于增强@AfterClass")
	@Override
	public void tearDownAfterClass() {
		// TODO
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		LogUtil.w("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
		assertEquals(getCurrentPageName(), "ManagerCenterActivity");
		updateTabPage.snapScreen("进入必备应用管理中心界面的更新Tab");
		if (updateTabPage.targetPageItemList.size() == 0) {
			throw new RuntimeException("不存在可更新的应用或游戏");
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkSlide2OtherTab() throws InterruptedException {
		// TODO 加入页面空的判断
		LogUtil.printCurrentMethodNameInLog4J();
		updateTabAction.go2SwipeFullScreen(Direction.RIGHT, 80);
		Assert.assertEquals(updateTabPage.eUpdateTab.isSelected(), false);
		Assert.assertEquals(updateTabPage.eDownloadTab.isSelected(), true);
		updateTabAction.go2SwipeFullScreen(Direction.LEFT, 80);
		Assert.assertEquals(updateTabPage.eUpdateTab.isSelected(), true);
		Assert.assertEquals(updateTabPage.eDownloadTab.isSelected(), false);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkClick2OtherTab() throws InterruptedException {
		// TODO 加入页面空的判断
		LogUtil.printCurrentMethodNameInLog4J();
		updateTabAction.go2ClickAndWait(updateTabPage.eDownloadTab, 2);
		Assert.assertEquals(updateTabPage.eUpdateTab.isSelected(), false);
		Assert.assertEquals(updateTabPage.eDownloadTab.isSelected(), true);
		updateTabAction.go2ClickAndWait(updateTabPage.eUpdateTab, 2);
		Assert.assertEquals(updateTabPage.eUpdateTab.isSelected(), true);
		Assert.assertEquals(updateTabPage.eDownloadTab.isSelected(), false);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击更新的Item", riskPoint = "页面不显示")
	public void checkRamdomEnterDetail() throws InterruptedException {
		updateTabAction.go2SelfPage();
		LogUtil.printCurrentMethodNameInLog4J();
		List<AndroidElement> eList = mDriver.findElements(By.id("com.cmic.mmnes:id/app_name"));
		updateTabPage.go2RandomDetail();
		// TODO 不稳定待解决
		assertEquals(getCurrentPageName(), "DetailActivity");
		updateTabAction.go2Backforward();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkUpdate2Baseline() {
		LogUtil.printCurrentMethodNameInLog4J();
		updateTabAction.go2Swipe2Bottom();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "更新一个Itme", riskPoint = "下载速度太快，进入安装页面")
	public void checkUpdateOne() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		// 开始检查网络
		Connection temp = updateTabAction.go2GetNetWorkStatus();
		LogUtil.w("当前网络状态为{}", temp.name());
		boolean isDataStatu = (temp == Connection.DATA);// 移动数据网络状态
		updateTabPage.randomGo2StartDownload();

		if (!isDataStatu) {// 不是移动网络状态//WIFI
			updateTabPage.randomGo2PauseDownload();// 马上点击停止，防止进入安装界面
			WaitUtil.forceWait(0.5);
		} else {// 移动网络状态//DATA//CANTUSE
			LogUtil.w("由于处于移动网络，进入弹窗提示页面");
			updateTabPage.randomGo2StartDownload();
			updateTabPage.forceWait(1);
			// 再点击暂停
			updateTabPage.randomGo2PauseDownload();// 马上点击停止，防止进入安装界面
		}
		assertEquals(updateTabPage.getRandomTargetText().contains("继续"), true);
	}

	// TODO 过于耗费流量暂时关闭
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击全部下载,不关闭|马上切换到下载页关闭")
	public void checkUpdateAll() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		updateTabAction.go2ClickAndWait(updateTabPage.btnAllUpdate, 2);
		// 先点击取消检验效果
		updateTabAction.go2ClickAndWait(updateTabPage.btnCancelUpdate, 2);
		updateTabPage.snapScreen("下载管理取消全部下载");// TODO 可改用其他方式验证
		// 进入确认
		updateTabAction.go2ClickAndWait(updateTabPage.btnAllUpdate, 2);
		updateTabAction.go2ClickAndWait(updateTabPage.btnAcceptUpdate, 2);
	}
}
