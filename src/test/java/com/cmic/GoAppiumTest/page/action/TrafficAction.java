package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

public class TrafficAction extends BaseAction {

	public TrafficAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void go2SelfPage() {
		String curContext = ContextUtil.getCurrentPageActivtiy();
		LogUtil.e("上下文为{}", curContext);
		if (curContext.equals("TrafficDetailActivity")) {
			return;
		} else if (curContext.equals("MainActivity")) {
			PageRedirect.incFromMain2Traffic();
		} else {
			PageRedirect.redirect2TrafficManagerActivity();
		}
	}
}
