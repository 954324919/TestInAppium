package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

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
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.NetworkUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestMainActivity {
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
	public void beforeClass() {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		WaitUtil.implicitlyWait(2);// 等待1S
		PageRedirect.redirect2MainActivity();
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
		// AdbManager.excuteAdbShell("adb uninstall com.cmic.mmnes");
	}

	@Test(enabled=false)
	public void initCheck() throws InterruptedException {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		ScreenUtil.screenShot("进入必备应用主页界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" },enabled=false)
	public void testNotification() {
		DeviceUtil.openNotification();
		WaitUtil.implicitlyWait(2);
		DeviceUtil.closeNotification();
		WaitUtil.implicitlyWait(2);
	}

	@Test
	public void testBack4SplashNoTip() {// 测试Splash跳过工信部弹窗

	}

	@Test
	public void testBack4SpalshTip() {// 测试Splash的工信部提示

	}

	@Test
	public void scroll2Bottom() {// 测试滑动到底部

	}

	@Test
	public void switchToOtherTab() {// 测试其他Tab(软件-游戏)

	}

	@Test
	public void refreshBatch() {// 点击换一批

	}

	@Test
	@Tips(description = "测试精选福利", riskPoint = "页面变动")
	public void testWellSelect() {// 测试精选福利

	}

	@Test
	@Tips(description = "测试点击精品应用", riskPoint = "页面变动")
	public void checkGreatGame() {
        
	}

}
