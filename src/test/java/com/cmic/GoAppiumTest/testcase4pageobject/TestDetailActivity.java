package com.cmic.GoAppiumTest.testcase4pageobject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.ExtentReportListener;
import com.cmic.GoAppiumTest.helper.Heading;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.DetailPage;
import com.cmic.GoAppiumTest.page.action.DetailAction;
import com.cmic.GoAppiumTest.page.middlepage.PermissionDetailOfDetailPage;
import com.cmic.GoAppiumTest.page.middlepage.PostPage;
import com.cmic.GoAppiumTest.page.middlepage.PostPage.PostAction;
import com.cmic.GoAppiumTest.testcase.retry.FailRetry;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;
import io.appium.java_client.android.Connection;

/**
 * 详情页面
 * 
 * @author kiwi
 *
 */
@Listeners(ExtentReportListener.class)
public class TestDetailActivity extends BaseTest {

	private DetailPage detailPage;
	private PostPage postPage;
	//
	private DetailAction detailAction;
	private PostAction postAction;

	@Tips(description = "继承自BaseActivity,用于增强@BeforeClass", triggerTime = "假设已经入首页且显示正常，开始准备跳转到详情页")
	@Override
	public void setUpBeforeClass() {
		detailPage = new DetailPage();
		detailAction = (DetailAction) detailPage.action;
		detailAction.go2SelfPage();
	}

	@Tips(description = "继承自BaseActivity,用于增强@AfterClass")
	@Override
	public void tearDownAfterClass() {
		// TODO
	}

