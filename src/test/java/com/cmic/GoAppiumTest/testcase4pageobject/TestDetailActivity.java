package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Heading;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.RandomUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;

/**
 * 详情页面
 * 
 * @author kiwi
 *
 */
@Listeners(ExtentReportListener.class)
public class TestDetailActivity extends BaseTest {

	private String mTempItemName;

	@Tips(description = "继承自BaseActivity,用于增强@BeforeClass", triggerTime = "假设已经入首页且显示正常，开始准备跳转到详情页")
	@Override
	public void setUpBeforeClass() {
		PageRedirect.redirect2MainActivity();
		Random randomIndex = new Random();// 主页显示16个Item
		int index = 1 + randomIndex.nextInt(14);
		// 定位点击
		WaitUtil.implicitlyWait(10);
		List<AndroidElement> eList = mDriver.findElements(By.id("com.cmic.mmnes:id/index_item_rl"));
		AndroidElement e = eList.get(index);
		WaitUtil.implicitlyWait(2);
		mTempItemName = mDriver.findElements(By.id("com.cmic.mmnes:id/recommend_item_appname_tv")).get(index).getText();
		e.click();
	}

	@Tips(description = "继承自BaseActivity,用于增强@AfterClass")
	@Override
	public void tearDownAfterClass() {
		//
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		System.err.println("进行[" + getClass().getSimpleName() + "]用例集的初始化检验，失败则跳过该用例集的所有测试");
		assertEquals(getCurrentPageName(), "DetailActivity");
		ScreenUtil.screenShot("进入必备详情页面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkActionBarSearch() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/search_iv"));
		e.click();
		// 是否进入SearchActivity页面
		WaitUtil.forceWait(2);
		assertEquals(getCurrentPageName(), "SearchActivity");
		PageRouteUtil.pressBack();
		WaitUtil.forceWait(1);
		assertEquals(getCurrentPageName(), "DetailActivity");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkTheImageScrollView() throws InterruptedException {
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/horizontal_scroll_view"));
		LogUtil.printCurrentMethodNameInLog4J();
		ElementUtil.swipeControl(e, Heading.LEFT);
		WaitUtil.forceWait(2);
		ScreenUtil.screenShot("滑动详情页广告图");
		ElementUtil.swipeControl(e, Heading.RIGHT);
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkImageBrower() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		List<AndroidElement> imageList = mDriver.findElements(By.id("com.cmic.mmnes:id/recycledImageView"));
		if (imageList.size() > 1) {
			imageList.get(1).click();// 预期进入ImageBrowerActivity,避免取到免安装
			WaitUtil.forceWait(2);
			assertEquals(getCurrentPageName(), "ImageBrowseActivity");
		}
		PageRouteUtil.pressBack();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查ImageBrowerActivity进行操作")
	public void opearaInImageBrower() throws InterruptedException {
		// TODO 一次尝试
		LogUtil.printCurrentMethodNameInLog4J();
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		List<AndroidElement> imageList = mDriver.findElements(By.id("com.cmic.mmnes:id/recycledImageView"));
		if (imageList.size() > 1) {
			// TODO 0时容易出错
			imageList.get(1).click();// 预期进入ImageBrowerActivity
			WaitUtil.forceWait(2);
			assertEquals(getCurrentPageName(), "ImageBrowseActivity");
		}
		try {
			ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
			WaitUtil.forceWait(1);
			ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
			WaitUtil.forceWait(1);
			// TODO 暂时关闭双击退出和缩放的途径
			// ScreenUtil.zoom(mDriver.findElement(By.className("android.widget.ImageView")));
			// WaitUtil.forceWait(1);
			// ScreenUtil.doubleRandomTap();// 点击退出
			PageRouteUtil.pressBack();
			WaitUtil.forceWait(1);
			assertEquals(getCurrentPageName().equals("ImageBrowseActivity"), false);
		} catch (Exception e) {
			PageRouteUtil.pressBack();
			throw new AssertionError();
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查体验免安装", riskPoint = "可能不存在")
	public void checkWithoutInstall() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/videoImgLayout"))) {
			AndroidElement eWithoutInstall = mDriver.findElement(By.id("com.cmic.mmnes:id/videoImgLayout"));
			eWithoutInstall.click();
			WaitUtil.forceWait(2);
			assertEquals(getCurrentPageName(), "FavorActivity");
			mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
			WaitUtil.forceWait(2);
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查应用简介", riskPoint = "点击控件可能不在界面之内")
	public void checkBriefIntroduction() {
		if (!getCurrentPageName().equals("DetailActivity")) {
			System.err.println("页面异常");
		}
		LogUtil.printCurrentMethodNameInLog4J();
		ScrollUtil.scrollToBase();
		String str1 = mDriver.getPageSource();
		if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/bottom_mark_tv"))) {
			mDriver.findElement(By.id("com.cmic.mmnes:id/bottom_mark_tv")).click();
			String str2 = mDriver.getPageSource();
			assertEquals(str1.equals(str2), false);
			ScrollUtil.scrollToBase();
			// 恢复收起状态
			mDriver.findElement(By.id("com.cmic.mmnes:id/bottom_mark_tv")).click();
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查福利Item", riskPoint = "可能不存在")
	public void checkAdInDetail() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/detail_benefit_layout"))) {
			mDriver.findElement(By.id("com.cmic.mmnes:id/detail_benefit_layout")).click();
			WaitUtil.forceWait(2);
			assertEquals(getCurrentPageName(), "FavorActivity");
			mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
			WaitUtil.forceWait(2);
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查其他人正在装")
	public void checkOtherInstall() throws InterruptedException {
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		String otherInstallItemUiSelector = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/app_name\")";
		LogUtil.printCurrentMethodNameInLog4J();
		List<AndroidElement> eList = mDriver.findElementsByAndroidUIAutomator(otherInstallItemUiSelector);
		int eListSize;
		if ((eListSize = eList.size()) > 0) {
			AndroidElement randomItem = eList.get(RandomUtil.getRandomNum(eListSize - 1));
			String tempAppName = randomItem.getText();
			randomItem.click();
			WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
			String nextDetailAppName = mDriver.findElement(By.id("com.cmic.mmnes:id/detail_appname_tv")).getText();
			assertEquals(nextDetailAppName.equals(tempAppName), true);
			PageRouteUtil.pressBack();
		}
	}

	// TODO 待修复
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击其他人正在安装的下载按钮")
	public void checkDownloadInOtherInstall() throws InterruptedException {
		// TODO 测试后删除
		ScrollUtil.scrollToBase();
		LogUtil.printCurrentMethodNameInLog4J();
		//
		String statusBtnUiSelector = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")";
		WaitUtil.implicitlyWait(5);
		List<AndroidElement> downloadBtnList = mDriver.findElementsByAndroidUIAutomator(statusBtnUiSelector);
		int eListSize;
		if ((eListSize = downloadBtnList.size()) > 0) {
			int randomIndex = RandomUtil.getRandomNum(eListSize - 1);
			AndroidElement e = downloadBtnList.get(randomIndex);
			assertEquals(e.getText(), "下载");
			e.click();
			// TODO 新增
			WaitUtil.forceWait(1);
			e.click();// TODO 新增，马上点击停止
			WaitUtil.forceWait(0.5);
			if (!e.getText().contains("继续")) {
				if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/mm_down_goon"))) {
					mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
					WaitUtil.forceWait(1);
					e.click();
					WaitUtil.forceWait(0.5);
				}
			}
			// 如果实在移动网络的情况下
			// 开始下载
			assertEquals(e.getText().contains("继续"), true);
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查权限")
	public void checkPermission() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/quanxian"));
		e.click();
		WaitUtil.forceWait(3);
		assertEquals(getCurrentPageName(), "AppPermissionActivity");
		// 测试滑动
		ScrollUtil.scrollToBase();
		WaitUtil.forceWait(2);
		// 回退
		mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
		assertEquals(getCurrentPageName(), "DetailActivity");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试举报")
	public void checkTipOff() throws InterruptedException {
		if (getCurrentPageName().equals("DetailActivity")) {
			ScrollUtil.scrollToBase();
		}
		WaitUtil.implicitlyWait(5);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/jubao"));
		LogUtil.printCurrentMethodNameInLog4J();
		e.click();
		WaitUtil.forceWait(3);
		assertEquals(getCurrentPageName(), "AppReportActivity");
		PageRouteUtil.pressBack();
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "测试举报页面", riskPoint = "造成脏数据|遍历深度浅")
	public void checkPostTipOffReport() throws InterruptedException {
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/jubao"));
		LogUtil.printCurrentMethodNameInLog4J();
		e.click();
		WaitUtil.forceWait(3);
		assertEquals(getCurrentPageName(), "AppReportActivity");
		// 先进行点击
		//
		mDriver.findElement(By.id("com.cmic.mmnes:id/submit_button")).click();
		String reportPostErrorToast = "请完成所有的必填项内容后再提交";
		Assert.assertEquals(ElementUtil.isTargetToast(reportPostErrorToast), true);
		// 填写内容
		mDriver.findElement(By.id("com.cmic.mmnes:id/report_type_layout")).click();
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		mDriver.findElement(By.xpath(
				"//android.widget.ListView[@resource-id='com.cmic.mmnes:id/report_listview']/android.widget.LinearLayout[7]"))
				.click();
		WaitUtil.implicitlyWait(App.WAIT_TIME_IMPLICITLY);
		// 填写举报内容
		AndroidElement reportContent = mDriver.findElement(By.id("com.cmic.mmnes:id/report_edittext"));
		reportContent.clear();
		reportContent.sendKeys("[测试举报功能，请忽略！我是测试人员");
		// 填写手机号
		AndroidElement reportPhone = mDriver.findElement(By.id("com.cmic.mmnes:id/phonenumber_edittext"));
		reportPhone.clear();
		reportPhone.sendKeys("18814127364");
		// 填写电子邮箱
		AndroidElement reportEmail = mDriver.findElement(By.id("com.cmic.mmnes:id/email_edittext"));
		reportEmail.clear();
		reportEmail.sendKeys("18814127364@139.com");
		// 点击提交
		mDriver.findElement(By.id("com.cmic.mmnes:id/submit_button")).click();
		Assert.assertEquals(ElementUtil.isTargetToast(reportPostErrorToast), false);
		WaitUtil.forceWait(0.5);
		if (getCurrentPageName().equals("DetailActivity")) {

		} else {
			// 回退
			mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
			assertEquals(getCurrentPageName(), "DetailActivity");
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试下载计数")
	public void checkRightTopNumTip() {
		WaitUtil.implicitlyWait(5);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/update_point_iv"));
		LogUtil.printCurrentMethodNameInLog4J();
		assertEquals(e.getText().equals(""), true);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试下载|不同网络状态和Notice级别可能存在影响")
	public void checkBottomDownload() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/install_btn"));
		LogUtil.printCurrentMethodNameInLog4J();
		assertEquals(e.getText().contains("下载"), true);
		e.click();
		WaitUtil.forceWait(1);
		e.click();// TODO 新增，马上点击停止
		WaitUtil.forceWait(0.5);
		if (!e.getText().contains("继续")) {
			if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/mm_down_goon"))) {
				mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
				WaitUtil.forceWait(1);
				e.click();
				WaitUtil.forceWait(0.5);
			}
		}
		// 如果实在移动网络的情况下
		// 开始下载
		assertEquals(e.getText().contains("继续"), true);
	}
}
