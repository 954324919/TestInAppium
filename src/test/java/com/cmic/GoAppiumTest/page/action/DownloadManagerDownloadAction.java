package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

@Tips(description = "下载管理的下载Tab")
public class DownloadManagerDownloadAction extends BaseAction {

	@Override
	public void go2SelfPage() {
		if ("ManagerCenterActivity".equals(ContextUtil.getCurrentPageActivtiy())) {
			// 已经是ManagerCenter
		} else if ("MainActivity".equals(ContextUtil.getCurrentPageActivtiy())) {
			PageRedirect.incFromMain2DownloadManager();
		} else {
			PageRedirect.redirect2DownloadManagerActivity();
		}
		ScrollUtil.scrollToPrecent(Direction.RIGHT, 80);// 右滑动保证在下载Tab页面
	}

	public DownloadManagerDownloadAction() {
		// TODO Auto-generated constructor stub
	}
}
