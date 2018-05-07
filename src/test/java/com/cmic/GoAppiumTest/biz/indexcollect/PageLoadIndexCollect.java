package com.cmic.GoAppiumTest.biz.indexcollect;

import java.sql.Driver;
import java.util.List;

import org.eclipse.jetty.websocket.common.ConnectionState;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTestNew;
import com.cmic.GoAppiumTest.biz.base.BaseTest4IndexCollect;
import com.cmic.GoAppiumTest.helper.AndroidDriverWait;
import com.cmic.GoAppiumTest.helper.ExpectedCondition4AndroidDriver;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SplashPage;
import com.cmic.GoAppiumTest.page.middlepage.NotificationPage;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.gargoylesoftware.htmlunit.javascript.host.Notification;
import com.google.common.base.Function;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.Connection;

public class PageLoadIndexCollect extends BaseTestNew {

	@Override
	public void setUpBeforeClassOrigin() {
		LogUtil.w("{}用例集[{}]开始", "指标收集", mTag);
	}

	@Override
	public void tearDownAfterClassOrigin() {
		LogUtil.w("{}用例集[{}]结束", "指标收集", mTag);
	}

	@Override
	public void setUpBeforeMTestCaseOrigin() {
		LogUtil.w("{}用例{}开始", "指标收集", ++App.CASE_COUNT);
	}

	@Override
	public void tearDownAfterMTestCaseOrigin() {
		LogUtil.w("{}用例{}结束", "指标收集", App.CASE_COUNT);
	}

	@Test
	public void initCheck() {
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2OpenTheWifi();
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
		notificationPage.go2CloseTheWifi();
		LogUtil.e("当前网络状态为{}", mDriver.getConnection());
	}

	@Tips(description = "加载首页", riskPoint = "受网速|缓存影响较大")
	@Test
	public void loadMainPage() {

	}

	@Tips(description = "加载流量管理", riskPoint = "受网速|缓存影响较大")
	public void loadTrafficPage() {

	}

	@Tips(description = "加载首页H5", riskPoint = "受网速|缓存影响较大")
	public void loadMainAdPage() {

	}

	@Tips(description = "加载搜索结果", riskPoint = "受网速|缓存影响较大")
	public void loadSearchPage() {

	}

	@Tips(description = "移动数据网络加载首页", riskPoint = "受网速|缓存影响较大")
	public void loadMainPageWithoutWifi() {

	}

	@Tips(description = "移动数据网络加载流量管理", riskPoint = "受网速|缓存影响较大")
	public void loadTrafficPageWithoutWifi() {

	}

	@Tips(description = "移动数据网络加载首页H5", riskPoint = "受网速|缓存影响较大")
	public void loadMainAdPageWithoutWifi() {

	}

	@Tips(description = "移动数据网络加载搜索结果", riskPoint = "受网速|缓存影响较大")
	public void loadSearchPageWithoutWifi() {

	}
}
