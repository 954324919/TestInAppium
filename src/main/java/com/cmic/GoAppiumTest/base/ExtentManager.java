package com.cmic.GoAppiumTest.base;

import java.io.File;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.FileUtil;

public class ExtentManager {
	private static volatile ExtentReports extent;

	public static ExtentReports getInstance(String filePath) {
		if (extent == null) {
			synchronized (ExtentManager.class) {
				if (extent == null) {
					createInstance(filePath);
				}
			}
		}
		return extent;
	}

	public static void createInstance(String filePath) {
		extent = new ExtentReports();
		extent.setSystemInfo("os", "Windows");// 实际无意义
		extent.attachReporter(createHtmlReporter(filePath));
	}

	@Tips(description = "用于生成Html报表")
	private static ExtentReporter createHtmlReporter(String filePath) {
		FileUtil.createFilePath(filePath);// 创建文件夹防止FileIOE
		String targetExtentSaveFilePath = filePath + File.separator + "index.html";
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(targetExtentSaveFilePath);
		// 报表位置
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		// 使报表上的图表可见
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(filePath);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);//更换CDN保证显示
		htmlReporter.config().setReportName("测试报告");
		return htmlReporter;
	}
}
