package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;

/**
 * @描述 权限管理弹窗(系统)+权限细则
 * @时机 安卓系统大于6.0（API23），且@TargetAPI>=23 TODO
 *     考虑拆分所有耦合为单独的一个Test,Eg：DenyPermission以及之后的操作
 * @author ikiwi
 */
@Listeners(ExtentReportListener.class)
public class TestGrantPremissionActivity extends BaseTest {

	@Tips(description = "继承自BaseActivity,用于增强@BeforeClass", triggerTime = "从Splash页面，准备跳转")
	@Override
	public void setUpBeforeClass() {
		if (ContextUtil.getCurrentActivity().equals(".permission.ui.GrantPermissionsActivity")) {

		} else if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/tv_ok"))) {
			WaitUtil.implicitlyWait(5);
			mDriver.findElement(By.id("com.cmic.mmnes:id/tv_ok")).click();
		}
	}

	@Tips(description = "继承自BaseActivity,用于增强@AfterClass")
	@Override
	public void tearDownAfterClass() {

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() throws InterruptedException {// 1
		WaitUtil.forceWait(2);
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(".permission.ui.GrantPermissionsActivity", ContextUtil.getCurrentActivity());
		assertEquals(true, DeviceUtil.moreThanTargetSdkVersion("6.0.0"));//
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void chekNotNotify() throws InterruptedException {// 点击不再询问弹窗
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement notifyCheckbox = mDriver
				.findElement(By.id("com.android.packageinstaller:id/do_not_ask_checkbox"));
		notifyCheckbox.click();
		WaitUtil.forceWait(1);
		// 回复状态
		notifyCheckbox.click();
		WaitUtil.forceWait(1);
		// 回复状态,影响之后的其他测试
		notifyCheckbox.click();
		WaitUtil.forceWait(1);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void denyAllPremission() throws InterruptedException {// 2
		// 必备1.5已知拥有4个高级权限
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement buttonAllow = mDriver
				.findElement(By.id("com.android.packageinstaller:id/permission_deny_button"));
		for (int i = 0; i < 4; i++) {
			buttonAllow.click();
			WaitUtil.forceWait(1);
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkShowPermissionRalation() throws InterruptedException {
		// 弹窗页面
		// 截图
		WaitUtil.forceWait(1);
		LogUtil.printCurrentMethodName();
		ScreenUtil.screenShot("权限弹窗");
		Assert.assertEquals(ContextUtil.getCurrentActivity(), ".activity.SplashActivity");
		System.out.println("进入权限弹窗界面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkGetPermissionDetail() throws InterruptedException {// 点击权限获取说明
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement showDetailButton = mDriver.findElement(By.id("com.cmic.mmnes:id/show_pre_text"));
		showDetailButton.click();
		// 截图
		WaitUtil.forceWait(1);
		ScreenUtil.screenShot("权限获取说明点击,显示列表");
		showDetailButton.click();
		WaitUtil.forceWait(1);
		// 截图
		ScreenUtil.screenShot("权限获取说明二次点击，收起列表");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkPermissionRefuse() throws InterruptedException {// 点击含泪拒绝
		// 点击退出应用
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement showDetailButton = mDriver.findElement(By.id("com.cmic.mmnes:id/mm_continue"));
		showDetailButton.click();
		// 重入应用，再次获取权限
		AppUtil.launchApp();
		// 返回之前的页面
		AndroidElement buttonAllow = mDriver
				.findElement(By.id("com.android.packageinstaller:id/permission_deny_button"));
		for (int i = 0; i < 4; i++) {
			buttonAllow.click();
			WaitUtil.forceWait(1);
		}
		Assert.assertEquals(ContextUtil.getCurrentActivity(), ".activity.SplashActivity");
	}

	/**
	 * 点击再次获取,会进入应用信息管理页面（N3）
	 * 
	 * @throws InterruptedException
	 */
	@Test(dependsOnMethods = { "initCheck" })
	public void checkPermissionRetry() throws InterruptedException {//
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement showDetailButton = mDriver.findElement(By.id("com.cmic.mmnes:id/mm_getagain"));
		showDetailButton.click();
		WaitUtil.forceWait(1);
		// PageRouteUtil.pressBack();
		AppUtil.resetApp();
		WaitUtil.forceWait(1);
		Assert.assertEquals(ContextUtil.getCurrentActivity(), ".activity.SplashActivity");
		System.out.println("重新回到权限弹窗界面");
		// 点击工信部弹窗
		AndroidElement element = mDriver.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
		element.click();
		WaitUtil.implicitlyWait(1);// 等待1S
		assertEquals(".permission.ui.GrantPermissionsActivity", ContextUtil.getCurrentActivity());
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void allowAllPremission() throws InterruptedException {// 必备1.5已知拥有4个高级权限
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		AndroidElement buttonAllow = mDriver
				.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"));
		for (int i = 0; i < 4; i++) {
			buttonAllow.click();
			WaitUtil.forceWait(1);
		}
		// 进入首页，进行截图
		ScreenUtil.screenShot("进入推荐页");
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "allowAllPremission" })
	@Tips(description = "测试Splash不取消工信部弹窗", //
			riskPoint = "必须进入应用推荐之后，耦合度最低", //
			triggerTime = "在第一次测试时关闭Spalsh的工信部弹窗不再提示进入首页之后")
	public void testBack4SplashNoCancelTip() throws InterruptedException {// 测试Splash跳过工信部弹窗
		LogUtil.printCurrentMethodName();
		// TODO 存在Bug需要修复
		String packageName = ContextUtil.getPackageName();
		AppUtil.killApp(packageName);
		AppUtil.runInBackground4AWhile();
		WaitUtil.forceWait(3);
		// TODO 等待修复
		// Assert.assertEquals(ContextUtil.getCurrentActivity(),
		// ".activity.SplashActivity");
		WaitUtil.implicitlyWait(1);
	}

	@Test(dependsOnMethods = { "allowAllPremission" })
	@Tips(description = "和testBack4SplashNoCancelTip相同情况下测试Splash取消跳过工信部弹窗", //
			riskPoint = "需要重启应用")
	public void testBack4SplashCancelTip() {
		PageRedirect.redirect2SplashActivity();// 重入Splash默认下次不再提示
		LogUtil.printCurrentMethodName();
		WaitUtil.implicitlyWait(2);
		Assert.assertEquals(ContextUtil.getCurrentActivity(), ".activity.SplashActivity");
		WaitUtil.implicitlyWait(1);
	}

	@Test
	@Tips(description = "仅为了测试无意义BaseTest是否有效")
	public void test4Test() {
		LogUtil.printCurrentMethodName();
	}
}
