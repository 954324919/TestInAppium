package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.util.ContextUtil;

public class SearchAction extends BaseAction {

	@Override
	public void go2SelfPage() {
		if (!ContextUtil.getCurrentActivity().equals("SearchActivity"))
			PageRedirect.redirect2SearchActivity();
	}

	public SearchAction() {
		if (!ContextUtil.getCurrentPageActivtiy().equals("SearchActivity")) {
			PageRedirect.redirect2MainActivity();
		}
	}
}
