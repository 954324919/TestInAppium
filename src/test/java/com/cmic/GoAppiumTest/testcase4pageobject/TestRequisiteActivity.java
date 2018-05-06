package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.page.RequisitePage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.LogUtil;

@Listeners(FailSnapshotListener.class)
public class TestRequisiteActivity extends BaseTest {

	private RequisitePage mRequsitePage;

	@Override
	public void setUpBeforeClass() {
		mRequsitePage = new RequisitePage();
		mRequsitePage.action.go2SelfPage();
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() throws InterruptedException {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		mRequsitePage.forceWait(1);
		LogUtil.w("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
		assertEquals(getCurrentPageName(), "RequisiteActivity");
		mRequsitePage.snapScreen("进入装机必备界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkAllSelect() {
		LogUtil.printCurrentMethodNameInLog4J();
		mRequsitePage.clickAllCheckbox();
		mRequsitePage.snapScreen("点击全选CheckBox");
		mRequsitePage.clickAllCheckbox();
		mRequsitePage.snapScreen("点击全选CheckBox恢复");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkOneSelect() {
		LogUtil.printCurrentMethodNameInLog4J();
		mRequsitePage.clickOneItemCheckbox();
		mRequsitePage.snapScreen("点击复选框选中");
		// 再次点击复选框
		mRequsitePage.clickOneItemCheckbox();
		mRequsitePage.snapScreen("点击复选框恢复");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkBackForward() {// 一旦回退，只能回到首页
		LogUtil.printCurrentMethodNameInLog4J();
		mRequsitePage.action.go2Backforward();
		assertEquals(getCurrentPageName(), "Launcher");
		mRequsitePage.action.goLaunchApp();
		mRequsitePage.forceWait(1);
		;// 等待1S
		assertEquals(getCurrentPageName(), "MainActivity");
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	public void checkOneGoDownload() throws InterruptedException {// 进入下载中心
		LogUtil.printCurrentMethodNameInLog4J();
		mRequsitePage.action.go2SelfPage();
		mRequsitePage.clickGoDownloadManager();
		if (!getCurrentPageName().equals("ManagerCenterActivity")) {// 不够稳定
			mRequsitePage.forceWait(3);
		}
		assertEquals(getCurrentPageName(), "ManagerCenterActivity");
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	public void checkEnterMain() throws InterruptedException {// 进入首页
		LogUtil.printCurrentMethodNameInLog4J();
		mRequsitePage.action.go2SelfPage(); // 此为清除缓存的行为，开启全部测试的时候必须开启
		mRequsitePage.clickGoMain();
		assertEquals(getCurrentPageName(), "MainActivity");
	}
}
