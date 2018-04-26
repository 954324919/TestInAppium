package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.util.WaitUtil;

public class TrafficAction extends BaseAction {

	public TrafficAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void go2SelfPage() {
		PageRedirect.redirect2TrafficManagerActivity();
		WaitUtil.forceWait(2);
	}
}
