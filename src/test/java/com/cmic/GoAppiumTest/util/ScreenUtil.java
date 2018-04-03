package com.cmic.GoAppiumTest.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class ScreenUtil {
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
		String screenDir = "F:/WorkSpace4Mars/GoAppiumTest/target/screenshot";
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
					new File(screenDir + "/" + (++App.PHONE_COUNT) + "." + msg + "-" + timeSuffix + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void lockScreen() {
		DriverManger.getDriver().lockDevice();// 锁屏，熄灭屏幕
	}

	public static int getScreenDpi() {
		String cmdResult = AdbManager.excuteAdbShellGetResultGrep("adb shell dumpsys window displays", "dpi");
		String[] tar = cmdResult.split(" ");
		for (int i = 1; i < tar.length; i++) {
			if (tar[i].equals(" ") || !tar[i].contains("dpi"))
				continue;
			try {
				return Integer.parseInt(tar[i].replaceAll("[a-zA-z]", ""));
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}
}
