package com.cmic.GoAppiumTest.base;

import com.cmic.GoAppiumTest.dataprovider.util.ExcelUtil;
import com.cmic.GoAppiumTest.helper.Heading;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ElementUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class BaseAction {

	protected AndroidDriver<AndroidElement> driver;

	public BaseAction(AndroidDriver driver) {
		this.driver = driver;
	}

	@Tips(description = "点击按钮")
	public void go2Click(AndroidElement e) {
		e.click();
	}

	@Tips(description = "点击坐标或按钮")
	public void go2Tap() {

	}

	@Tips(description = "双击按钮")
	public void go2DoubleTap() {

	}

	@Tips(description = "长按按钮")
	public void go2LongTap() {

	}

	@Tips(description = "全屏幕滑动")
	public void go2SwipeInFullScreen() {

	}

	@Tips(description = "控件内滚动")
	public void go2SwipeInElement(AndroidElement elementScrollable, Heading heading) {
		ElementUtil.swipeControl(elementScrollable, heading);
	}

	@Tips(description = "滚动到底部")
	public void go2Swipe2Bottom() {

	}

	@Tips(description = "Et输入")
	public void go2Type() {

	}

	@Tips(description = "清楚缓存")
	public void go2ClearData() {

	}

	@Tips(description = "清除控件Et的输入内容")
	public void go2Clear() {

	}

	@Tips(description = "复制Et内容")
	public void go2CopyText() {

	}

	@Tips(description = "粘贴复制的文字")
	public void go2PasteText() {

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
	public void go2Command() {

	}

	/**
	 * 点击Home键
	 */
	@Tips(description = "点击Home键")
	public void go2HomePage() {

	}

	@Tips(description = "卸载软件")
	public void go2UninstallApp() {

	}

	@Tips(description = "到达目标页面")
	public void go2TargetPage() {

	}

	@Tips(description = "进行截屏")
	public void go2SnapScreen(String snapMsg) {

	}

	@Tips(description = "点击后退键")
	public void go2Backforward() {

	}

	@Tips(description = "安装app")
	public void go2InstallApp(String appPath) {
	}

	@Tips(description = "判断是否安装")
	public boolean go2CheckInstall(String PackageName) {
		return false;
	}

	@Tips(description = "判断是否锁屏")
	public boolean go2CheckScrennLocked() {
		return false;
	}

	@Tips(description = "打开通知栏界面")
	public void go2OpenNotifications() {
	}

	@Tips(description = "重置应用数据，清除缓存")
	public void go2AppReset() {

	}

	@Tips(description = "软Reset,不清楚缓存")
	public void go2SoftReset() {

	}

	@Tips(description = "模拟按下安卓手机键")
	public void go2PressAndroidKey(int androidKeyCode) {
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

	}

	@Tips(description = "强制休眠")
	public boolean go2CheckElementIsPresentInSafe() {
		return false;
	}

	@Tips(description = "键盘切换")
	public void go2KeyboardSwitch() {

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

}
