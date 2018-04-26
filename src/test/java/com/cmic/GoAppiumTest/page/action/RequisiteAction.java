package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;

public class RequisiteAction extends BaseAction {

	@Tips(description = "无参数构造方法")
	public RequisiteAction() {
		//
	}

	@Override
	public void go2SelfPage() {
		PageRedirect.redirect2RequestiteActivity();
		WaitUtil.forceWait(1);		
	}
}
