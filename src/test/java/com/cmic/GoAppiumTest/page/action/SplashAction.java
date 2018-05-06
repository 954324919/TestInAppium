package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;

@Tips(description = "Spalsh操作管理内部类")
public class SplashAction extends BaseAction {

	@Tips(description = "无参数构造方法")
	public SplashAction() {
		//
	}

	@Tips(description = "到达SplashPage")
	@Override
	public void go2SelfPage() {
       PageRedirect.redirect2SplashActivity();
       WaitUtil.forceWait(1);
	}
}