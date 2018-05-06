package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

public class DownloadTipDialogPage extends BasePage {

	@WithTimeout(time = 2, unit = TimeUnit.SECONDS) // 调用原生比较快，两秒不显示判断为超时
	@AndroidFindBy(id = "com.cmic.mmnes:id/flow_seekbar")
	public AndroidElement sbDownloadTip;// 下载提示Seekbar

	@WithTimeout(time = 2, unit = TimeUnit.SECONDS) // 调用原生比较快，两秒不显示判断为超时
	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_ok")
	public AndroidElement btnDownloadTipDialogAccept;// 接收下载设置提示设置弹窗的设置

	@WithTimeout(time = 2, unit = TimeUnit.SECONDS) // 调用原生比较快，两秒不显示判断为超时
	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_cancel")
	public AndroidElement btnCancelDownloadTipDialog;// 取消下载设置提示设置弹窗的设置

	@WithTimeout(time = 2, unit = TimeUnit.SECONDS) // 调用原生比较快，两秒不显示判断为超时
	@AndroidFindBy(id = "com.cmic.mmnes:id/close_iv")
	public AndroidElement btnCloseDownloadTipDialog;// 关闭下载提示设置的弹窗

	@WithTimeout(time = 2, unit = TimeUnit.SECONDS) // 调用原生比较快，两秒不显示判断为超时
	@AndroidFindBy(id = "com.cmic.mmnes:id/flow_setting_et")
	public AndroidElement etDownloadTip;// 流量提示设置的输入框

	public DownloadTipDialogPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new DownloadTipDialogAction();
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

	@Tips(description = "临时操作DownloadTipDialogPage的内部Action")
	public class DownloadTipDialogAction extends BaseAction {

		@Override
		public void go2SelfPage() {
			// TODO Auto-generated method stub
		}

		public DownloadTipDialogAction() {
			// TODO Auto-generated constructor stub
		}

	}
}
