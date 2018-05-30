package com.cmic.GoAppiumTest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cmic.GoAppiumTest.helper.Tips;

public class EssentialUtil {

	@Tips(description = "日期转换")
	public static String currentTime2Date() {
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("HH时mm分ss秒");
		return formatter.format(new Date(currentTime));
	}

	@Tips(description = "获得时间差,单位Second")
	public static double getTheTimeDiff(long before) {
		return (System.currentTimeMillis() - before) / 1000.0;
	}

	@Tips(description = "获取时间差，单位未Second")
	public static double getTheTimeDiff(long before, long after) {
		return (after - before) / 1000.0;
	}

	@Tips(description = "检查字符串是否为空串")
	public static boolean isEmpty(String target) {
		return target == null || target.isEmpty();
	}
}