	@Test(retryAnalyzer = FailRetry.class)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		LogUtil.w("进行[{}]用例集的初始化检验，失败则跳过该用例集的所有测试", getClass().getSimpleName());
		assertEquals(getCurrentPageName(), "DetailActivity");
		detailPage.snapScreen("进入必备详情页面");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkActionBarSearch() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		detailAction.go2ClickAndWait(detailPage.searchLLy, 1);
		// 是否进入SearchActivity页面
		assertEquals(getCurrentPageName(), "SearchActivity");
		detailAction.go2Backforward();
		assertEquals(getCurrentPageName(), "DetailActivity");
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkTheImageScrollView() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		detailAction.go2ClickAndWait(detailPage.imageAdScorll, 1.5);
		detailAction.go2SwipeInElement(detailPage.imageAdScorll, Heading.LEFT);
		ScreenUtil.screenShot("滑动详情页广告图");
		detailAction.go2SwipeInElement(detailPage.imageAdScorll, Heading.RIGHT);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void checkImageBrower() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		detailPage.go2WithoutInstall();// 进入免安装页面
		assertEquals(getCurrentPageName(), "ImageBrowseActivity");
		detailPage.action.go2Backforward();
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查ImageBrowerActivity进行操作")
	public void opearaInImageBrower() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		detailPage.go2WithoutInstall();// 进入免安装页面
		assertEquals(getCurrentPageName(), "ImageBrowseActivity");
		try {
			// 滑动
			detailAction.go2SwipeFullScreen(Direction.LEFT, 80);
			detailAction.go2SwipeFullScreen(Direction.RIGHT, 80);
			// TODO 暂时关闭双击退出和缩放的途径
			// ScreenUtil.zoom(mDriver.findElement(By.className("android.widget.ImageView")));
			// WaitUtil.forceWait(1);
			// ScreenUtil.doubleRandomTap();// 点击退出
			go2Backforward();// 无状态页面后退
			assertNotEquals(getCurrentPageName(), "ImageBrowseActivity");
		} catch (Exception e) {
			go2Backforward();// 无状态页面后退
			throw new AssertionError();
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查体验免安装", riskPoint = "可能不存在")
	public void checkWithoutInstall() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		if (detailPage.isElementShown(detailPage.h5Ad)) {
			detailAction.go2ClickAndWait(detailPage.h5Ad, 2);
			assertEquals(getCurrentPageName(), "FavorActivity");
			go2Backforward();// 无状态页面后退
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查应用简介", riskPoint = "点击控件可能不在界面之内")
	public void checkBriefIntroduction() {
		LogUtil.printCurrentMethodNameInLog4J();
		if (!getCurrentPageName().equals("DetailActivity"))
			LogUtil.e("页面异常");//
		detailAction.go2Swipe2Bottom();//
		String str1 = detailAction.go2GetPageResource();
		if (detailPage.isElementShown(detailPage.appMarkDetail)) {// TODO
			detailAction.go2Click(detailPage.appMarkDetail);
			assertNotEquals(str1, detailAction.go2GetPageResource());
			detailAction.go2Swipe2Bottom();
			// 恢复收起状态
			detailAction.go2Click(detailPage.appMarkDetail);//
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查福利Item", riskPoint = "可能不存在")
	public void checkAdInDetail() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		if (detailPage.isElementShown(detailPage.benifitLly)) {
			detailAction.go2ClickAndWait(detailPage.benifitLly, 2);
			assertEquals(getCurrentPageName(), "FavorActivity");
			go2Backforward();// 无状态页面后退
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查其他人正在装")
	public void checkOtherInstall() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		detailPage.goRandomClick2DetailInOtherInstall();
		detailAction.go2Backforward();
	}

	// TODO 待修复
	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击其他人正在安装的下载按钮")
	public void checkDownloadInOtherInstall() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		detailAction.go2Swipe2Bottom();
		// 开始检查网络
		Connection temp = detailAction.go2GetNetWorkStatus();
		LogUtil.w("当前网络状态为{}", temp.name());
		boolean isDataStatu = (temp == Connection.DATA);// 移动数据网络状态
		detailPage.go2RandomStartDownloadOtherInstall();
		if (!isDataStatu) {// 不是移动网络状态//WIFI
			detailPage.go2RandomPauseDownloadOtherInstall();// 马上点击停止，防止进入安装界面
			WaitUtil.forceWait(0.5);
		} else {// 移动网络状态//DATA//CANTUSE
			LogUtil.w("由于处于移动网络，进入弹窗提示页面");
			detailPage.go2RandomStartDownloadOtherInstall();
			detailPage.forceWait(1);
			// 再点击暂停
			detailPage.go2RandomPauseDownloadOtherInstall();// 马上点击停止，防止进入安装界面
		}
		assertEquals(detailPage.getRandomTargetText().contains("继续"), true);
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "检查权限")
	public void checkPermission() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		detailAction.go2ClickAndWait(detailPage.btnPermissino, 2);
		// 断言
		assertEquals(getCurrentPageName(), "AppPermissionActivity");
		PermissionDetailOfDetailPage permissionDetailPage = new PermissionDetailOfDetailPage();
		// 测试滑动
		permissionDetailPage.action.go2Swipe2Bottom();
		// 回退
		permissionDetailPage.action.go2Backforward();
		assertEquals(getCurrentPageName(), "DetailActivity");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试举报")
	public void checkTipOff() throws InterruptedException {
		if (getCurrentPageName().equals("DetailActivity")) {// 检查依赖导致的页面紊乱
			ScrollUtil.scrollToBase();
		}
		detailAction.go2ClickAndWait(detailPage.btnPost, 2);
		LogUtil.printCurrentMethodNameInLog4J();
		assertEquals(getCurrentPageName(), "AppReportActivity");
		postPage = new PostPage();
		postAction = (PostAction) postPage.action;
		postAction.go2Backforward();
	}

	@Test(dependsOnMethods = { "initCheck" }, retryAnalyzer = FailRetry.class)
	@Tips(description = "测试举报页面", riskPoint = "造成脏数据|遍历深度浅")
	public void checkPostTipOffReport() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		detailAction.go2ClickAndWait(detailPage.btnPost, 2);
		assertEquals(getCurrentPageName(), "AppReportActivity");
		// 先进行点击
		postAction.go2Click(postPage.btnSubmit);
		String reportPostErrorToast = "请完成所有的必填项内容后再提交";
		Assert.assertEquals(postPage.isTargetToast(reportPostErrorToast), true);
		// 填写内容
		postAction.go2ClickAndWait(postPage.btnReportTypeSelect, 1.5);
		postAction.go2ClickAndWait(postPage.targetTypeItem, 0.5);
		// 填写举报内容
		postAction.go2SendWord(postPage.etReportInfo, "[测试举报功能，请忽略！我是测试人员");
		// 填写手机号
		postAction.go2SendWord(postPage.etPhoneNum, "18814127364");
		// 填写电子邮箱
		postAction.go2SendWord(postPage.etEMailInfo, "18814127364@139.com");
		// 点击提交
		postAction.go2Click(postPage.btnSubmit);
		Assert.assertEquals(postPage.isTargetToast(reportPostErrorToast), false);
		postPage.forceWait(0.5);
		if (!getCurrentPageName().equals("DetailActivity")) {
			// 回退
			postAction.go2Backforward();
			assertEquals(getCurrentPageName(), "DetailActivity");
		}
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试下载计数")
	public void checkRightTopNumTip() {
		LogUtil.printCurrentMethodNameInLog4J();
		WaitUtil.implicitlyWait(5);
		assertEquals(detailPage.tvPointNum, "");
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "测试下载|不同网络状态和Notice级别可能存在影响")
	public void checkBottomDownload() throws InterruptedException {
		LogUtil.printCurrentMethodNameInLog4J();
		assertEquals(detailPage.btnInstall, "下载");
		// 开始检查网络
		Connection temp = detailAction.go2GetNetWorkStatus();
		LogUtil.w("当前网络状态为{}", temp.name());
		boolean isDataStatu = (temp == Connection.DATA);// 移动数据网络状态
		detailAction.go2Click(detailPage.btnInstall);
		if (!isDataStatu) {// 不是移动网络状态//WIFI
			detailAction.go2ClickAndWait(detailPage.btnInstall, 0.5);// 马上点击停止，防止进入安装界面
		} else {// 移动网络状态//DATA//CANTUSE
			LogUtil.w("由于处于移动网络，进入弹窗提示页面");
			detailAction.go2ClickAndWait(detailPage.btnInstall, 0.5);
			// 再点击暂停
			detailAction.go2ClickAndWait(detailPage.btnInstall, 0.5);// 马上点击停止，防止进入安装界面
		}
		assertEquals(detailPage.getRandomTargetText().contains("继续"), true);
	}
}
