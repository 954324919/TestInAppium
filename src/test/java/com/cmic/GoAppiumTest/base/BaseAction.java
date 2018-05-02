package com.cmic.GoAppiumTest.base;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.dataprovider.util.ExcelUtil;
import com.cmic.GoAppiumTest.helper.Heading;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.KeyboardUtil;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.NetworkUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.ScrollUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.Connection;

public abstract class BaseAction {

	@Tips(description = "重试时间")
	protected static final int TIMESTEP = 3;
	@Tips(description = "重试次数")
	protected static final int RETRYTIME = 3;
	@Tips(description = "驱动")
	protected static AndroidDriver<AndroidElement> driver = DriverManger.getDriver();

	@Tips(description = "无参数构造方法")
	public BaseAction() {
		// TODO
	}

	@Tips(description = "点击按钮")
	public void go2Click(AndroidElement e) {
		if (driver == null) {
			LogUtil.e("DriverNull");
		}
		if (e == null) {
			LogUtil.d("eNull");
		}
		e.click();
	}

	public void go2TapByPoint(int x, int y) {
		ScreenUtil.singleTap(x, y);
	}

	@Tips(description = "输入文字")
	public void go2SendWord(AndroidElement e, String keyWord) {
		e.clear();
		WaitUtil.forceWait(0.5);
		e.sendKeys(keyWord);
	}

	public String go2GetText(AndroidElement e) {
		return e.getText();
	}

	@Tips(description = "回到当前页面")
	public abstract void go2SelfPage();

	@Tips(description = "双击按钮")
	public void go2DoubleTap() {

	}

	@Tips(description = "长按按钮")
	public void go2LongTap(AndroidElement e) {
		TouchAction ta = new TouchAction(driver);
		ta.longPress(e).release().perform(); // 长按
	}

	@Tips(description = "获取网络状态")
	public Connection go2GetNetWorkStatus() {
		return NetworkUtil.getNetworkState();
	}

	@Tips(description = "控件内滚动")
	public void go2SwipeInElement(AndroidElement elementScrollable, Heading heading) {
		ElementUtil.swipeControl(elementScrollable, heading);
	}

	@Tips(description = "全屏幕滑动")
	public void go2SwipeFullScreen(ScrollUtil.Direction heading, int precent) {
		ScrollUtil.scrollToPrecent(heading, precent);
		WaitUtil.forceWait(2);
	}

	@Tips(description = "滚动到底部")
	public void go2Swipe2Bottom() {
		ScrollUtil.scrollToBase();
	}

	@Tips(description = "滚动到底部")
	public void go2Swipe2BottomWithTimeOut(int timeoutSecond) {
		ScrollUtil.scrollToBaseTimeoutQuit(timeoutSecond);
	}

	@Tips(description = "Et输入")
	public void go2TypeWord(AndroidElement e, String word) {
		e.sendKeys(word);
	}

	@Tips(description = "清楚缓存")
	public void go2ClearData() {
		AppUtil.clearAppData(App.PACKAGE_NAME);
	}

	@Tips(description = "清除控件Et的输入内容")
	public void go2Clear(AndroidElement e) {
		e.clear();
	}

	@Tips(description = "复制Et内容")
	public void go2CopyText() {
	}

	@Tips(description = "粘贴复制的文字")
	public void go2PasteText(AndroidElement e) {
		ElementUtil.ctrlVPaste(e);
	}

