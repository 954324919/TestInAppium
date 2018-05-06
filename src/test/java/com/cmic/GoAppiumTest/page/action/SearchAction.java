package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.util.ContextUtil;

public class SearchAction extends BaseAction {

	@Override
	public void go2SelfPage() {
		if (ContextUtil.getCurrentPageActivtiy().equals("SearchActivity")) {
			return;
		} else if (ContextUtil.getCurrentPageActivtiy().equals("MainActivity")) {
			PageRedirect.incFromMain2Search();
		} else {
			PageRedirect.redirect2SearchActivity();
		}
	}

	public SearchAction() {
		// TODO 无参构造方法
	}
}
