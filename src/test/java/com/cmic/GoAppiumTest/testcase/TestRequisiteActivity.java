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
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.NetworkUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @描述 推荐页面
 * @时机 确认授予所有权限，第一次进入应用
 * @其他 未完成无网络的状态确认
 * @author kiwi
 */
public class TestRequisiteActivity {
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
		WaitUtil.implicitlyWait(3);// 等待1S
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
		// AdbManager.excuteAdbShell("adb uninstall com.cmic.mmnes");
	}

	@Test(enabled = false)
	public void initCheck() throws InterruptedException {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.RequisiteActivity");
		ScreenUtil.screenShot("进入装机必备界面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkAllSelect() {
		AndroidElement element = mDriver.findElement(By.xpath("//android.widget.CheckBox[@text=' 全选']"));
		element.click();
		ScreenUtil.screenShot("点击全选CheckBox");
		WaitUtil.implicitlyWait(2);
		element.click();
		ScreenUtil.screenShot("点击全选CheckBox恢复");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkOneSelect() {
		AndroidElement element = mDriver.findElement(By.xpath(
				"//android.support.v7.widget.RecyclerView[@resource-id='com.cmic.mmnes:id/rv_app']/android.widget.RelativeLayout[1]"));
		// 点击复选框所属的LinearLayout
		element.click();
		WaitUtil.implicitlyWait(2);
		ScreenUtil.screenShot("点击复选框选中");
		AndroidElement checkBox = (AndroidElement) element.findElement(By.id("com.cmic.mmnes:id/checkbox"));
		// 再次点击复选框
		checkBox.click();
		ScreenUtil.screenShot("点击复选框恢复");
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkBackForward() {// 一旦回退，只能回到首页
		PageRouteUtil.pressBack();
		assertEquals(ContextUtil.getCurrentActivity(), ".Launcher");
		WaitUtil.implicitlyWait(1);// 等待1S
		AppUtil.launchApp();
		WaitUtil.implicitlyWait(2);// 等待1S
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
		WaitUtil.implicitlyWait(1);// 等待1S
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkEnterMain() {// 进入首页
		PageRedirect.redirect2RequestiteActivity(); // 此为清除缓存的行为，开启全部测试的时候必须开启
		// com.cmic.mmnes:id/tv_main
		AndroidElement mainButton = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_main"));
		mainButton.click();
		WaitUtil.implicitlyWait(2);// 等待1S
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.MainActivity");
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkOneGoDownload() {// 进入下载中心
		PageRedirect.redirect2RequestiteActivity();
		// com.cmic.mmnes:id/tv_main
		AndroidElement mainButton = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_load"));
		mainButton.click();
		WaitUtil.implicitlyWait(3);// 等待1S
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.ManagerCenterActivity");
		WaitUtil.implicitlyWait(1);// 等待1S
	}
}
