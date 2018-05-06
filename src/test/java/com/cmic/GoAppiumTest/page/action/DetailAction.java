package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;

public class DetailAction extends BaseAction {

	@Override
	public void go2SelfPage() {
		PageRedirect.redirect2DetailActivity();
	}

	public DetailAction() {
		//
	}
}
