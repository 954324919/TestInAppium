package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestWebviewADActivity {
	private String mTag;
	private AndroidDriver<AndroidElement> mDriver;

	@BeforeMethod
	public void tipBeforeTestCase() {
		// 点击同意并使用
		System.out.println("测试用例[" + (++App.CASE_COUNT) + "]开始");
	}

	@AfterMethod
	public void tipAfterTestCase() {
		System.out.println("测试用例[" + (App.CASE_COUNT) + "]结束");
	}

	@BeforeClass
	@Tips(description = "假设已经入Setting&&未跳转到其他页面")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		PageRedirect.redirect2MainActivity();
		System.err.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {// 执行一些初始化操作
		System.err.println("测试用例集[" + mTag + "]结束");
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// 建议移到和MainyAc一起测试 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面

		// TODO 通过测试后删除
		PageRedirect.redirect2MainActivity();

		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		ScreenUtil.screenShot("进入必备应用主页广告界面");
		WaitUtil.implicitlyWait(2);
	}

	// TODO 可建议分开，方便依赖

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "检查SoftWare底部的集团广告是否存在", riskPoint = "开发大哥根本没有定义ID，怎么定位控件啊，0405取巧")
	public void checkMainSoftAdShow() throws InterruptedException {
		String AdClass = "android.support.v4.view.ViewPager";
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		List<AndroidElement> elementList = mDriver.findElementsByClassName(AdClass);
		WaitUtil.implicitlyWait(10);
		mDriver.findElement(By.id("com.cmic.mmnes:id/index_item_rl")); // 主要是为了让主线程进如隐式等待，避免CPU等待过长
		ScrollUtil.scrollToBase();
		WaitUtil.forceWait(2);
		String gameAdClass = "android.support.v4.view.ViewPager";
		List<AndroidElement> elementListAfterScroll = mDriver.findElementsByClassName(gameAdClass);
		// 滑动到底部，集团广告的ViewPage，主页的ViewPage
		assertEquals(elementList.size() != elementListAfterScroll.size(), true);
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "检测主页软件Tab集团广告内容显示", riskPoint = "必须保障依赖，不然稳定性差")
	public void checkMainSoftAdContent() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		List<AndroidElement> elementList = mDriver.findElements(By.id("com.cmic.mmnes:id/index_item_rl"));
		LogUtil.printCurrentMethodName();
		// 进入该方法说明了底部的集团广告存在
		elementList.get(elementList.size() - 1).click();
		// TODO 后期可加入页面逻辑检测
		WaitUtil.forceWait(3);
		ScreenUtil.screenShot("主页软件Tab集团广告显示");
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.FavorActivity");
		WaitUtil.implicitlyWait(5);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv"));
		e.click();
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查GameTab底部的集团广告是否存在", riskPoint = "开发大哥根本没有定义ID，怎么定位控件啊，0405取巧")
	public void checMainGameAdShow() throws InterruptedException {
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		LogUtil.printCurrentMethodName();
		String gameAdClass = "android.support.v4.view.ViewPager";
		List<AndroidElement> elementList = mDriver.findElementsByClassName(gameAdClass);
		WaitUtil.implicitlyWait(10);
		mDriver.findElement(By.id("com.cmic.mmnes:id/index_item_rl"));
		ScrollUtil.scrollToBase();
		WaitUtil.forceWait(2);
		List<AndroidElement> elementListAfterScroll = mDriver.findElementsByClassName(gameAdClass);
		// 滑动到底部，集团广告的ViewPage，主页的ViewPage
		assertEquals(elementList.size() != elementListAfterScroll.size(), true);
	}

	// TODO 出现新的Activity需要重新进行覆盖
	@Test(dependsOnMethods = { "checMainGameAdShow" })
	@Tips(description = "检测主页游戏Tab集团广告内容显示", riskPoint = "必须保障依赖，不然稳定性差||可能进入登陆页面")
	public void checkMainGameAdContent() throws InterruptedException {
		String curAct1 = ContextUtil.getCurrentActivity();
		boolean isTargetAct1 = curAct1.equals(".activity.LoginActivity") || curAct1.equals(".activity.FavorActivity");
		if (isTargetAct1) {// 用于失败重试
			while (ContextUtil.getCurrentActivity().equals(".activity.MainActivity")) {
				PageRouteUtil.pressBack();
				WaitUtil.forceWait(3);
			}
		} else {
			System.out.println("当前页面异常");
		}
		WaitUtil.implicitlyWait(10);
		List<AndroidElement> elementList = mDriver.findElements(By.id("com.cmic.mmnes:id/index_item_rl"));
		LogUtil.printCurrentMethodName();
		// 进入该方法说明了底部的集团广告存在
		elementList.get(elementList.size() - 1).click();

		// TODO 后期可加入页面逻辑检测
		WaitUtil.forceWait(5);
		ScreenUtil.screenShot("主页游戏Tab集团广告显示");

		// 可能进入.activity.LoginActivity页面
		String curAct = ContextUtil.getCurrentActivity();
		boolean isTargetAct = curAct.equals(".activity.LoginActivity") || curAct.equals(".activity.FavorActivity");
		assertEquals(isTargetAct, true);
		if (curAct.equals(".activity.LoginActivity")) {// LoginActivity先退一次
			AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv"));
			e.click();
			WaitUtil.forceWait(3);
		}
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv"));
		e.click();
		WaitUtil.forceWait(3);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
	}
}
