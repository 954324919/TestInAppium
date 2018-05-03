package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.PermissionPage;
import com.cmic.GoAppiumTest.page.middlepage.PermissionTipsPage;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

@Tips(description = "权限弹窗", riskPoint = "权限弹窗不是上下文Activity,依附于SpalashAct")
@Listeners(FailSnapshotListener.class)
public class TestGrantpermissionActivityTips extends BaseTest {

	private PermissionTipsPage mPermissionTipPage;

	@Tips(description = "继承自BaseActivity,用于增强@BeforeClass", triggerTime = "从Splash页面，准备跳转")
	@Override
	public void setUpBeforeClass() {
		if (DeviceUtil.moreThanTargetSdkVersion("6.0.0")) {
			LogUtil.w("设备Android系统版本为{}", DeviceUtil.getDeviceVersion());
		} else {
			LogUtil.e("设备Android版本低于6.0,没有权限管理特性");
			throw new RuntimeException("设备Android版本低于6.0,没有权限管理特性");
		}
		mPermissionTipPage = new PermissionTipsPage();
		mPermissionTipPage.action.go2SelfPage();// TODO 不够稳定
		if (getCurrentPageName().equals("GrantPermissionsActivity")) {
			LogUtil.w("当前不是目标页面,所在页面为{}", "GrantPermissionsActivity");
			PermissionPage tempPermissionPage = new PermissionPage();
			tempPermissionPage.clickDenyTillAll();
		}
	}

	@Tips(description = "继承自BaseActivity,用于增强@AfterClass")
	@Override
	public void tearDownAfterClass() {

	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() throws InterruptedException {// 1
		mPermissionTipPage.forceWait(2);
		LogUtil.w("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
		assertEquals("SplashActivity", getCurrentPageName());//
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkShowPermissionRalation() throws InterruptedException {
		// 弹窗页面
		LogUtil.printCurrentMethodNameInLog4J();
		// 截图
		mPermissionTipPage.snapScreen("权限提示弹窗");
		Assert.assertEquals(getCurrentPageName(), "SplashActivity");
		LogUtil.w("进入权限弹窗界面");
	}

	@Test(dependsOnMethods = { "checkShowPermissionRalation" })
	public void checkGetPermissionDetail() throws InterruptedException {// 点击权限获取说明
		LogUtil.printCurrentMethodNameInLog4J();
		mPermissionTipPage.click2GetPermissionDetail();
		// 截图
		mPermissionTipPage.snapScreen("权限获取说明点击,显示列表");
		mPermissionTipPage.click2GetPermissionDetail();
		// 截图
		mPermissionTipPage.snapScreen("权限获取说明二次点击，收起列表");
	}

	@Test(dependsOnMethods = { "checkGetPermissionDetail" })
	public void checkPermissionRefuse() throws InterruptedException {// 点击含泪拒绝
		// 点击退出应用
		LogUtil.printCurrentMethodNameInLog4J();
		mPermissionTipPage.click2RefucePermissionTip();
		// 重入应用，再次获取权限
		// AppUtil.launchApp();
		mPermissionTipPage.action.go2AppReset();
		// 返回之前的页面
		// mPermissionTipPage = new PermissionTipsPage();// 需要通过这种方式不然页面刷新会丢失Session
		mPermissionTipPage.action.go2SelfPage();
		Assert.assertEquals(getCurrentPageName(), "SplashActivity");
	}

	/**
	 * 点击再次获取,会进入应用信息管理页面（N3）
	 * 
	 * @throws InterruptedException
	 */
	@Test(dependsOnMethods = { "checkPermissionRefuse" })
	public void checkPermissionRetry() throws InterruptedException {//
		LogUtil.printCurrentMethodNameInLog4J();
		// PermissionTipsPage mPermissionTipPage = new PermissionTipsPage();
		mPermissionTipPage.click2RetryGetPermission();
		mPermissionTipPage.snapScreen("进入权限管理中心");
		mPermissionTipPage.action.go2Backforward();
		mPermissionTipPage.action.go2AppReset();
		// WaitUtil.forceWait(1); #停止为了重入
		Assert.assertEquals(getCurrentPageName(), "SplashActivity");
		LogUtil.w("重新回到权限弹窗界面");
	}

}
