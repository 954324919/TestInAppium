package com.cmic.GoAppiumTest.page;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.DownloadManagerAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class DownloadManagerPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/app_name")
	public List<AndroidElement> targetPageItemList;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")")
	public List<AndroidElement> targetUpdateOrDownItemList;// 下载或更新的按钮

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"下载\")")
	public AndroidElement eDownloadTab;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"更新\")")
	public AndroidElement eUpdateTab;

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_down_goon")
	public AndroidElement btnDownloadGoOn; // 土豪继续下载

	@AndroidFindBy(id = "com.cmic.mmnes:id/updateall_tx")
	public AndroidElement btnAllUpdate;// 全部更新

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_cancel")
	public AndroidElement btnCancelUpdate;// 取消更新

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_ok")
	public AndroidElement btnAcceptUpdate;// 确定更新

	@AndroidFindBy(id = "com.cmic.mmnes:id/function_tv")
	public AndroidElement btnAllOperate;// 全部操作
	
	@AndroidFindBy(id = "com.cmic.mmnes:id/delete_btn")
	public AndroidElement btnDeleteDownload;// 取消下载
	
	public DownloadManagerPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new DownloadManagerAction();
	}
}