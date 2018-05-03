package com.cmic.GoAppiumTest.script;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.DetailPage;
import com.cmic.GoAppiumTest.page.DownloadManagerUpdatePage;
import com.cmic.GoAppiumTest.page.MainSoftTabPage;
import com.cmic.GoAppiumTest.page.PermissionPage;
import com.cmic.GoAppiumTest.page.RequisitePage;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.SettingPage;
import com.cmic.GoAppiumTest.page.SplashPage;
import com.cmic.GoAppiumTest.page.TrafficManagerPage;
import com.cmic.GoAppiumTest.page.action.DetailAction;
import com.cmic.GoAppiumTest.page.action.DownloadManagerUpdateAction;
import com.cmic.GoAppiumTest.page.action.SplashAction;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.FileUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.Connection;

@Listeners(FailSnapshotListener.class)
@Tips(riskPoint = "当前认为性能脚本进入时应用为第一次进入应用的状态")
public class PerformanceAnalyze4PageObject extends BaseTest {

	@Override
	public void setUpBeforeClass() {
		// TODO Auto-generated method stub
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Tips(description = "用于覆盖父类方法")
	@BeforeMethod
	public void tipBeforeTestCase() {
		// 性能测试用例前置操作，每个用例执行一次
	}

	@Tips(description = "用于覆盖父类方法")
	@AfterMethod
	public void tipAfterTestCase() {
		// 性能测试用例后置操作，每个用例执行一次
	}

	// +++++++++++++ Page部分 ++++++++++++
	private SplashPage splashPage;
	private DetailPage detailPage;
	// +++++++++++++ Action部分 +++++++++++
	private SplashAction splashAction;
	private DetailAction detailAction;

	@BeforeClass
	@Tips(description = "从进入权限管理开始")
	public void beforeClass() {
		// 由于覆盖需要重新赋值
		mTag = getClass().getSimpleName();
		splashPage = new SplashPage();
		splashAction = (SplashAction) splashPage.action;
		splashAction.go2SelfPage();
		LogUtil.w("性能测试集[{}]开始", mTag);
	}

	@Tips(description = "")
	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		LogUtil.w("性能测试集[{}]结束", mTag);
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		LogUtil.w("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
		Assert.assertEquals(getCurrentPackageName(), "SplashActivity");
		new Thread(new Runnable() {
			@Override
			public void run() {
				initPythonScript();
			}
		}).start();
	}

	@Tips(description = "启动Python测试脚本")
	void initPythonScript() {// 2
		// TODO 当前暂且用PY脚本，后期可改为Java
		String scriptPath = FileUtil.filePathTransformRelative(
				"\\src\\test\\java\\com\\cmic\\GoAppiumTest\\script\\py\\get_cpu_mem_info.py");
		String result = AdbManager.excuteAdbShellGetResult("python " + scriptPath);
		LogUtil.w(result);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void passSplashAct() {
		// 点击同意并使用
		LogUtil.printCurrentMethodNameInLog4J();
		splashAction.go2ClickAndWait(splashPage.btnAccept, 1);
		// 进入授权管理页面
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过授权管理")
	public void passPermissionGrantAct() throws InterruptedException {// 3
		if (DeviceUtil.moreThanTargetSdkVersion("6.0")) {// 是否有授权管理页面
			LogUtil.printCurrentMethodNameInLog4J();
			PermissionPage permissionPage = new PermissionPage();// 只在该方法内使用，不作为属性
			permissionPage.clickAllowTillAll();
		} else {
			LogUtil.e("当前设备操作系统低于6.0,不具备权限管理的特性");
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过推荐页面", riskPoint = "当前认为该测试用例一定会进入")
	public void passRequestPageAct() {
		// 进入推荐页面
		LogUtil.printCurrentMethodNameInLog4J();
		RequisitePage requsitePage = new RequisitePage();
		requsitePage.clickGoMain();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过主页页面")
	public void passMainAct() throws InterruptedException {
		// 测试是不是在主页
		LogUtil.printCurrentMethodNameInLog4J();
		MainSoftTabPage mainPage = new MainSoftTabPage();
		mainPage.action.go2SelfPage();
		mainPage.action.go2SwipeFullScreen(Direction.LEFT, 80);
		mainPage.action.go2SwipeFullScreen(Direction.RIGHT, 80);
		mainPage.action.go2Swipe2Bottom();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过流量管家页面", riskPoint = "主页不显示")
	public void passTrafficManagerAct() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		TrafficManagerPage mTrafficPage = new TrafficManagerPage();
		mTrafficPage.action.go2SelfPage();// 加载比较慢
		// 操作
		mTrafficPage.action.go2SwipeFullScreen(Direction.LEFT, 80);
		mTrafficPage.forceWait(1);
		mTrafficPage.action.go2SwipeFullScreen(Direction.RIGHT, 80);
		mTrafficPage.forceWait(1);
		mTrafficPage.drawDown2Refresh(50);// 下拉50%
		mTrafficPage.forceWait(1.5);
		mTrafficPage.action.go2Backforward();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过搜索页面，简单")
	public void passSearchAct() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		//
		SearchPage mSearchPage = new SearchPage();
		mSearchPage.action.go2SelfPage();
		//
		mSearchPage.action.go2ClickAndWait(mSearchPage.btnSubmitSearch, 2);

		SearchResultPage mSearchResultPage = new SearchResultPage();
		mSearchResultPage.action.go2SwipeFullScreen(Direction.LEFT, 80);
		mSearchResultPage.action.go2SwipeFullScreen(Direction.LEFT, 80);
		mSearchResultPage.action.go2SwipeFullScreen(Direction.RIGHT, 80);
		mSearchResultPage.action.go2SwipeFullScreen(Direction.RIGHT, 80);
		//
		mSearchResultPage.action.go2Swipe2BottomWithTimeOut(5);
		mSearchResultPage.action.go2Backforward();
		mSearchResultPage.action.go2Backforward();// 退回主页
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过主页页面")
	public void passDownloadManagerAct() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		//
		DownloadManagerUpdatePage updateTabPage = new DownloadManagerUpdatePage();
		DownloadManagerUpdateAction updateTabAction = (DownloadManagerUpdateAction) updateTabPage.action;
		updateTabAction.go2SelfPage();
		//
		updateTabAction.go2SwipeFullScreen(Direction.RIGHT, 80);
		updateTabAction.go2SwipeFullScreen(Direction.LEFT, 80);
		//
		new SettingPage().action.go2SelfPage();
		go2Backforward();
		//
		updateTabAction.go2Backforward();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过详情页面")
	public void passDetailAct() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		//
		detailPage = new DetailPage();
		detailAction = (DetailAction) detailPage.action;
		detailAction.go2SelfPage();
		//
		detailPage.go2WithoutInstall();// 进入免安装页面
		//
		detailAction.go2SwipeFullScreen(Direction.LEFT, 80);
		detailAction.go2SwipeFullScreen(Direction.RIGHT, 80);
		go2Backforward();// 无状态页面后退

		// 下滑沉底
		detailAction.go2Swipe2Bottom();//
		// 点击进入三层
		goToDeatilByOtherInstall(); // 进入第2层
		goToDeatilByOtherInstall(); // 进入第3层
		// 不退回主页
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过详情的H5页面")
	public void goWebviewPage() throws InterruptedException {
		// 进入H5
		LogUtil.printCurrentMethodNameInLog4J();
		detailAction.go2Swipe2Bottom();
		//
		if (detailPage.isElementShown(detailPage.benifitLly)) {
			LogUtil.w("进入福利的详情H5");
			detailAction.go2ClickAndWait(detailPage.benifitLly, 3);
			go2Backforward();
		} else {
			LogUtil.e("DetailActivity不存在免安装和广播");
		}
		detailAction.go2Swipe2Bottom();
		detailAction.go2Backforward();
	}

	@Tips(description = "通过下载页面", riskPoint = "可能进入安装页面")
	@Test(dependsOnMethods = { "initCheck" })
	public void goDownload() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();

		assertTrue(detailAction.go2GetText(detailPage.btnInstall).contains("下载"));
		// 开始检查网络
		Connection temp = detailAction.go2GetNetWorkStatus();
		LogUtil.w("当前网络状态为{}", temp.name());
		boolean isDataStatu = (temp == Connection.DATA);// 移动数据网络状态
		detailAction.go2Click(detailPage.btnInstall);
		if (!isDataStatu) {// 不是移动网络状态//WIFI
			detailAction.go2ClickAndWait(detailPage.btnInstall, 0.5);// 马上点击停止，防止进入安装界面
		} else {// 移动网络状态//DATA//CANTUSE
			LogUtil.w("由于处于移动网络，进入弹窗提示页面");
			detailAction.go2ClickAndWait(detailPage.btnInstall, 0.5);
			// 再点击暂停
			detailAction.go2ClickAndWait(detailPage.btnInstall, 0.5);// 马上点击停止，防止进入安装界面
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void extraAnalyze() {
		new MainTempPage().action.go2SelfPage();
	}

	@Tips(description = "引入仅为了测试，无意义")
	@Test(enabled = false)
	public void launchAnalyzeScript() {
		String scriptPath = FileUtil.filePathTransformRelative(
				"\\src\\test\\java\\com\\cmic\\GoAppiumTest\\script\\py\\get_cpu_mem_info.py");
		String result = AdbManager.excuteAdbShellGetResult("python " + scriptPath);
		LogUtil.w(result);
	}

	private void goToDeatilByOtherInstall() {
		DetailPage detailPage = new DetailPage();
		DetailAction detailAction = (DetailAction) detailPage.action;
		detailPage.goRandomClick2DetailInOtherInstall();
		detailAction.go2Swipe2Bottom();//
	}
}
