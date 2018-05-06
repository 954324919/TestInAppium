package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

public class TrafficAction extends BaseAction {

	public TrafficAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void go2SelfPage() {
		if (ContextUtil.getCurrentPageActivtiy().equals("TrafficDetailActivity")) {
			return;
		} else if (ContextUtil.getCurrentPageActivtiy().equals("MainActivity")) {
			PageRedirect.incFromMain2Traffic();
		} else {
			PageRedirect.redirect2TrafficManagerActivity();
		}
	}
}
