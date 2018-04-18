package com.cmic.GoAppiumTest.script;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.testcase.RandomUtil;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

//TODO 由于当前的局限性，对这个测试用例暂且预估一个合理时间，确认进行Top指令的次数
@Listeners(FailSnapshotListener.class)
public class Test4PerformanceAnalyze {
	private String mTag;
	private AndroidDriver<AndroidElement> mDriver;

	@BeforeMethod
	public void tipBeforeTestCase() {
		// 性能测试用例前置操作，每个用例执行一次
	}

	@AfterMethod
	public void tipAfterTestCase() {
		// 性能测试用例后置操作，每个用例执行一次
	}

	@BeforeClass
	@Tips(description = "从进入权限管理开始")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		PageRedirect.redirect2SplashActivity();
	}

	@AfterClass
	public void afterClass() throws InterruptedException {// 执行一些初始化操作
		System.err.println("性能测试集[" + mTag + "]结束");
		AppUtil.unInstall(App.PACKAGE_NAME);
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		Assert.assertEquals(ContextUtil.getCurrentActivity(), ".activity.SplashActivity");
		WaitUtil.implicitlyWait(2);
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
		String result = AdbManager.excuteAdbShellGetResult(
				"python D:\\EclipseWorkspace\\GoAppium\\GoAppiumTest\\src\\test\\java\\com\\cmic\\GoAppiumTest\\script\\py\\get_cpu_mem_info.py");
		System.err.println(result);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void passSplashAct() {
		// 点击同意并使用
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement element = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
		element.click();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过授权管理")
	public void passPermissionGrantAct() throws InterruptedException {// 3
		if (DeviceUtil.moreThanTargetSdkVersion("6.0")) {// 是否有授权管理页面
			WaitUtil.implicitlyWait(5);
			LogUtil.printCurrentMethodName();
			AndroidElement buttonAllow = mDriver
					.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"));
			for (int i = 0; i < 4; i++) {
				buttonAllow.click();
				WaitUtil.forceWait(1);
			}
			// 进入首页+进行截图
			WaitUtil.forceWait(2);
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过推荐页面")
	public void passRequestPageAct() {
		WaitUtil.implicitlyWait(5);
		AndroidElement mainButton = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_main"));
		LogUtil.printCurrentMethodName();
		mainButton.click();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过主页页面")
	public void passMainAct() throws InterruptedException {
		// 测试是不是在主页
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");

		String gameUiSelector = "new UiSelector().resourceId(\"com.cmic.mmnes:id/pagerSlide\")"
				+ ".childSelector(new UiSelector().textContains(\"游戏\"))";
		if (ElementUtil.wait4ElementWithoutOverTime(gameUiSelector)) {
			// 等待
			LogUtil.printCurrentMethodName();
			ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
			WaitUtil.forceWait(3);
			ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
			WaitUtil.forceWait(3);
			// 滚动到底部
			ScrollUtil.scrollToBase();
		} else {
			LogUtil.printCurrentMethodName();
			System.err.println("异常情况，*主页*寻找控件超时");
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过流量管家页面", riskPoint = "主页不显示")
	public void passTrafficManagerAct() throws InterruptedException {
		// 测试是不是在主页
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		// 加载
		WaitUtil.implicitlyWait(2);// 等待1S
		// 滑动
		LogUtil.printCurrentMethodName();
		if (ElementUtil.wait4ElementWithoutOverTime(By.id("com.cmic.mmnes:id/jump_ll"))) {
			AndroidElement managerRly = mDriver.findElement(By.id("com.cmic.mmnes:id/jump_ll"));
			managerRly.click();
			// 左右切换
			WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
			if (ElementUtil.wait4ElementWithoutOverTime(
					"new UiSelector().className(\"android.widget.TextView\").textContains(\"按类型查看\")")) {
				ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
				WaitUtil.forceWait(2);
				ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
				WaitUtil.forceWait(2);
				PageRouteUtil.pressBack();
			} else {
				System.err.println("流量管家显示超时");
				PageRouteUtil.pressBack();
			}
			// 退出
		} else {
			System.err.println("异常情况，*主页*寻找控件超时");
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过搜索页面，简单")
	public void passSearchAct() throws InterruptedException {
		// 测试是不是在主页
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		// 进入搜索
		WaitUtil.implicitlyWait(5);// 等待1S
		AndroidElement searchLayout = mDriver.findElement(By.id("com.cmic.mmnes:id/search_layout"));
		LogUtil.printCurrentMethodName();
		searchLayout.click();
		WaitUtil.forceWait(2);
		// 点击搜索
		WaitUtil.implicitlyWait(5);
		mDriver.findElement(By.id("com.cmic.mmnes:id/search_icon_layout")).click();
		// 搜索滑动切换
		WaitUtil.forceWait(2);
		WaitUtil.implicitlyWait(15);
		AndroidElement e = mDriver.findElementByAndroidUIAutomator(
				"new UiSelector().className(\"android.widget.TextView\").textContains(\"全部\")");
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(3);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(3);
		// 滑动到底部
		ScrollUtil.scrollToBaseTimeoutQuit(5);// 滑动超过5秒未见底
		// 退出搜索结果
		PageRouteUtil.pressBack();
		// 退出搜索页
		WaitUtil.forceWait(1);
		// 退回主页
		PageRouteUtil.pressBack();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过主页页面")
	public void passDownloadManagerAct() throws InterruptedException {
		// 测试是不是在主页
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		// 进入DownloadManager
		WaitUtil.implicitlyWait(2);// 等待1S
		AndroidElement managerRly = mDriver.findElement(By.id("com.cmic.mmnes:id/managerview"));
		LogUtil.printCurrentMethodName();
		managerRly.click();
		// 进入SettingActivity
		WaitUtil.implicitlyWait(5);// 等待1S
		AndroidElement settingRly = mDriver.findElement(By.id("com.cmic.mmnes:id/setting_iv"));
		settingRly.click();
		WaitUtil.forceWait(2);
		// 退出Setting
		PageRouteUtil.pressBack();
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.ManagerCenterActivity");
		// 下载管理
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		WaitUtil.forceWait(2);
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		WaitUtil.forceWait(2);
		// 切换滑动
		// 退回主页
		PageRouteUtil.pressBack();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过详情页面")
	public void passDetailAct() throws InterruptedException {
		// 测试是不是在主页
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		// 进入详情页
		goToDetailAct();
		// 进入大图浏览
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		List<AndroidElement> imageList = mDriver.findElements(By.id("com.cmic.mmnes:id/recycledImageView"));
		if (imageList.size() > 1) {// 预期进入ImageBrowerActivity
			// TODO 0时容易出错
			imageList.get(1).click();
			WaitUtil.forceWait(2);
			assertEquals(ContextUtil.getCurrentActivity(), ".activity.ImageBrowseActivity");
		}
		try {
			ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
			WaitUtil.forceWait(1);
			ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
			WaitUtil.forceWait(1);
			PageRouteUtil.pressBack();
			WaitUtil.forceWait(1);
			assertEquals(ContextUtil.getCurrentActivity().equals(".activity.ImageBrowseActivity"), false);
		} catch (Exception e) {
			PageRouteUtil.pressBack();
			throw new AssertionError();
		}
		// 下滑沉底
		ScrollUtil.scrollToBase();
		// 点击进入三层
		goToDeatilByOtherInstall(); // 进入第2层
		goToDeatilByOtherInstall(); // 进入第3层
		// 不退回主页
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "通过详情的H5页面")
	public void goWebviewPage() throws InterruptedException {
		// 进入H5
		LogUtil.printCurrentMethodName();
		ScrollUtil.scrollToBase();
		if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/detail_benefit_layout"))) {
			System.err.println("进入福利的详情H5");
			mDriver.findElement(By.id("com.cmic.mmnes:id/detail_benefit_layout")).click();
			WaitUtil.forceWait(6);// 等待时间加长，防止未加载
			assertEquals(ContextUtil.getCurrentActivity(), ".activity.FavorActivity");
			mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
			WaitUtil.forceWait(2);
		} else {
			System.err.println("DetailActivity不存在免安装和广播");
		}
		// 优先选择免安装进入，其次选择福利的H5
		// 滑动
		ScrollUtil.scrollToBase();
		// 退出
		PageRouteUtil.pressBack();
	}

	@Tips(description = "通过下载页面", riskPoint = "可能进入安装页面")
	@Test(dependsOnMethods = { "initCheck" })
	public void goDownload() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/install_btn"));
		LogUtil.printCurrentMethodName();
		assertEquals(e.getText().contains("下载"), true);
		e.click();
		WaitUtil.forceWait(1);
		if (!e.getText().contains("暂停")) {
			if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/mm_down_goon"))) {
				mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
				WaitUtil.forceWait(1);
				e.click();
				WaitUtil.forceWait(0.5);
			}
		}
		WaitUtil.forceWait(5);
		// 回退
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void extraAnalyze() {
		PageRedirect.redirect2MainActivity();
	}

	private void goToDetailAct() {
		Random randomIndex = new Random();// 主页显示16个Item
		int index = 1 + randomIndex.nextInt(10);// 保证稳定性
		// 定位点击
		WaitUtil.implicitlyWait(10);
		List<AndroidElement> eList = mDriver.findElements(By.id("com.cmic.mmnes:id/index_item_rl"));
		AndroidElement e = eList.get(index);
		WaitUtil.implicitlyWait(2);
		e.click();
		try {
			WaitUtil.forceWait(2);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private void goToDeatilByOtherInstall() {
		ScrollUtil.scrollToBase();
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		String otherInstallItemUiSelector = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/app_name\")";
		List<AndroidElement> eList = mDriver.findElementsByAndroidUIAutomator(otherInstallItemUiSelector);
		int eListSize;
		if ((eListSize = eList.size()) > 0) {
			AndroidElement randomItem = eList.get(RandomUtil.getRandomNum(eListSize - 1));
			randomItem.click();
			WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		}
	}
}
