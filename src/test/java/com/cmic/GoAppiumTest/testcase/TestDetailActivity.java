package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Heading;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 详情页面
 * 
 * @author kiwi
 *
 */
public class TestDetailActivity {
	private String mTag;
	private AndroidDriver<AndroidElement> mDriver;

	private String mTempItemName;

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
	@Tips(description = "从主页进入,模拟一个随机位置", riskPoint = "主页未显示")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		PageRedirect.redirect2MainActivity();
		Random randomIndex = new Random();// 主页显示16个Item
		int index = 1 + randomIndex.nextInt(14);
		System.out.println(index);
		// 定位点击
		WaitUtil.implicitlyWait(5);
		List<AndroidElement> eList = mDriver.findElements(By.id("com.cmic.mmnes:id/index_item_rl"));
		AndroidElement e = eList.get(index);
		WaitUtil.implicitlyWait(2);
		mTempItemName = mDriver.findElements(By.id("com.cmic.mmnes:id/recommend_item_appname_tv")).get(index).getText();
		System.out.println(mTempItemName);
		e.click();
		System.out.println("测试用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {// 执行一些初始化操作
		System.out.println("测试用例集[" + mTag + "]结束");
	}

	@Test
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.DetailActivity");
		ScreenUtil.screenShot("进入必备详情页面");
		WaitUtil.implicitlyWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkActionBarSearch() throws InterruptedException {
		mDriver.findElement(By.id("com.cmic.mmnes:id/search_iv")).click();
		// 是否进入SearchActivity页面
		WaitUtil.forceWait(2);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SearchActivity");
		PageRouteUtil.pressBack();
		WaitUtil.forceWait(1);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.DetailActivity");
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkTheImageScrollView() throws InterruptedException {
		ElementUtil.swipeControl(By.id("com.cmic.mmnes:id/horizontal_scroll_view"), Heading.LEFT);
		WaitUtil.forceWait(2);
		ScreenUtil.screenShot("滑动详情页广告图");
		ElementUtil.swipeControl(By.id("com.cmic.mmnes:id/horizontal_scroll_view"), Heading.RIGHT);
		WaitUtil.forceWait(2);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void checkImageBrower() throws InterruptedException {
		List<AndroidElement> imageList = mDriver.findElements(By.id("com.cmic.mmnes:id/recycledImageView"));
		if (imageList.size() > 0) {
			imageList.get(0).click();// 预期进入ImageBrowerActivity
			WaitUtil.forceWait(2);
			assertEquals(ContextUtil.getCurrentActivity(), ".activity.ImageBrowseActivity");
		}
	}

	@Test(dependsOnMethods = { "checkImageBrower" })
	@Tips(description = "检查ImageBrowerActivity进行操作")
	public void opearaInImageBrower() {
		// TODO 一次尝试
		try {
			ScrollUtil.scrollToPrecent(Direction.LEFT, 80);
			WaitUtil.forceWait(1);
			ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
			WaitUtil.forceWait(1);
			ScreenUtil.zoom(mDriver.findElement(By.className("android.widget.ImageView")));
			WaitUtil.forceWait(1);
			ScreenUtil.doubleRandomTap();// 点击退出
			WaitUtil.forceWait(1);
			assertEquals(ContextUtil.getCurrentActivity().equals(".activity.ImageBrowseActivity"), false);
		} catch (Exception e) {
			PageRouteUtil.pressBack();
			throw new AssertionError();
		}
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查体验免安装", riskPoint = "可能不存在")
	public void checkWithoutInstall() throws InterruptedException {
		if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/videoImgLayout"))) {
			AndroidElement eWithoutInstall = mDriver.findElement(By.id("com.cmic.mmnes:id/videoImgLayout"));
			eWithoutInstall.click();
			WaitUtil.forceWait(2);
			assertEquals(ContextUtil.getCurrentActivity(), ".activity.FavorActivity");
			mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
			WaitUtil.forceWait(2);
		}
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查应用简介", riskPoint = "点击控件可能不在界面之内")
	public void checkBriefIntroduction() {
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

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查福利Item", riskPoint = "可能不存在")
	public void checkAdInDetail() throws InterruptedException {
		if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/detail_benefit_layout"))) {
			mDriver.findElement(By.id("com.cmic.mmnes:id/detail_benefit_layout")).click();
			WaitUtil.forceWait(2);
			assertEquals(ContextUtil.getCurrentActivity(), ".activity.FavorActivity");
			mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
			WaitUtil.forceWait(2);
		}
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查其他人正在装")
	public void checkOtherInstall() throws InterruptedException {
		String otherInstallItemUiSelector = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/app_name\")";
		List<AndroidElement> eList = mDriver.findElementsByAndroidUIAutomator(otherInstallItemUiSelector);
		int eListSize;
		if ((eListSize = eList.size()) > 0) {
			AndroidElement randomItem = eList.get(RandomUtil.getRandomNum(eListSize - 1));
			String tempAppName = randomItem.getText();
			randomItem.click();
			WaitUtil.implicitlyWait(3);
			String nextDetailAppName = mDriver.findElement(By.id("com.cmic.mmnes:id/title_tv")).getText();
			assertEquals(nextDetailAppName.equals(tempAppName), true);
			PageRouteUtil.pressBack();
		}
	}

	@Tips(description = "点击其他人正在安装的下载按钮")
	public void checkDownloadInOtherInstall() throws InterruptedException {
		String statusBtnUiSelector = "new UiSelector().className(\"android.widget.TextView\").textContains(\"下载\").resourceId(\"com.cmic.mmnes:id/status_btn\")";
		List<AndroidElement> downloadBtnList = mDriver.findElementsByAndroidUIAutomator(statusBtnUiSelector);
		int eListSize;
		if ((eListSize = downloadBtnList.size()) > 0) {
			AndroidElement targetElement = downloadBtnList.get(RandomUtil.getRandomNum(downloadBtnList.size() - 1));
			assertEquals(targetElement.getText(), "下载");
			targetElement.click();
			// TODO 网速判断
			// 开始下载
			mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
			WaitUtil.forceWait(2);
			assertEquals(targetElement.getText(), "暂停");
			// 暂停下载
			targetElement.click();
			WaitUtil.forceWait(1);
			assertEquals(targetElement.getText(), "继续");
			// TODO 不稳定待日后完善
		}
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "检查权限")
	public void checkPermission() throws InterruptedException {
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/quanxian"));
		e.click();
		WaitUtil.forceWait(3);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.AppPermissionActivity");
		// 测试滑动
		ScrollUtil.scrollToBase();
		WaitUtil.forceWait(2);
		// 回退
		mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.DetailActivity");

	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "测试举报")
	public void checkTipOff() throws InterruptedException {
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/jubao"));
		e.click();
		WaitUtil.forceWait(3);
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.AppReportActivity");
	}

	@Test(dependsOnMethods = { "checkTipOff" }, enabled = false)
	@Tips(description = "测试举报页面", riskPoint = "造成脏数据|遍历深度浅")
	public void checkPostTipOffReport() {
		// TODO
		// 先进行点击
		qiuckToastCheck();
		// 填写内容
		mDriver.findElement(By.id("com.cmic.mmnes:id/report_type_layout")).click();
		WaitUtil.implicitlyWait(2);
		mDriver.findElement(By.xpath(
				"//android.widget.ListView[@resource-id='com.cmic.mmnes:id/report_listview']/android.widget.LinearLayout[7]"));
		WaitUtil.implicitlyWait(2);
		// 填写举报内容
		AndroidElement reportContent = mDriver.findElement(By.id("com.cmic.mmnes:id/report_edittext"));
		reportContent.clear();
		reportContent.sendKeys("[测试举报功能，请忽略！]我是卓望公司的终端适配测试人员");
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
		String reportPostErrorToast = "请完成所有的必填项内容后天再提交";
		Assert.assertEquals(ElementUtil.isTargetToast(reportPostErrorToast), false);
		// 回退
		mDriver.findElement(By.id("com.cmic.mmnes:id/back_iv")).click();
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.DetailActivity");
	}

	private void qiuckToastCheck() {
		mDriver.findElement(By.id("com.cmic.mmnes:id/submit_button")).click();
		String reportPostErrorToast = "请完成所有的必填项内容后天再提交";
		Assert.assertEquals(ElementUtil.isTargetToast(reportPostErrorToast), true);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "测试下载|不同网络状态和Notice级别可能存在影响")
	public void checkBottomDownload() throws InterruptedException {
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/install_btn"));
		assertEquals(e.getText().contains("下载"), true);
		e.click();
		WaitUtil.forceWait(2);
		// 如果实在移动网络的情况下
		// 开始下载
		mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
		WaitUtil.forceWait(1);
		assertEquals(e.getText().contains("暂停"), true);
		// 暂停下载
		e.click();
		WaitUtil.forceWait(1);
		assertEquals(e.getText().contains("继续"), true);
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	@Tips(description = "测试下载计数")
	public void checkRightTopNumTip() {
		AndroidElement e = mDriver.findElement(By.id("com.cmic.mmnes:id/update_point_iv"));
		assertEquals(e.getText().equals(""), true);
	}

}
