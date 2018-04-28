package com.cmic.GoAppiumTest.page;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.DetailAction;
import com.cmic.GoAppiumTest.page.action.SplashAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class DetailPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/search_iv")
	public AndroidElement searchLLy;// 详情页ActionBar搜索框

	@AndroidFindBy(id = "com.cmic.mmnes:id/horizontal_scroll_view")
	public AndroidElement imageAdScorll;// 图片广告画廊

	@AndroidFindBy(id = "com.cmic.mmnes:id/recycledImageView")
	public List<AndroidElement> imageAdList;// 单个广告

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/app_name\")")
	public List<AndroidElement> hotInstall;// 大家都在装的软件列表

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")")
	public List<AndroidElement> hotInstallStartBtn;// 大家都在装的下载按钮列表

	@AndroidFindBy(id = "com.cmic.mmnes:id/detail_appname_tv")
	public AndroidElement targetHotInstallAppName;// 具体选中的大家都在 搜游戏名称

	@AndroidFindBy(id = "com.cmic.mmnes:id/videoImgLayout")
	public AndroidElement h5Ad;

	@AndroidFindBy(id = "com.cmic.mmnes:id/bottom_mark_tv")
	public AndroidElement appMarkDetail;// 应用详情简介、

	@AndroidFindBy(id = "com.cmic.mmnes:id/detail_benefit_layout")
	public AndroidElement benifitLly;// 福利详情

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_down_goon")
	public AndroidElement goOnDownload;// 继续下载

	@AndroidFindBy(id = "com.cmic.mmnes:id/quanxian")
	public AndroidElement btnPermissino;// 权限

	@AndroidFindBy(id = "com.cmic.mmnes:id/jubao")
	public AndroidElement btnPost;// 举报按钮

	// ++++++++
	@AndroidFindBy(id = "com.cmic.mmnes:id/submit_button")
	public AndroidElement btnSubmit;// 提交举报

	@AndroidFindBy(id = "com.cmic.mmnes:id/report_type_layout")
	public AndroidElement btnReportTypeSelect;// 举报类型选择

	@AndroidFindBy(xpath = "//android.widget.ListView[@resource-id='com.cmic.mmnes:id/report_listview']/android.widget.LinearLayout[7]")
	public AndroidElement targetTypeItem;// 目标类型

	@AndroidFindBy(id = "com.cmic.mmnes:id/report_edittext")
	public AndroidElement etReportInfo;

	@AndroidFindBy(id = "com.cmic.mmnes:id/phonenumber_edittext")
	public AndroidElement etPhoneNum;

	@AndroidFindBy(id = "com.cmic.mmnes:id/email_edittext")
	public AndroidElement etEMailInfo;// 邮箱信息
	// ++++++++++++++

	@AndroidFindBy(id = "com.cmic.mmnes:id/update_point_iv")
	public AndroidElement tvPointNum;// 下载计数

	@AndroidFindBy(id = "com.cmic.mmnes:id/install_btn")
	public AndroidElement btnInstall;// 底部安装按钮

	public DetailPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new DetailAction();
	}
}
