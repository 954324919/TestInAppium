package com.cmic.GoAppiumTest.page.action;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;

public class MainSoftTabAction extends BaseAction {

	@Override
	public void go2SelfPage() {
		if (!ContextUtil.getCurrentActivity().equals("MainActivity")) {
			PageRedirect.redirect2MainActivity();
			go2SwipeFullScreen(Direction.RIGHT, 80);// 右滑动
		} else {
			go2SwipeFullScreen(Direction.RIGHT, 80);// 右滑动
		}
	}

}
