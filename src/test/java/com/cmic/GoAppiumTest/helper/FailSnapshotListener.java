package com.cmic.GoAppiumTest.helper;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;

public class FailSnapshotListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		// 每次调用测试之前调用

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// 每次测试成功时调用

	}

	@Override
	public void onTestFailure(ITestResult result) {
		// 每次测试失败时调用
		ScreenUtil.screenShotForce(result.getName() + "执行失败");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// 每次跳过测试时调用

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// 每次方法失败但是已经使用successPercentage进行注释时调用，并且此失败仍保留在请求的成功百分比之内。

	}

	@Override
	public void onStart(ITestContext context) {
		// 在测试类被实例化之后调用，并在调用任何配置方法之前调用

	}

	@Override
	public void onFinish(ITestContext context) {
		// 在所有测试运行之后调用，并且所有的配置方法都被调用

	}

}
