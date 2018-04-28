package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.SettingAction;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SettingPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/setting_zero_layout")
	public AndroidElement llyAutoUpdateSetting;// 进行子更新设置的条目

	@AndroidFindBy(id = "com.cmic.mmnes:id/flow_seekbar")
	public AndroidElement sbDownloadTip;// 下载提示Seekbar

	@AndroidFindBy(id = "com.cmic.mmnes:id/setting_download_notice_layout")
	public AndroidElement llyDownloadTipDialog;// 进行下载设置的条目

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_ok")
	public AndroidElement btnDownloadTipDialogAccept;// 接收下载设置提示设置弹窗的设置

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_cancel")
	public AndroidElement btnCancelDownloadTipDialog;// 取消下载设置提示设置弹窗的设置

	@AndroidFindBy(id = "com.cmic.mmnes:id/close_iv")
	public AndroidElement btnCloseDownloadTipDialog;// 关闭下载提示设置的弹窗

	@AndroidFindBy(id = "com.cmic.mmnes:id/flow_setting_et")
	public AndroidElement etDownloadTip;// 流量提示设置的输入框

	@AndroidFindBy(id = "com.cmic.mmnes:id/setting_download_notice_content")
	public AndroidElement tvDownloadTipNum;// 下载提示设置的限制数

	public SettingPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new SettingAction();
	}

	public void go2ShowDownloadSettingTip() {
		action.go2Click(llyDownloadTipDialog);
		forceWait(2);
	}

	public void go2TapTheHalfSeekbar() {
		// 获取控件开始位置的坐标轴
		Point start = sbDownloadTip.getLocation();
		int startX = start.x;
		int startY = start.y;
		// 获取控件宽高
		Dimension q = sbDownloadTip.getSize();
		int x = q.getWidth();
		int y = q.getHeight();
		// 计算出控件结束坐标
		int endX = x + startX;
		int endY = y + startY;
		// 计算中间点坐标
		int centreX = (endX + startX) / 2;
		int centreY = (endY + startY) / 2;
		// 点击
		action.go2TapByPoint(centreX, centreY);
		WaitUtil.forceWait(1);
	}
}
