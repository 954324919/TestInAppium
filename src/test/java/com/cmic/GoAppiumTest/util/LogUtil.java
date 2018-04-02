package com.cmic.GoAppiumTest.util;

import com.cmic.GoAppiumTest.helper.Tips;

public class LogUtil {
	public static String getExecutingMethodName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stackTrace[2];
		return e.getMethodName();
	}

	public static String getExecutingClassName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stackTrace[2];
		return e.getClassName();
	}

	public static String getExecutingFileName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stackTrace[2];
		return e.getFileName();
	}

	@Tips(description = "打印当前方法名", //
			triggerTime = "在0402尚且不能作为一个切面来进行日志打印。先按此方法执行")
	public static void printCurrentMethodName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stackTrace[2];
		System.err.println(e.getMethodName());
	}
}
