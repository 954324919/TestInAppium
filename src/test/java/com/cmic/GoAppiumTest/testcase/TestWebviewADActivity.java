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
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
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
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test(enabled = false)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		ScreenUtil.screenShot("进入必备应用主页广告界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查SoftWare底部的集团广告", riskPoint = "开发大哥根本没有定义ID，怎么定位控件啊，0405取巧")
	public void checkMainSoftAdShow() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		String AdClass = "android.support.v4.view.ViewPager";
		List<AndroidElement> elementList = mDriver.findElementsByClassName(AdClass);
		WaitUtil.implicitlyWait(16);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/index_item_rl"));
		ScrollUtil.scrollToBase();
		WaitUtil.forceWait(2);
		String gameAdClass = "android.support.v4.view.ViewPager";
		List<AndroidElement> elementListAfterScroll = mDriver.findElementsByClassName(gameAdClass);
		// 滑动到底部，集团广告的ViewPage，主页的ViewPage
		assertEquals(elementList.size() != elementListAfterScroll.size(), true);
	}

	@Test(dependsOnMethods = { "checkMainSoftAdShow" }, enabled = false)
	public void checkMainSoftAdContent() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		List<AndroidElement> elementList = mDriver.findElements(By.id("com.cmic.mmnes:id/index_item_rl"));
		// 进入该方法说明了底部的集团广告存在
		elementList.get(elementList.size() - 1).click();
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.FavorActivity");
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv"));
		e.click();
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查GameTab底部的集团广告", riskPoint = "开发大哥根本没有定义ID，怎么定位控件啊，0405取巧")
	public void checMainGameAdShow() throws InterruptedException {
		ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
		LogUtil.printCurrentMethodName();
		String gameAdClass = "android.support.v4.view.ViewPager";
		List<AndroidElement> elementList = mDriver.findElementsByClassName(gameAdClass);
		WaitUtil.implicitlyWait(16);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/index_item_rl"));
		ScrollUtil.scrollToBase();
		WaitUtil.forceWait(2);
		List<AndroidElement> elementListAfterScroll = mDriver.findElementsByClassName(gameAdClass);
		// 滑动到底部，集团广告的ViewPage，主页的ViewPage
		assertEquals(elementList.size() != elementListAfterScroll.size(), true);
	}

	@Test(dependsOnMethods = { "checMainGameAdShow" }, enabled = false)
	public void checkMainGameAdContent() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		List<AndroidElement> elementList = mDriver.findElements(By.id("com.cmic.mmnes:id/index_item_rl"));
		// 进入该方法说明了底部的集团广告存在
		elementList.get(elementList.size() - 1).click();
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.FavorActivity");
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv"));
		e.click();
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
	}
}
