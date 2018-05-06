package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.SettingAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SettingPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/setting_zero_layout")
	public AndroidElement llyAutoUpdateSetting;// 进行子更新设置的条目

	@AndroidFindBy(id = "com.cmic.mmnes:id/setting_download_notice_layout")
	public AndroidElement llyDownloadTipDialog;// 进行下载设置的条目

	@AndroidFindBy(id = "com.cmic.mmnes:id/setting_download_notice_content")
	public AndroidElement tvDownloadTipNum;// 下载提示设置的限制数

	@AndroidFindBy(id = "com.cmic.mmnes:id/rl_share")
	public AndroidElement llyShare;// 下载提示设置的限制数

	public SettingPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new SettingAction();
	}

	public void go2ShowDownloadSettingTip() {
		action.go2Click(llyDownloadTipDialog);
		forceWait(2);
	}

	public void go2SharePage() {
		action.go2Click(llyShare);
		forceWait(1.5);
	}
}
