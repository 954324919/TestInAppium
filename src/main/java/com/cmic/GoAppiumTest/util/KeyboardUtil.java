
package com.cmic.GoAppiumTest.util;

import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.base.DriverManger;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class KeyboardUtil {
	// 逐字删除编辑框中的文字
	public static void clearText(AndroidElement element) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		String className = element.getClass().getSimpleName();
		if (className.equals("EditText")) {
			String text = element.getText();
			// 跳到最后
			DriverManger.getDriver().pressKeyCode(AndroidKeyCode.KEYCODE_MOVE_END);
			for (int i = 0; i < text.length(); i++) {
				// 循环后退删除
				driver.pressKeyCode(AndroidKeyCode.BACKSPACE);
			}
		} else {
			System.out.println("不是文本输入框架，无法删除文字");
		}
	}

	// 在需要搜索的时候执行下面的代码，切换的输入法用自己查看列表的输入法内容，我这里是搜狗输入法
	// 使用adb shell 切换输入法-更改为
	// 击搜狗拼音搜索，这个看你本来用的什么输入法
	@SuppressWarnings("unused")
	private void goSougoIMESearch(AndroidElement targetEditText) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		AdbManager.excuteAdbShell("adb shell ime set com.sohu.inputmethod.sogou.xiaomi/.SogouIME");
		// 再次点击输入框，调取键盘，软键盘被成功调出
		targetEditText.click();
		// 点击右下角的搜索，即ENTER键
		driver.pressKeyCode(AndroidKeyCode.ENTER);
		// 再次切回 输入法键盘为Appium unicodeKeyboard
		AdbManager.excuteAdbShell("adb shell ime set io.appium.android.ime/.UnicodeIME");
	}

	// 点击讯飞输入搜索
	@SuppressWarnings("unused")
	private void goFlyIMESearch(AndroidElement targetEditText) {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		AdbManager.excuteAdbShell("adb shell ime set com.iflytek.inputmethod/.FlyIME");
		// 再次点击输入框，调取键盘，软键盘被成功调出
		targetEditText.click();
		// 点击右下角的搜索，即ENTER键
		driver.pressKeyCode(AndroidKeyCode.ENTER);
		// 再次切回 输入法键盘为Appium unicodeKeyboard
		AdbManager.excuteAdbShell("adb shell ime set com.iflytek.inputmethod/.FlyIME");
	}

	// 隐藏键盘
	public static void hideKeyBoard() {
		AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
		driver.hideKeyboard();
	}
}
