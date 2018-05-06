package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;

public class LoginAction extends BaseAction {

	@Tips(description = "无参数构造方法")
	public LoginAction() {
		//
	}

	@Tips(description = "到达LoginPage")
	@Override
	public void go2SelfPage() {
		WaitUtil.forceWait(1);
	}
}