package com.cmic.GoAppiumTest.util;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public static void printCurrentMethodName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stackTrace[2];
		System.err.println(e.getMethodName());
	}

	public static Logger get(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	public static Logger get(String name) {
		return LoggerFactory.getLogger(name);
	}

	public static Logger get() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stackTrace[2].getClassName());
	}

	public static void trace(String format, Object... arguments) {
		trace(innerGet(), format, arguments);
	}

	public static void trace(Logger log, String format, Object... arguments) {
		log.trace(format, arguments);
	}

	public static void d(String msg) {
		innerGet().debug(msg);
	}

	public static void d(String format, Object... arguments) {
		d(innerGet(), format, arguments);
	}

	public static void d(Logger log, String format, Object... arguments) {
		log.debug(format, arguments);
	}

	public static void i(String format, Object... arguments) {
		i(innerGet(), format, arguments);
	}

	public static void i(String msg) {
		innerGet().info(msg);
	}

	private static void i(Logger log, String format, Object... arguments) {
		log.info(format, arguments);
	}

	public static void w(String format, Object... arguments) {
		w(innerGet(), format, arguments);
	}

	private static void w(Logger log, String format, Object... arguments) {
		log.warn(format, arguments);
	}

	public static void w(Throwable e, String format, Object... arguments) {
		w(innerGet(), e, format(format, arguments));
	}

	public static void w(String msg) {
		innerGet().warn(msg);
	}

	private static void w(Logger log, Throwable e, String format, Object... arguments) {
		log.warn(format(format, arguments), e);
	}

	public static void e(String format, Object... arguments) {
		e(innerGet(), format, arguments);
	}

	public static void e(String msg) {
		innerGet().error(msg);
	}

	private static void e(Logger log, String format, Object... arguments) {
		log.error(format, arguments);
	}

	public static void e(Throwable e, String format, Object... arguments) {
		e(innerGet(), e, format(format, arguments));
	}

	public static void e(Logger log, Throwable e, String format, Object... arguments) {
		log.error(format(format, arguments), e);
	}

	private static String format(String template, Object... values) {
		return String.format(template.replace("{}", "%s"), values);
	}

	private static Logger innerGet() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stackTrace[3].getClassName());
	}
}
