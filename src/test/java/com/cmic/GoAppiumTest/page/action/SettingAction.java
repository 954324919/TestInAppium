package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;

public class SettingAction extends BaseAction {

	@Tips(description = "无参数构造方法")
	public SettingAction() {
		// TODO
	}

	@Tips(description = "到达SplashPage")
	@Override
	public void go2SelfPage() {
		if ("SettingActivity".equals(ContextUtil.getCurrentPageActivtiy())) {
			// 已经是SettingActivity
		} else if ("MainActivity".equals(ContextUtil.getCurrentPageActivtiy())) {
			PageRedirect.incFromMain2Setting();
		} else if ("ManagerCenterActivity".equals(ContextUtil.getCurrentPageActivtiy())) {
			PageRedirect.incFromDownloadManager2Setting();
		} else {
			PageRedirect.redirect2SettingActivity();
		}
	}
}