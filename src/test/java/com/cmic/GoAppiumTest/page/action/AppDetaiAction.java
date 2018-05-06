package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;

public class AppDetaiAction extends BaseAction {

	@Tips(description = "无参数构造方法")
	public AppDetaiAction() {
		//
	}

	@Tips(description = "到达SplashPage")
	@Override
	public void go2SelfPage() {
		// TODO
		WaitUtil.forceWait(1);
	}
}