package com.cmic.GoAppiumTest.helper;

import java.io.File;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.ExtentManager;
import com.cmic.GoAppiumTest.util.FileUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;

public class ExtentReportListener implements ITestListener {

	private static final String REPORT_PATH = FileUtil.filePathTransformRelative("/target/extent-output");
	private static ExtentReports extent = ExtentManager.getInstance(REPORT_PATH);
	private static ThreadLocal<ExtentTest> testLocal = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		testLocal.set(extent.createTest(result.getMethod().getMethodName()));
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		((ExtentTest) testLocal.get()).pass("Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		((ExtentTest) testLocal.get()).fail(result.getThrowable());
		String screenPath = ScreenUtil.screenShotForceReturnPath(result.getName() + "执行失败");
		try {
			((ExtentTest) testLocal.get()).addScreenCaptureFromPath(screenPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		((ExtentTest) testLocal.get()).skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Tips(description = "Extent要求最后进行刷新")
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
