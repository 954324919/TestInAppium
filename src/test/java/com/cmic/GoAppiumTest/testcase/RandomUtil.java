package com.cmic.GoAppiumTest.testcase;

import java.util.Random;

import com.cmic.GoAppiumTest.helper.Tips;

public class RandomUtil {

	/**
	 * 产生随机数
	 * @param start
	 * @param range
	 * @return
	 */
	@Tips(description = "产生一个随机数，不包含range的值")
	public static int getRandomNum(int start, int range) {
		Random random = new Random();
		return start + random.nextInt(range);
	}

	/**
	 * 产生随机字符串
	 * @param length 生成字符串长度
	 * @return
	 */
	@Tips(description="产生随机字符串")
	public static String getRandomString(int length) {
		// 定义一个字符串（A-Z，a-z，0-9）即62位；
		String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		// 由Random生成随机数
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
}
