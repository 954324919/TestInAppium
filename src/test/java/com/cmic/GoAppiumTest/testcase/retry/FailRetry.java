package com.cmic.GoAppiumTest.testcase.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * @描述 失败重试
 * @author cmic
 *
 */
public class FailRetry implements IRetryAnalyzer {

	private int mFailTime = 1; // 统计测试用例失败的次数
	private int mMaxFailCount = 3; // 测试用例最大失败次数

	@Override
	public boolean retry(ITestResult iTestResult) {
		String msg = "执行用例：" + iTestResult.getName() + "第" + mFailTime + "次运行失败";
		System.err.println("失败重试: " + iTestResult);
		Reporter.log(msg);
		if (mFailTime < mMaxFailCount) {
			mFailTime++;
			return true;
		}
		return false;
	}

}
