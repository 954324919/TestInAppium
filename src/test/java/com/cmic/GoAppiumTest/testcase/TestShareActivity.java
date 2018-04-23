package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Tips;
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

	@Tips(description = "假设已经在TestShareActivity", riskPoint = "耦合度暂不考虑，从MainTest完成进入")
	@Override
	public void setUpBeforeClass() {

	}

	@Override
	public void tearDownAfterClass() {

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SettingActivity");
		ScreenUtil.screenShot("进入必备应用设置中心-分享界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查分享应用功能")
	public void shareTheApp() throws InterruptedException {
		goShare();
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		// TODO 检查影响，0404暂不实现
		boolean shareLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/tv_cancel"));
		assertEquals(shareLlyIsPresent, true);
		if (shareLlyIsPresent) {
			PageRouteUtil.pressBack();
		}
		WaitUtil.forceWait(1);
	}

	public void goShare() {
		WaitUtil.implicitlyWait(2);
		AndroidElement shareLly = mDriver.findElement(By.id("com.cmic.mmnes:id/rl_share"));
		shareLly.click();
		WaitUtil.forceWait(3);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void shareByLink() throws InterruptedException {
		goShare();
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement shareByLinkLly = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_copy"));
		shareByLinkLly.click();

	}

	// 在0404，暂时不检查其复制的字段
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试更多分享|目前只有短信分享的方式", riskPoint = "后台运行出现问题，直接重启应用")
	public void shareByMore() throws InterruptedException {
		goShare();
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(5);
		AndroidElement shareByMoreLly = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_share"));
		shareByMoreLly.click();
		// 抓取Toast
		WaitUtil.implicitlyWait(5);
		AndroidElement shareByMessageLly = mDriver.findElement(By.id("com.cmic.mmnes:id/item_layout"));
		shareByMessageLly.click();
		//
		WaitUtil.forceWait(5);
		String targetPackageName = ContextUtil.getPackageName();
		assertEquals(targetPackageName != App.PACKAGE_NAME, true);
		ScreenUtil.screenShot("短信分享");
		AppUtil.runInBackground4AWhile();
	}
}
