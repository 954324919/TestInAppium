package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;

public class ShareAction extends BaseAction {

	@Tips(description = "无参数构造方法")
	public ShareAction() {
		// TODO
	}

	@Tips(description = "到达SharePage")
	@Override
	public void go2SelfPage() {
		// TODO
		WaitUtil.forceWait(1);
	}
}