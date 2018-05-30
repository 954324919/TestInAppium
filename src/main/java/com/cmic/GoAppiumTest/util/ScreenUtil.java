package com.cmic.GoAppiumTest.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;

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

	/**
	 * @param standard
	 *            标准图片名称
	 * @param actual
	 *            实际截图名称
	 * @return 相似度 100为完全相同
	 */
	@Tips(description = "对比2张图片的相似度")
	public static double compareImageByPath(String standard, String actual) {
		LogUtil.i("开始图片全图对比");
		String path1 = FileUtil.filePathTransformRelativeRaw(standard);
		String path2 = FileUtil.filePathTransformRelativeRaw(actual);
		LogUtil.i("图片1：" + path1);
		LogUtil.i("图片2：" + path2);
		// 标准图片
		BufferedImage image1 = ImageUtil.getImageFromFile(new File(path1));
		BufferedImage image2 = ImageUtil.getImageFromFile(new File(path2));
		return ImageUtil.compareImage(image1, image2);
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

	/**
	 * 根据控件元素进行截图
	 * 
	 * @param me
	 *            控件元素
	 * @param picName
	 */
	@Tips(description = "获取元素的截图")
	public static void captureByElement(AndroidElement element, String picName) {
		int x = element.getLocation().x;
		int y = element.getLocation().y;
		int w = element.getSize().width;
		int h = element.getSize().height;
		File srcFile = DriverManger.getDriver().getScreenshotAs(OutputType.FILE);
		String screenDir = FileUtil.filePathTransformRelative("/target/screenshot");
		File targetFile = null;
		if (!(new File(screenDir).isDirectory())) {
			// 判断是否存在该目录
			new File(screenDir).mkdir();
		}
		// 调用方法捕捉画面
		try {
			targetFile = new File(screenDir + File.separator + (++App.PHONE_COUNT) + "-" + picName + ".jpg");
			LogUtil.i("[截图]保存地址：" + targetFile.getPath());
			FileUtils.copyFile(srcFile, targetFile);
			BufferedImage image = ImageUtil.getImageFromFile(targetFile);
			LogUtil.i("截图：起始坐标[" + x + "," + y + "],宽" + w + ",高" + h);
			BufferedImage subimage = ImageUtil.getSubImage(image, x, y, w, h);
			FileOutputStream output = new FileOutputStream(targetFile);
			ImageIO.write(subimage, "png", output);
			output.close();
		} catch (IOException e) {
			LogUtil.i("[截图]搬迁图片失败", e);
		}
	}

	/**
	 * 根据控件元素进行截图
	 * 
	 * @param me
	 *            控件元素
	 * @param picName
	 */
	@Tips(description = "获取元素的截图")
	public static BufferedImage captureByElementWithReturn(AndroidElement element, String picName) {
		int x = element.getLocation().x;
		int y = element.getLocation().y;
		int w = element.getSize().width;
		int h = element.getSize().height;
		File srcFile = DriverManger.getDriver().getScreenshotAs(OutputType.FILE);
		String screenDir = FileUtil.filePathTransformRelative("/target/screenshot");
		File targetFile = null;
		if (!(new File(screenDir).isDirectory())) {
			// 判断是否存在该目录
			new File(screenDir).mkdir();
		}
		// 调用方法捕捉画面
		try {
			targetFile = new File(screenDir + File.separator + (++App.PHONE_COUNT) + "-" + picName + ".jpg");
			LogUtil.i("[截图]保存地址：" + targetFile.getPath());
			FileUtils.copyFile(srcFile, targetFile);
			BufferedImage image = ImageUtil.getImageFromFile(targetFile);
			LogUtil.i("截图：起始坐标[" + x + "," + y + "],宽" + w + ",高" + h);
			BufferedImage subimage = ImageUtil.getSubImage(image, x, y, w, h);
			FileOutputStream output = new FileOutputStream(targetFile);
			ImageIO.write(subimage, "png", output);
			output.close();
			return subimage;
		} catch (IOException e) {
			LogUtil.i("[截图]搬迁图片失败", e);
		}
		return null;
	}

	public static BufferedImage captureByElementWithoutSave(AndroidElement element) {
		int x = element.getLocation().x;
		int y = element.getLocation().y;
		int w = element.getSize().width;
		int h = element.getSize().height;
		File srcFile = DriverManger.getDriver().getScreenshotAs(OutputType.FILE);
		BufferedImage image = ImageUtil.getImageFromFile(srcFile);
		LogUtil.i("截图：起始坐标[" + x + "," + y + "],宽" + w + ",高" + h);
		BufferedImage subimage = ImageUtil.getSubImage(image, x, y, w, h);
		return subimage;
	}

	/**
	 * 根据控件元素进行截图
	 * 
	 * @param me
	 *            控件元素
	 * @param picName
	 */
	@Tips(description = "获取元素的截图")
	public static BufferedImage captureByElementWithReturn(AndroidElement element, String picName,
			boolean formatTheName) {
		int x = element.getLocation().x;
		int y = element.getLocation().y;
		int w = element.getSize().width;
		int h = element.getSize().height;
		File srcFile = DriverManger.getDriver().getScreenshotAs(OutputType.FILE);
		String screenDir = FileUtil.filePathTransformRelative("/target/screenshot");
		File targetFile = null;
		if (!(new File(screenDir).isDirectory())) {
			// 判断是否存在该目录
			new File(screenDir).mkdir();
		}
		// 调用方法捕捉画面
		try {
			if (formatTheName) {
				targetFile = new File(screenDir + File.separator + (++App.PHONE_COUNT) + "-" + picName + ".jpg");
			} else {
				targetFile = new File(screenDir + File.separator + picName + ".jpg");
			}
			LogUtil.i("[截图]保存地址：" + targetFile.getPath());
			FileUtils.copyFile(srcFile, targetFile);
			BufferedImage image = ImageUtil.getImageFromFile(targetFile);
			LogUtil.i("截图：起始坐标[" + x + "," + y + "],宽" + w + ",高" + h);
			BufferedImage subimage = ImageUtil.getSubImage(image, x, y, w, h);
			FileOutputStream output = new FileOutputStream(targetFile);
			ImageIO.write(subimage, "png", output);
			output.close();
			return subimage;
		} catch (IOException e) {
			LogUtil.i("[截图]搬迁图片失败", e);
		}
		return null;
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
