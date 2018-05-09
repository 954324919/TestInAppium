package com.cmic.GoAppiumTest.biz.indexcollect;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.biz.base.BaseTest4IndexCollect;
import com.cmic.GoAppiumTest.helper.AndroidDriverWait;
import com.cmic.GoAppiumTest.helper.ExpectedCondition4AndroidDriver;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SplashPage;
import com.cmic.GoAppiumTest.page.middlepage.MainTempPage;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.EssentialUtil;
import com.cmic.GoAppiumTest.util.LogUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

@Tips(description = "统计应用启动时间", riskPoint = "使用PageObject较之DriverWait不够准确，不采用")
public class BootstrapIndexCollect extends BaseTest4IndexCollect {

	private SplashPage mSplashPage;

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setUpBeforeClass() {
		AppUtil.killApp(App.PACKAGE_NAME);
	}

	@Test(timeOut = 15000, invocationCount = 2)
	public void bootstrapInResetApp() {
		LogUtil.printCurrentMethodNameInLog4J();
		AppUtil.resetApp();
		long beforeTime = System.currentTimeMillis();
		AndroidDriverWait wait = new AndroidDriverWait(mDriver, 15, 100);
		try {
			wait.until(new ExpectedCondition4AndroidDriver<Boolean>() {
				@Override
				public Boolean apply(AndroidDriver<AndroidElement> arg0) {
					AndroidElement e = arg0.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
					long afterTime = System.currentTimeMillis();
					if (e != null)
						LogUtil.i("启动完成时间为:{}", EssentialUtil.getTheTimeDiff(beforeTime, afterTime));
					return true;// 不关注结果
				}
			});
		} catch (Exception e) {
			LogUtil.e("元素寻找超时(15秒)");
		}

	}

	@Test(timeOut = 15000, invocationCount = 2)
	public void bootstrapInLauncherApp() {
		LogUtil.printCurrentMethodNameInLog4J();
		AppUtil.launchApp();
		//
		long beforeTime = System.currentTimeMillis();
		AndroidDriverWait wait = new AndroidDriverWait(mDriver, 15, 100);
		try {
			wait.until(new ExpectedCondition4AndroidDriver<Boolean>() {
				@Override
				public Boolean apply(AndroidDriver<AndroidElement> arg0) {
					AndroidElement e = arg0.findElement(By.id("com.cmic.mmnes:id/tv_ok"));
					long afterTime = System.currentTimeMillis();
					if (e != null)
						LogUtil.i("启动完成时间为:{}", EssentialUtil.getTheTimeDiff(beforeTime, afterTime));
					return true;
				}
			});
		} catch (Exception e) {
			LogUtil.e("元素寻找超时(15秒)");
		}
	}

	@Tips(description = "使用PageObject不够准确，还是采用显示等待")
	@Test(invocationCount = 3)
	public void bootstrapInResetAppByPO() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSplashPage = new SplashPage();
		mSplashPage.action.go2AppReset();
		LogUtil.i("启动完成时间为:{}", mSplashPage.action.go2GetTimeDiffElementShow(mSplashPage.btnAccept));
	}

	@Tips(description = "使用PageObject不够准确，还是采用显示等待")
	@Test(invocationCount = 3)
	public void bootsrtapInLauncherAppByPO() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSplashPage = new SplashPage();
		mSplashPage.action.goLaunchApp();
		LogUtil.i("启动完成时间为:{}", mSplashPage.action.go2GetTimeDiffElementShow(mSplashPage.btnAccept));
	}

	@Tips(description = "启动到首页|迁移到PageLoadingCollect")
	public void bootstrap2MainByPO() {
		LogUtil.printCurrentMethodNameInLog4J();
		// mSplashPage.action.go2PressAndroidKey(AndroidKeyCode.HOME);
		// mSplashPage.action.go2killApp(App.PACKAGE_NAME);
		mSplashPage.action.go2SoftReset();
		MainTempPage mainTempPage = new MainTempPage();
		LogUtil.i("启动完成时间为:{}", mainTempPage.action.go2GetTimeDiffElementShow(mainTempPage.rollKeyword));
	}

	@Tips(description = "启动到首页|迁移到PageLoadingCollect")
	public void bootstrap2Main() {
		LogUtil.printCurrentMethodNameInLog4J();
		mSplashPage.action.go2SoftReset();
		String rollKeywordUiSelector = "new UiSelector().className(\"android.widget.LinearLayout\").resourceId(\"com.cmic.mmnes:id/search_layout\").childSelector(new UiSelector().className(\"android.widget.TextView\"))";
		long beforeTime = System.currentTimeMillis();
		AndroidDriverWait wait = new AndroidDriverWait(mDriver, 15, 100);
		try {
			wait.until(new ExpectedCondition4AndroidDriver<Boolean>() {
				@Override
				public Boolean apply(AndroidDriver<AndroidElement> arg0) {
					AndroidElement e = arg0.findElementByAndroidUIAutomator(rollKeywordUiSelector);
					long afterTime = System.currentTimeMillis();
					if (e != null)
						LogUtil.i("启动完成时间为:{}", EssentialUtil.getTheTimeDiff(beforeTime, afterTime));
					return true;
				}
			});
		} catch (Exception e) {
			LogUtil.e("元素寻找超时(15秒)");
		}
	}

	@Test(enabled = false)
	public void bootstrapWithoutWithoutResetApp() {

	}

	@Test(enabled = false)
	public void bootstrapInBackground() {

	}
}
