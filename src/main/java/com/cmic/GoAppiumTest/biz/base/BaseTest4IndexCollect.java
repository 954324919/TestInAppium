package com.cmic.GoAppiumTest.biz.base;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTestNew;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.LogUtil;

@Tips(description = "用于分化不同的测试类型")
public abstract class BaseTest4IndexCollect extends BaseTestNew {

	@Override
	public void setUpBeforeClassOrigin() {
		LogUtil.w("{}用例集[{}]开始", "指标收集", mTag);
		setUpBeforeClass();
	}

	@Override
	public void tearDownAfterClassOrigin() {
		LogUtil.w("{}用例集[{}]结束", "指标收集", mTag);
		tearDownAfterClass();
	}

	@Override
	public void setUpBeforeMTestCaseOrigin() {
		LogUtil.w("{}用例{}开始", "指标收集", ++App.CASE_COUNT);
	}

	@Override
	public void tearDownAfterMTestCaseOrigin() {
		LogUtil.w("{}用例{}结束", "指标收集", App.CASE_COUNT);
	}

	public abstract void tearDownAfterClass();

	public abstract void setUpBeforeClass();

}
