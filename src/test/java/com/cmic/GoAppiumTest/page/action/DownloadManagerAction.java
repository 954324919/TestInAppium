package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;

public class DownloadManagerAction extends BaseAction {

	@Tips(description = "无参数构造方法")
	public DownloadManagerAction() {
		//
	}

	@Tips(description = "到达SplashPage")
	@Override
	public void go2SelfPage() {
		// TODO
		PageRedirect.redirect2DownloadManagerActivity();
		WaitUtil.forceWait(1);
	}
}