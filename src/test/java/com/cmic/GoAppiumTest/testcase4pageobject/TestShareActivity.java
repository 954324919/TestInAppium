package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.middlepage.SharePage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;

@Listeners(ExtentReportListener.class)
public class TestShareActivity extends BaseTest {

	private SharePage sharePage;

	@Tips(description = "假设已经在TestShareActivity", riskPoint = "耦合度暂不考虑，从MainTest完成进入")
	@Override
	public void setUpBeforeClass() {
		sharePage = new SharePage();
		sharePage.action.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		LogUtil.e("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
		assertEquals(getCurrentPageName(), "SettingActivity");
		sharePage.snapScreen("进入必备应用设置中心-分享界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查分享应用功能")
	public void shareTheApp() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		sharePage.action.go2SelfPage();
		assertEquals(sharePage.shareDialogVisiable(), true);
		if (sharePage.shareDialogVisiable()) {
			sharePage.action.go2Backforward();
		} else {
			throw new RuntimeException("分享弹窗不显示");
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void shareByLink() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		sharePage.action.go2SelfPage();
		sharePage.clickShareByLink();
	}

	// 在0404，暂时不检查其复制的字段
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试更多分享|目前只有短信分享的方式", riskPoint = "后台运行出现问题，直接重启应用")
	public void shareByMore() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		sharePage.action.go2SelfPage();
		sharePage.clickShareMore();
		// 抓取Toast
		assertEquals(ContextUtil.getPackageName(), App.PACKAGE_NAME);
		sharePage.snapScreen("短信分享");
		AppUtil.runInBackground4AWhile();
	}
}
