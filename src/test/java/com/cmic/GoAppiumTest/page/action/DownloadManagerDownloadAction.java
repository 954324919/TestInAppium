package com.cmic.GoAppiumTest.page.action;

import org.openqa.selenium.By;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

@Tips(description = "下载管理的下载Tab")
public class DownloadManagerDownloadAction extends BaseAction {

	@Override
	public void go2SelfPage() {
		if ("ManagerCenterActivity".equals(ContextUtil.getCurrentPageActivtiy())) {
			ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);
		} else if ("MainActivity".equals(ContextUtil.getCurrentPageActivtiy())) {
			WaitUtil.implicitlyWait(5);// 等待1S
			driver.findElement(By.id("com.cmic.mmnes:id/managerview")).click();
			WaitUtil.forceWait(2);
		} else {
			PageRedirect.redirect2DownloadManagerActivity();
			ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);// 左滑动保证在更新页面
		}
	}

	public DownloadManagerDownloadAction() {
		// TODO Auto-generated constructor stub
	}
}
