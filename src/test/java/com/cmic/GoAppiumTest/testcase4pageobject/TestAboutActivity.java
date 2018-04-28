package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.middlepage.AboutPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;

public class TestAboutActivity extends BaseTest {

	private AboutPage aboutPage;

	@Tips(description = "继承自BaseActivity,用于增强@BeforeClass", triggerTime = "假设已经入Setting&&未跳转到其他页面")
	@Override
	public void setUpBeforeClass() {
		aboutPage = new AboutPage();
		aboutPage.action.go2SelfPage();
	}

	@Tips(description = "继承自BaseActivity,用于增强@AfterClass")
	@Override
	public void tearDownAfterClass() {
		if (getCurrentPageName().equals("AboutActivity")) {
			go2Backforward();// 回退一次
		} else if (getCurrentPageName().equals("ProtocalActivity")) {
			go2Backforward();
			go2Backforward();
		} else if (getCurrentPageName().equals("SettingActivity")) {
			return;
		}
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// 先确认是否进入该页面
		LogUtil.w("进行{}用例集的初始化检验，失败则跳过该用例集的所有测试", mTag);
		assertEquals(getCurrentPageName(), "AboutActivity");
		aboutPage.snapScreen("进入必备应用关于界面");
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "检查关于应用-更新功能", riskPoint = "0404只能检查是最新版本")
	public void checkAboutUpdate() throws InterruptedException {//
		LogUtil.printCurrentMethodNameInLog4J();
		aboutPage.click2Update();
		// 抓取Toast 如果抓不到就判断是否有软件更新
		aboutPage.isTargetToast("已经是最新版本");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkUserProtocol() {
		LogUtil.printCurrentMethodNameInLog4J();
		aboutPage.click2UserProtocol();
		assertEquals(getCurrentPageName(), "ProtocalActivity");// TODO 必要性
		aboutPage.snapScreen("用户协议显示");
	}

	@Test(dependsOnMethods = { "checkUserProtocol" })
	public void rollAndClose() throws InterruptedException {// 进入用户协议
		LogUtil.printCurrentMethodNameInLog4J();
		go2Swipe2Bottom();//
		go2Backforward();
		assertEquals(getCurrentPageName(), "AboutActivity");
	}
}