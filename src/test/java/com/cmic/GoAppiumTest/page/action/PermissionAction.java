package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;

public class PermissionAction extends BaseAction {

	@Tips(description = "无参数构造方法")
	public PermissionAction() {
		// TODO
	}

	@Tips(description = "去往权限提示页面", triggerTime = "权限AllDeny")
	public void go2PermissionDetail(AndroidElement btnDeny) {
		for (int i = 0; i < 4; i++) {
			go2Click(btnDeny);
			WaitUtil.forceWait(1);
		}
	}

	@Tips(description = "去往权限授权页面")
	@Override
	public void go2SelfPage() {
		PageRedirect.redirect2RequestPermissionActivity();
	}
}
