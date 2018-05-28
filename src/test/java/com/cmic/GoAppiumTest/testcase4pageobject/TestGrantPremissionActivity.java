package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.PermissionPage;
import com.cmic.GoAppiumTest.page.SplashPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

/**
 * @描述 权限管理弹窗(系统)+权限细则
 * @时机 安卓系统大于6.0（API23），且@TargetAPI>=23 TODO
 *     考虑拆分所有耦合为单独的一个Test,Eg：DenyPermission以及之后的操作
 * @author ikiwi
 */
@Listeners(ExtentReportListener.class)
public class TestGrantPremissionActivity extends BaseTest {

	private PermissionPage mPermissionPage;

	@Tips(description = "继承自BaseActivity,用于增强@BeforeClass", triggerTime = "从Splash页面，准备跳转")
	@Override
	public void setUpBeforeClass() {
		if (DeviceUtil.moreThanTargetSdkVersion(23)) {
			// LogUtil.w("设备Android系统版本为{}", DeviceUtil.getDeviceVersion());
		} else {
			LogUtil.e("设备Android版本低于6.0,没有权限管理特性");
			throw new RuntimeException("设备Android版本低于6.0,没有权限管理特性");
		}
		mPermissionPage = new PermissionPage();
		if (getCurrentPageName().equals("GrantPermissionsActivity")) {
			LogUtil.w("当前所在页面为{}", "GrantPermissionsActivity");
		} else if (getCurrentPageName().equals("SplashActivity")) {
			SplashPage splashPage = new SplashPage();
			splashPage.clickAcceptProtocol();
		}
	}

	@Tips(description = "继承自BaseActivity,用于增强@AfterClass")
	@Override
	public void tearDownAfterClass() {

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() throws InterruptedException {// 1
		WaitUtil.forceWait(2);
		LogUtil.w("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
		assertEquals("GrantPermissionsActivity", getCurrentPageName());
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void chekNotNotify() throws InterruptedException {// 点击不再询问弹窗
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodNameInLog4J();
		mPermissionPage.clickNotAsk();
		// 回复状态
		mPermissionPage.clickNotAsk();
		// 回复状态,影响之后的其他测试
		mPermissionPage.clickNotAsk();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void allowAllPremission() throws InterruptedException {// 必备1.5已知拥有4个高级权限
		LogUtil.printCurrentMethodNameInLog4J();
		mPermissionPage.clickAllowTillAll();
	}

	@Test(dependsOnMethods = { "allowAllPremission" })
	@Tips(description = "测试Splash不取消工信部弹窗", //
			riskPoint = "必须进入应用推荐之后，耦合度最低", //
			triggerTime = "在第一次测试时关闭Spalsh的工信部弹窗不再提示进入首页之后")
	public void testBack4SplashNoCancelTip() throws InterruptedException {// 测试Splash跳过工信部弹窗
		LogUtil.printCurrentMethodNameInLog4J();
		// TODO 存在Bug需要修复
		AppUtil.killApp(getCurrentPackageName());
		AppUtil.runInBackground4AWhile();
		// TODO 等待修复
		mPermissionPage.forceWait(1);
	}

	@Test(dependsOnMethods = { "allowAllPremission" })
	@Tips(description = "和testBack4SplashNoCancelTip相同情况下测试Splash取消跳过工信部弹窗", //
			riskPoint = "需要重启应用")
	public void testBack4SplashCancelTip() {
		LogUtil.printCurrentMethodNameInLog4J();
		SplashPage tempSplashPage = new SplashPage();
		tempSplashPage.action.go2SelfPage();// 重入Splash默认下次不再提示
		Assert.assertEquals(getCurrentPageName(), "SplashActivity");
	}

	@Test
	@Tips(description = "仅为了测试无意义BaseTest是否有效")
	public void test4Test() {
		LogUtil.printCurrentMethodNameInLog4J();
	}
}
