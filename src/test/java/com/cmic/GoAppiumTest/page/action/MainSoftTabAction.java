package com.cmic.GoAppiumTest.page.action;

import org.openqa.selenium.WebDriverException;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil.Direction;
import com.cmic.GoAppiumTest.util.WaitUtil;

public class MainSoftTabAction extends BaseAction {

	@Override
	public void go2SelfPage() {
		if (!ContextUtil.getCurrentPageActivtiy().equals("MainActivity")) {
			PageRedirect.redirect2MainActivity();
		}
		int time = 1;
		WaitUtil.forceWait(3);// 等待加载
		while (time < RETRYTIME) {// 模拟重试，重试时间不断加长
			try {
				go2SwipeFullScreen(Direction.RIGHT, 80);// 右滑动
				break;
			} catch (WebDriverException e) {
				LogUtil.e("{}的go2SelfPage重试第{}次，首页Soft加载时间超过{}秒", this.getClass().getSimpleName(), time,
						TIMESTEP * time);
				WaitUtil.forceWait(TIMESTEP + time);
				time++;
			}
		}
	}

}