	@Tips(description = "获取Excel的数据")
	public Object[][] go2GetExcelData(String filePath, String sheetName) {
		try {
			return ExcelUtil.readExcel(filePath, sheetName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 发送Adb指令
	 */
	@Tips(description = "发送Adb指令")
	public void go2Command(String cmd) {
		AdbManager.excuteAdbShell(cmd);
	}

	/**
	 * 点击Home键
	 */
	@Tips(description = "点击Home键")
	public void go2HomePage() {
		PageRouteUtil.pressHome();
	}

	@Tips(description = "卸载软件")
	public void go2UninstallApp() {
		AppUtil.unInstall(App.PACKAGE_NAME);
	}

	@Tips(description = "点击后退键")
	public void go2Backforward() {
		PageRouteUtil.pressBack();
	}

	@Tips(description = "安装app")
	public void go2InstallApp(String appPath) {
		go2Command("adb install " + appPath);
	}

	@Tips(description = "判断是否安装")
	public boolean go2CheckInstall(String PackageName) {
		return false;
	}

	@Tips(description = "判断是否锁屏")
	public boolean go2CheckScrennLocked() {
		return DeviceUtil.getLockStatus();
	}

	@Tips(description = "打开通知栏界面")
	public void go2OpenNotifications() {
		DeviceUtil.openNotification();
		WaitUtil.forceWait(1);
	}

	@Tips(description = "关闭通知栏")
	public void go2CloseNotification() {
		DeviceUtil.closeNotification();
		WaitUtil.forceWait(1);
	}

	@Tips(description = "重置应用数据，清除缓存")
	public void go2AppReset() {
		AppUtil.resetApp();
	}

	@Tips(description = "软Reset,不清楚缓存", riskPoint = "可能导致PageFactory在页面变动时出现NullPoint")
	public void go2SoftReset() {
		AppUtil.softResetApp();
	}

	@Tips(description = "模拟按下安卓手机键")
	public void go2PressAndroidKey(int androidKeyCode) {
		driver.pressKeyCode(androidKeyCode);
	}

	@Tips(description = "获取控件元素的左上角X")
	public int go2GetElementX(AndroidElement element) {//
		return element.getLocation().getX();
	}

	@Tips(description = "页面伸缩")
	public static void zoom(AndroidElement el) {
		DriverManger.getDriver().zoom(el);
	}

	@Tips(description = "获取控件元素的右上角Y")
	public int go2GetElementY(AndroidElement element) {//
		return element.getLocation().getY();
	}

	@Tips(description = "获取控件元素的宽度")
	public int go2GetElementWidth(AndroidElement element) {
		return element.getSize().width;
	}

	@Tips(description = "获取控件元素的高度")
	public int go2GetElementHeight(AndroidElement element) {// 右上角Y
		return element.getSize().height;
	}

	@Tips(description = "强制关闭应用")
	public void go2ForceKillApp() {
		AppUtil.killApp(App.PACKAGE_NAME); // driver.close()
		// AppUtil.killApp(App.PACKAGE_NAME); #调用adb
	}

	@Tips(description = "元素是否出现")
	public boolean go2CheckElementIsPresentInSafe(AndroidElement e) {
		return e.isDisplayed();
	}

	@Tips(description = "键盘切换")
	public void go2KeyboardSwitch() {

	}

	@Tips(description = "键盘隐藏")
	public void go2HideKeyboard() {
		if (driver.isKeyboardShown()) {
			KeyboardUtil.hideKeyBoard();
		}
	}

	@Tips(description = "点击Menu")
	public void go2PressMenu() {
		DriverManger.getDriver().pressKeyCode(AndroidKeyCode.KEYCODE_MENU);
	}

	@Tips(description = "H5页面前进")
	public void goWebkitForward() {
		DriverManger.getDriver().navigate().forward();
	}

	@Tips(description = "H5页面后退")
	public void goWebkitBack() {
		DriverManger.getDriver().navigate().back();
	}

	@Tips(description = "H5页面刷新")
	public void goWebkitRefresh() {
		DriverManger.getDriver().navigate().refresh(); // 刷新
	}

	@Tips(description = "启动应用")
	public void goLaunchApp() {
		driver.launchApp();
	}

	public void go2killApp() {
		AppUtil.killApp();
	}

	public void go2killApp(String packageName) {
		AppUtil.killApp(packageName);
	}

	public boolean go2GetElementSelectedStatus(AndroidElement e) {
		return e.isSelected();
	}

	public boolean go2GetElementEnableStatus(AndroidElement e) {
		return e.isEnabled();
	}

	public void go2ClickAndWait(AndroidElement e, int waitSecond) {
		e.click();
		WaitUtil.forceWait(waitSecond);
	}
}
