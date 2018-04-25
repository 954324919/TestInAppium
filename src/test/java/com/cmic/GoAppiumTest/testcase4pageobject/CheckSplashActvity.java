package com.cmic.GoAppiumTest.testcase4pageobject;

import java.util.NoSuchElementException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.dataprovider.util.ExcelUtil;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SplashPage;
import com.cmic.GoAppiumTest.testcase4pageobject.retry.FailRetry;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;


@Listeners(FailSnapshotListener.class)
public class CheckSplashActvity extends BaseTest {

	private SplashPage mSpalashPage;

	@Tips(description = "继承自BaseActivity,用于增强@BeforeClass", triggerTime = "首次进入应用|清楚缓存后进入")
	@Override
	public void setUpBeforeClass() {
		mSpalashPage = new SplashPage(mDriver);
	}

	@Tips(description = "继承自BaseActivity,用于增强@AfterClass")
	@Override
	public void tearDownAfterClass() {

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() throws Exception {// 0
		// 确认为SplashActivity
		LogUtil.w("进行{}用例集的初始化检验，失败则跳过该用例集的所有测试", mTag);
		// TODO 先试探错误的情况
		Assert.assertEquals(mSpalashPage.getCurrActivity(), "SplashActivity");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkNoNotify() throws InterruptedException {// 1
		// TODO 不再提示需要测试重入，从进入首页后返回检验
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		mSpalashPage.switchTheNoTip();
		WaitUtil.forceWait(2);
		mSpalashPage.switchTheNoTip();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkDenyProcotol() throws InterruptedException {// 2
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		// 点击拒绝协议
		mSpalashPage.clickRefuceProtocol();
		// 屏幕截图
		mSpalashPage.action.go2SnapScreen("点击拒绝协议");
		// 重入应用
		mSpalashPage.action.go2AppReset();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkScrollProcotol() {// 3
		// 滑动协议至其底部
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		mSpalashPage.isElementIsPresent(mSpalashPage.svProtocol);
		mSpalashPage.swipeInTheProtocol();
	};

	@Test(dependsOnMethods = { "initCheck" })
	public void checkComfirmProcotol() {// 4
		// 点击同意并使用
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		mSpalashPage.clickAcceptProtocol();
	}

	@Test
	@Tips(description = "为了测试，模拟进行一个正常的用例")
	public void check4Usual() {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		mSpalashPage.clickAcceptProtocol();
	}

	// TODO 无意义仅为了测试,可删除
	@Tips(description = "为了测试，模拟抛出一个预期的NoSuchEx")
	@Test
	public void check4ExceptException() {
		throw new NoSuchElementException("世界和平");
	}

	// TODO 无意义仅为了测试,可删除
	@Tips(description = "为了测试，模拟进行一次数据驱动")
	@Test(dataProvider = "searchMetadata")
	public void check4DataProvider(String searchKeyWord, String searchResult1, String searchResult2) {
		System.err.println(searchKeyWord + "=>" + searchResult1 + " + " + searchResult2);
	}

	@DataProvider(name = "searchMetadata")
	public Object[][] data() throws Exception {
		return ExcelUtil.readExcel(App.SEARCH_DATA_PROVIDER, App.SEARCH_SHEET_NAME);
	}

}
