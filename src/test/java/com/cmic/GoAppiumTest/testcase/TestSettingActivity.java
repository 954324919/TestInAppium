package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestSettingActivity {
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
	@Tips(description = "假设已经入ManagerAct&&未跳转到其他页面")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		PageRedirect.redirect2SettingActivity();
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test(enabled = false)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SettingActivity");
		ScreenUtil.screenShot("进入必备应用设置中心界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查自更新设置的影响")
	public void checkAutoUpdate() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement updateSetting = mDriver.findElement(By.id("com.cmic.mmnes:id/setting_zero_layout"));
		updateSetting.click();
		// TODO 检查影响，0404暂不实现
		WaitUtil.forceWait(1);
		updateSetting.click();
		// TODO 检查影响，0404暂不实现
		WaitUtil.forceWait(1);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查下载提示的影响")
	public void downloadTipShow() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		// TODO 检查影响，0404暂不实现
		showDialog();
		WaitUtil.implicitlyWait(2);
		boolean notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, true);
		if (notifyLlyIsPresent) {
			PageRouteUtil.pressBack();
		}
		WaitUtil.forceWait(1);
	}

	public void showDialog() {
		WaitUtil.implicitlyWait(2);
		AndroidElement notifyLly = mDriver.findElement(By.id("com.cmic.mmnes:id/setting_download_notice_layout"));
		notifyLly.click();
		try {
			WaitUtil.forceWait(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查下载提示的影响")
	public void downloadTipCloseInOtherWay() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement notifyLly = mDriver.findElement(By.id("com.cmic.mmnes:id/setting_download_notice_layout"));
		// 重新点击显示NotifyDialog
		notifyLly.click();
		WaitUtil.forceWait(1);
		boolean notifyLlyIsPresent;
		notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, true);
		AndroidElement clearIcon = mDriver.findElement(By.id("com.cmic.mmnes:id/close_iv"));
		clearIcon.click();
		WaitUtil.forceWait(1);
		notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, false);
		// 重新点击显示NotifyDialog
		notifyLly.click();
		WaitUtil.forceWait(1);
		notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, true);
		AndroidElement cancelIcon = mDriver.findElement(By.id("com.cmic.mmnes:id/mm_dialog_cancel"));
		cancelIcon.click();
		WaitUtil.forceWait(1);
		notifyLlyIsPresent = ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/flow_seekbar"));
		assertEquals(notifyLlyIsPresent, false);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查下载提示的影响")
	public void setRangeByEditText() {
		showDialog();
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		AndroidElement et = mDriver.findElement(By.id("com.cmic.mmnes:id/flow_setting_et"));
		et.clear();
		et.sendKeys("250");
		ScreenUtil.screenShot("输入250M时提示");
		// 点击确认
		mDriver.findElement(By.id("com.cmic.mmnes:id/mm_dialog_ok")).click();
		WaitUtil.implicitlyWait(2);
		AndroidElement notifyContentTv = mDriver
				.findElement(By.id("com.cmic.mmnes:id/setting_download_notice_content"));
		String setDownloadRangerResult = notifyContentTv.getText().replaceAll("[^0-9]", "");
		assertEquals(setDownloadRangerResult, "250");
		// 回复原先的Seekbar状态
		showDialog();
		WaitUtil.implicitlyWait(2);
		AndroidElement et1 = mDriver.findElement(By.id("com.cmic.mmnes:id/flow_setting_et"));
		et1.clear();
		mDriver.findElement(By.id("com.cmic.mmnes:id/mm_dialog_ok")).click();
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void setRangeBySeekbar() throws InterruptedException {
		showDialog();
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		String seekBarUiSelector = "new UiSelector().className(\"android.widget.SeekBar\").resourceId(\"com.cmic.mmnes:id/flow_seekbar\")";
		AndroidElement seekbar = mDriver.findElementByAndroidUIAutomator(seekBarUiSelector);
		seekbar.clear();
		// 点击Seekbar中间点
		// 获取控件开始位置的坐标轴
		Point start = seekbar.getLocation();
		int startX = start.x;
		int startY = start.y;
		// 获取控件宽高
		Dimension q = seekbar.getSize();
		int x = q.getWidth();
		int y = q.getHeight();
		// 计算出控件结束坐标
		int endX = x + startX;
		int endY = y + startY;
		// 计算中间点坐标
		int centreX = (endX + startX) / 2;
		int centreY = (endY + startY) / 2;
		// 点击
		ScreenUtil.singleTap(centreX, centreY);
		WaitUtil.forceWait(1);
		ScreenUtil.screenShot("输入250M时提示");
		// 点击确认
		mDriver.findElement(By.id("com.cmic.mmnes:id/mm_dialog_ok")).click();
		WaitUtil.implicitlyWait(2);
		AndroidElement notifyContentTv = mDriver
				.findElement(By.id("com.cmic.mmnes:id/setting_download_notice_content"));
		assertEquals(notifyContentTv.getText().replaceAll("[^0-9]", ""), "250");
	}
}
