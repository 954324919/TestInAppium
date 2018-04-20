package com.cmic.GoAppiumTest.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.testcase.RandomUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class ScreenUtil {

	public static int DPI = 0;

	public static int getDeviceWidth() {
		return DriverManger.getDriver().manage().window().getSize().width;
	}

	public static int getDeviceHeight() {
		return DriverManger.getDriver().manage().window().getSize().height;// 获取手机屏幕高度
	}

	public static void screenShot(String msg) {
		// 時間格式
		if (!App.SNAPSHOT_SWITCH) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH時mm分ss秒");
		// String screenDir = "F:/WorkSpace4Mars/GoAppiumTest/target/screenshot";
		String screenDir = FileUtil.filePathTransformRelative("/target/screenshot");
		// 生成时间戳
		String timeSuffix = sdf.format(new Date());
		// 由于可能存在异常图片的且当被删除的可能，所以这边先判断目录是否存在
		if (!(new File(screenDir).isDirectory())) {
			// 判断是否存在该目录
			new File(screenDir).mkdir();
		}
		// 调用方法捕捉画面
		File screen = DriverManger.getDriver().getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screen,
					new File(screenDir + File.separator + (++App.PHONE_COUNT) + "." + msg + "-" + timeSuffix + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void screenShotForce(String msg) {
		// 時間格式
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		// String screenDir = "F:/WorkSpace4Mars/GoAppiumTest/target/screenshot";
		// String screenDir =
		// "D:/EclipseWorkspace/GoAppium/GoAppiumTest/target/screenshot";
		String screenDir = FileUtil.filePathTransformRelative("/target/screenshot");

		// 生成时间戳
		String timeSuffix = sdf.format(new Date());
		// 由于可能存在异常图片的且当被删除的可能，所以这边先判断目录是否存在
		if (!(new File(screenDir).isDirectory())) {
			// 判断是否存在该目录
			new File(screenDir).mkdir();
		}
		// 调用方法捕捉画面
		File file = null;
		try {
			file = new File(screenDir + File.separator + (++App.PHONE_COUNT) + "-" + msg + "-" + timeSuffix + ".jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		File screen = DriverManger.getDriver().getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screen, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String screenShotForceReturnPath(String msg) {
		// 時間格式
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		// String screenDir = "F:/WorkSpace4Mars/GoAppiumTest/target/screenshot";
		String screenDir = FileUtil.filePathTransformRelative("/target/screenshot");

		// 生成时间戳
		String timeSuffix = sdf.format(new Date());
		// 由于可能存在异常图片的且当被删除的可能，所以这边先判断目录是否存在
		if (!(new File(screenDir).isDirectory())) {
			// 判断是否存在该目录
			new File(screenDir).mkdir();
		}
		// 调用方法捕捉画面
		File file = null;
		try {
			file = new File(screenDir + File.separator + (++App.PHONE_COUNT) + "-" + msg + "-" + timeSuffix + ".jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		File screen = DriverManger.getDriver().getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screen, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (file != null) {
			return file.getAbsolutePath();
		} else {
			return "";
		}

	}

	public static void lockScreen() {
		DriverManger.getDriver().lockDevice();// 锁屏，熄灭屏幕
	}

	@Tips(description = "用于获取设备的dpi", //
			riskPoint = "目前只支持Win，不同的操作系统需要兼容")
	public static int getScreenDpi() {
		if (DPI != 0) {
			return DPI;
		}
		String cmdResult = AdbManager.excuteAdbShellGetResultGrep("adb shell dumpsys window displays", "dpi");
		String[] tar = cmdResult.split(" ");
		for (int i = 1; i < tar.length; i++) {
			if (tar[i].equals(" ") || !tar[i].contains("dpi"))
				continue;
			try {
				DPI = Integer.parseInt(tar[i].replaceAll("[a-zA-z]", ""));
				return DPI;
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	public static void singleTap(int x, int y) {
		DriverManger.getDriver().tap(1, x, y, 100);
	}

	public static void singleRandomTap() {
		int randomX = RandomUtil.getRandomNum(getDeviceWidth());
		int randomY = RandomUtil.getRandomNum(getDeviceHeight());
		singleTap(randomX, randomY);
	}

	public static void doubleTap(int x, int y) {
		DriverManger.getDriver().tap(2, x, y, 100);
	}

	public static void doubleRandomTap() {
		int randomX = RandomUtil.getRandomNum(getDeviceWidth());
		int randomY = RandomUtil.getRandomNum(getDeviceHeight());
		doubleTap(randomX, randomY);
	}

	public static int dp2Px(int dp) {
		getScreenDpi();
		return (int) (dp * (DPI / 160.0));
	}

	public static int dx2Dp(int px) {
		getScreenDpi();
		return (int) (px / (DPI / 160.0));
	}

	public static int getStatusBarHeight() {
		return dp2Px(App.STATUS_BAR_HEIGHT_DP);
	}

	public static int getActionBarHeight() {
		return dp2Px(App.TITLE_BAR_HEIGHT_DP);
	}

	public static void zoom(AndroidElement el) {
		DriverManger.getDriver().zoom(el);
	}
}
